package cn.cexp.sweethome.aliyunddns;

import cn.cexp.sweethome.common.ConfBase;

public class DdnsConf extends ConfBase {
	private String domainName;
	private String resourceRecord;
	private String type;

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getResourceRecord() {
		return resourceRecord;
	}

	public void setResourceRecord(String resourceRecord) {
		this.resourceRecord = resourceRecord;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
