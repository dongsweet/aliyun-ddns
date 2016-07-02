package cn.cexp.sweethome.aliyunddns;


import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.cexp.sweethome.common.GenericUtils;

public class DnsUpdateJob implements Job {
	private static Logger log = LoggerFactory.getLogger(DnsUpdateJob.class);
			
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap map = context.getJobDetail().getJobDataMap();
		DdnsConf conf = (DdnsConf) map.get(Constants.JOB_DATA_KEY_CONF);
		NicAddressLoader addLoader = (NicAddressLoader) map.get(Constants.JOB_DATA_KEY_NIC_LOADER);
		DnsUpdater updater = (DnsUpdater) map.get(Constants.JOB_DATA_KEY_UPDATER);
		String ipv4Addr = addLoader.getFirstIPv4Address();
		if(false == GenericUtils.isNullOrEmpty(ipv4Addr)) {
			updater.load();
			if(true == ipv4Addr.equals(updater.getRecordValue())) {
				log.info("IP {} not changed, will not perform update with {}.", ipv4Addr, conf);
			} else {
				log.info("Will perform update {} with ip: {}.", conf, ipv4Addr);
				updater.setRecordValue(ipv4Addr);
				boolean result = updater.doUpdate();
				log.info("Update result: {}.", result);
			}
		}
	}
}
