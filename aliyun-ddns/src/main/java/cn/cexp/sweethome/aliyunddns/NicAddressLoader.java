package cn.cexp.sweethome.aliyunddns;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NicAddressLoader {
	private static Logger log = LoggerFactory.getLogger(NicAddressLoader.class);
	private String nicName;
	private List<InetAddress> ipv4Addrs;
	private List<InetAddress> ipv6Addrs;

	public NicAddressLoader() {
	}

	public NicAddressLoader(DdnsConf conf) {
		setConf(conf);
	}
	
	public NicAddressLoader(String nicName) {
		this.nicName = nicName;
	}
	
	private void load() {
		ipv4Addrs = new ArrayList<InetAddress>();
		ipv6Addrs = new ArrayList<InetAddress>();
		NetworkInterface intf = null;
		try {
			intf = NetworkInterface.getByName(this.nicName);
		} catch (SocketException e) {
			log.error("Get interface error.", e);
			return;
		}
		if(null == intf) {
			log.error("Cannot get the address of interface {}.", this.nicName);
			return;
		}
		Enumeration<InetAddress> addresses = intf.getInetAddresses();
		while(addresses.hasMoreElements()) {
			InetAddress addr = addresses.nextElement();
			if(addr instanceof Inet4Address) {
				ipv4Addrs.add(addr);
			} else if(addr instanceof Inet6Address) {
				ipv6Addrs.add(addr);
			}
		}
	}
	
	public String getFirstIPv4Address() {
		load();
		if(0 != ipv4Addrs.size()) {
			return ipv4Addrs.get(0).getHostAddress();
		}
		return null;
	}
	
	public String getFirstIPv6Address() {
		load();
		if(0 != ipv6Addrs.size()) {
			return ipv6Addrs.get(0).getHostAddress();
		}
		return null;
	}
	
	public List<InetAddress> getIPv4AddressList() {
		load();
		return ipv4Addrs;
	}
	
	public List<InetAddress> getIPv6AddressList() {
		load();
		return ipv6Addrs;
	}

	public void setConf(DdnsConf conf) {
		this.nicName = conf.getWatchNic();
	}

	public String getNicName() {
		return nicName;
	}

	public void setNicName(String nicName) {
		this.nicName = nicName;
	}
}
