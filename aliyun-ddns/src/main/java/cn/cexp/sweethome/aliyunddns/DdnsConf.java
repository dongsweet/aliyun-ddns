package cn.cexp.sweethome.aliyunddns;

import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse.Record;

import cn.cexp.sweethome.common.ConfBase;
import cn.cexp.sweethome.common.GenericUtils;

public class DdnsConf extends ConfBase {
    private String domainName;
    private String resourceRecord;
    private String type;
    private int checkPeriod;
    private String watchNic;

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
    
    public int getCheckPeriod() {
		return checkPeriod;
	}

	public void setCheckPeriod(int checkPeriod) {
		this.checkPeriod = checkPeriod;
	}

	public String getWatchNic() {
		return watchNic;
	}

	public void setWatchNic(String watchNic) {
		this.watchNic = watchNic;
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

    public static boolean recordEquals(Record rec1, Record rec2) {
        if (true == GenericUtils.equals(rec1, rec2)) {
            return true;
        }
        if (null == rec1) {
            return false;
        } else if (null == rec2) {
            return false;
        }
        if(false == GenericUtils.equals(rec1.getDomainName(), rec2.getDomainName())) {
            return false;
        }
        if(false == GenericUtils.equals(rec1.getRecordId(), rec2.getRecordId())) {
            return false;
        }
        if(false == GenericUtils.equals(rec1.getRR(), rec2.getRR())) {
            return false;
        }
        if(false == GenericUtils.equals(rec1.getType(), rec2.getType())) {
            return false;
        }
        if(false == GenericUtils.equals(rec1.getValue(), rec2.getValue())) {
            return false;
        }
        if(false == GenericUtils.equals(rec1.getTTL(), rec2.getTTL())) {
            return false;
        }
        if(false == GenericUtils.equals(rec1.getPriority(), rec2.getPriority())) {
            return false;
        }
        if(false == GenericUtils.equals(rec1.getLine(), rec2.getLine())) {
            return false;
        }
        if(false == GenericUtils.equals(rec1.getStatus(), rec2.getStatus())) {
            return false;
        }
        if(false == GenericUtils.equals(rec1.getLocked(), rec2.getLocked())) {
            return false;
        }
        if(false == GenericUtils.equals(rec1.getWeight(), rec2.getWeight())) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "DdnsConf [domainName=" + domainName + ", resourceRecord=" + resourceRecord + ", type=" + type
				+ ", checkPeriod=" + checkPeriod + ", watchNic=" + watchNic + "]";
	}
    
    
}
