package cn.cexp.sweethome.aliyunddns;

import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.junit.Before;
import org.junit.Test;

import cn.cexp.sweethome.common.ConfigurationLoader;

public class NicAddressLoaderTest {

	private ConfigurationLoader<DdnsConf> loader;
	private DdnsConf conf;

	@Before
	public void before() throws IOException {
		loader = new ConfigurationLoader<DdnsConf>(DdnsConf.class);
		loader.load("D:\\ddns.conf");
		conf = loader.getConfUnique();
	}
	
	@Test
	public void testLoad() {
		NicAddressLoader loader = new NicAddressLoader("eth4");
		String ip = loader.getFirstIPv4Address();
		System.out.println(ip);
//		loader = new NicAddressLoader("eth6");
//		ip = loader.getNicAddress();
//		System.out.println(ip);
//		loader = new NicAddressLoader("eth7");
//		ip = loader.getNicAddress();
//		System.out.println(ip);
	}
	
	@Test
	public void printInterfaceList() throws SocketException {
		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		while(interfaces.hasMoreElements()) {
			System.out.println(interfaces.nextElement());
		}
	}
}
