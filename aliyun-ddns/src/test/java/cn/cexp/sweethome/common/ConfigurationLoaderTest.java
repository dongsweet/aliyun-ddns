package cn.cexp.sweethome.common;

import java.util.List;

import org.junit.Test;

import cn.cexp.sweethome.aliyunddns.DdnsConf;

public class ConfigurationLoaderTest {
	@Test
	public void testLoad() {
		ConfigurationLoader<DdnsConf> loader = new ConfigurationLoader<DdnsConf>(DdnsConf.class);
		loader.load("d:\\ddns.json");
		List<DdnsConf> confList = loader.getConfList();
		System.out.println(confList);
	}
}
