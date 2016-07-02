package cn.cexp.sweethome.aliyunddns;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.cexp.sweethome.common.ConfigurationLoader;
import cn.cexp.sweethome.common.GenericUtils;

public class Main {
	private static Logger log = LoggerFactory.getLogger(Main.class);
	private ConfigurationLoader<DdnsConf> loader;
	private Scheduler scheduler;
	private String confFile;
	private static final String DEFAULT_CONF_FILE = "ddns.conf";
	private static final String[] CONF_FILE_PATHES = { "." + File.separator + DEFAULT_CONF_FILE,
			"/etc/ddns/" + DEFAULT_CONF_FILE, "c:\\ddns\\" + DEFAULT_CONF_FILE };

	public Main() {
		loader = new ConfigurationLoader<DdnsConf>(DdnsConf.class);
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
		} catch (SchedulerException e) {
			log.error("Build scheduler error.", e);
		}
	}

	public void loadAndSchedule() {
		if (false == tryLoadConf()) {
			log.warn("Cannot find conf file or conf file empty. Programme will exit.");
			try {
				scheduler.shutdown();
			} catch (SchedulerException e) {
				log.error("Scheduler shutdown error.", e);
			}
			return;
		}
		List<DdnsConf> confs = loader.getConfList();
		int scheduleCount = 0;
		for (DdnsConf conf : confs) {
			// JobKey jk = new JobKey(conf.toString(), Constants.JOB_KEY_GROUP);
			// TriggerKey tk = new TriggerKey(conf.toString(),
			// Constants.JOB_KEY_GROUP);
			JobDataMap jdMap = new JobDataMap();
			DnsUpdater updater = new DnsUpdater(conf);
			NicAddressLoader nicLoader = new NicAddressLoader(conf);
			jdMap.put(Constants.JOB_DATA_KEY_CONF, conf);
			jdMap.put(Constants.JOB_DATA_KEY_UPDATER, updater);
			jdMap.put(Constants.JOB_DATA_KEY_NIC_LOADER, nicLoader);
			JobDetail job = JobBuilder.newJob(DnsUpdateJob.class).setJobData(jdMap).build();
			Trigger trigger = TriggerBuilder.newTrigger().startNow()
					.withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(conf.getCheckPeriod())).build();
			try {
				scheduler.scheduleJob(job, trigger);
				log.info("Scheduled job with {}", conf);
				scheduleCount++;
			} catch (SchedulerException e) {
				log.error("Schdule job error with {}. ", conf, e);
			}
		}

		if (0 == scheduleCount) {
			log.info("No job scheduled, scheduler will shutdown.");
			try {
				scheduler.shutdown();
			} catch (SchedulerException e) {
				log.error("No job scheduled shutdown error. ", e);
			}
		}

	}

	private boolean tryLoadConf() {
		if (false == GenericUtils.isNullOrEmpty(confFile)) {
			loader.setConfFile(confFile);
			try {
				loader.load();
			} catch (IOException e) {
				return false;
			}
			if (null != loader.getConfList() && 0 != loader.getConfList().size()) {
				return true;
			}
		} else {
			for (int i = 0; i < CONF_FILE_PATHES.length; i++) {
				loader.setConfFile(CONF_FILE_PATHES[i]);
				try {
					loader.load();
				} catch (IOException e) {
					continue;
				}
				if (null != loader.getConfList() && 0 != loader.getConfList().size()) {
					return true;
				}
			}
		}
		return false;
	}

	public void processCommand(String[] args) throws ParseException {
		CommandLineParser parser = new DefaultParser();
		Options opt = new Options();
		opt.addOption("h", "help", false, "Display this usage information.");
		opt.addOption("c", "conf", true, "Configuration file path.");

		CommandLine cmd = parser.parse(opt, args);
		if (cmd.hasOption("h")) {
			HelpFormatter hf = new HelpFormatter();
			hf.printHelp("Usage: java ddns.jar [-c conf_file_path]", "", opt, "");
			System.exit(0);
		}
		if (cmd.hasOption("c")) {
			confFile = cmd.getOptionValue("c");
		}
	}

	public static void main(String[] args) throws ParseException {
		Main main = new Main();
		main.processCommand(args);
		main.loadAndSchedule();
	}

	public ConfigurationLoader<DdnsConf> getLoader() {
		return loader;
	}

	public void setLoader(ConfigurationLoader<DdnsConf> loader) {
		this.loader = loader;
	}
}
