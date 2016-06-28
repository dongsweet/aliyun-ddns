package cn.cexp.sweethome.aliyunddns;

import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse.Record;

import cn.cexp.sweethome.common.ConfBase;
import cn.cexp.sweethome.common.GenericUtils;

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

	public boolean equalsRecord(Record rec) {
		if (false == GenericUtils.equals(this.getDomainName(), rec.getDomainName())) {
			return false;
		}
		if (false == GenericUtils.equalsIgnoreCase(this.getType(), rec.getType())) {
			return false;
		}
		if (false == GenericUtils.isNullOrEmpty(this.getResourceRecord())) {
			if (false == GenericUtils.equalsIgnoreCase(this.getResourceRecord(), rec.getRR())) {
				return false;
			}
		} else {
			if (false == GenericUtils.equals("@", rec.getRR())) {
				return false;
			}
		}
		return true;
	}
}
