package cn.cexp.sweethome.common;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import cn.cexp.sweethome.aliyunddns.DdnsConf;

public class ConfigurationLoaderTest {
	@Test
	public void testLoad() throws IOException {
		ConfigurationLoader<DdnsConf> loader = new ConfigurationLoader<DdnsConf>(DdnsConf.class);
		loader.load("d:\\ddns.conf");
		List<DdnsConf> confList = loader.getConfList();
		System.out.println(confList);
	}
}
