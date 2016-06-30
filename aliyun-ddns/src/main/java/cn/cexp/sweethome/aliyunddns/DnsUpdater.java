package cn.cexp.sweethome.aliyunddns;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsRequest;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse.Record;
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordRequest;
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import cn.cexp.sweethome.common.GenericUtils;

public class DnsUpdater {
	private static Logger log = LoggerFactory.getLogger(DnsUpdater.class);
	private IAcsClient client;
	private DdnsConf conf;
	private Record record;

	public DnsUpdater() {
	}

	public DnsUpdater(DdnsConf conf) {
		setConf(conf);
	}

	public void load() {
		record = null;
		DescribeDomainRecordsRequest req = new DescribeDomainRecordsRequest();
		req.setDomainName(conf.getDomainName());
		req.setRRKeyWord(conf.getResourceRecord());
		req.setTypeKeyWord(conf.getType());
		DescribeDomainRecordsResponse resp = null;
		try {
			resp = client.getAcsResponse(req);
		} catch (ClientException e) {
			log.error("Domain load error with {}:", conf, e);
			return;
		}
		List<Record> records = resp.getDomainRecords();
		if (null != records) {
			for (Record rec : records) {
				if(true == conf.equalsRecord(rec)) {
					this.record = rec;
					break;
				}
			}
		}
	}

	public boolean doUpdate() {
	    if(null == this.record) {
	        throw buildIllegalStateException();
        }
		UpdateDomainRecordRequest request = new UpdateDomainRecordRequest();
		request.setRecordId(record.getRecordId());
		request.setRR(record.getRR());
		request.setType(record.getType());
		request.setValue(record.getValue());
		request.setTTL(record.getTTL());
		request.setPriority(record.getPriority());
		request.setLine(record.getLine());
		UpdateDomainRecordResponse resp = null;
		try {
            resp = client.getAcsResponse(request);
        } catch (ClientException e) {
            log.error("Domain update error with {}:", conf, e);
            return false;
        }
		if(true == GenericUtils.equals(record.getRecordId(), resp.getRecordId())) {
		    return true;
		}
		return false;
	}
	
	public boolean available() {
	    return null != this.record;
	}
	
	public void setRecordValue(String ip) {
	    if(null == this.record) {
	        throw buildIllegalStateException();
	    }
	    this.record.setValue(ip);
	}
	
	public String getRecordValue() {
	    if(null == this.record) {
            throw buildIllegalStateException();
        }
	    return this.record.getValue();
	}
	
	private IllegalStateException buildIllegalStateException() {
	    return new IllegalStateException("No record obtained. Maybe you haven't run load()?");
	}

	private void buildClient() {
		IClientProfile profile = DefaultProfile.getProfile(conf.getRegionId(), conf.getAccessKeyId(),
				conf.getAccessKeySecret());
		// 若报Can not find endpoint to access异常，请添加以下此行代码
		// DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Alidns",
		// "alidns.aliyuncs.com");
		this.client = new DefaultAcsClient(profile);
	}

	public DdnsConf getConf() {
		return conf;
	}

	public void setConf(DdnsConf conf) {
		this.conf = conf;
		buildClient();
	}
}
