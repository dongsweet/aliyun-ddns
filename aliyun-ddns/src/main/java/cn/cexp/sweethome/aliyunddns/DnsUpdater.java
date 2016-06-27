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
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

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
			log.error("Domain list error with {}: {}.", conf, e);
			return;
		}
		List<Record> records = resp.getDomainRecords();
		if(null != records) {
			for(Record rec : records) {
				if(true == conf.getResourceRecord().equalsIgnoreCase(rec.getRR())) {
					record = rec;
					break;
				} else if(0 == conf.getResourceRecord().length() && "@".equals(rec.getRR())) {
					record = rec;
					break;
				}
			}
		}
	}

	public boolean doUpdate() {
		UpdateDomainRecordRequest request = new UpdateDomainRecordRequest();
		request.setLine("");
		return false;
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

	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}
}
