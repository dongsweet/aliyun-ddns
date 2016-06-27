package cn.cexp.sweethome.aliyunddns;

import org.junit.Before;
import org.junit.Test;

import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse.Record;

import cn.cexp.sweethome.common.ConfigurationLoader;

public class DnsUpdaterTest {
	
	private ConfigurationLoader<DdnsConf> loader;
	private DdnsConf conf;

	@Before
	public void before() {
		loader = new ConfigurationLoader<DdnsConf>(DdnsConf.class);
		loader.load("D:\\ddns.conf");
		conf = loader.getConfUnique();
	}
	
	@Test
	public void testLoad() {
		DnsUpdater updater = new DnsUpdater(conf);
		updater.load();
		Record r = updater.getRecord();
		System.out.println(r);
	}
}
