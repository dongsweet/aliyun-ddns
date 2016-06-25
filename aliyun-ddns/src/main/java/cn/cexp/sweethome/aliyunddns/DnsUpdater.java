package cn.cexp.sweethome.aliyunddns;

import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsRequest;
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordRequest;
import com.aliyuncs.profile.DefaultProfile;

public class DnsUpdater {
    
    public void list(DdnsConf conf) {
        DescribeDomainRecordsRequest req = new DescribeDomainRecordsRequest();
        req.setDomainName(conf.getDomainName());
//        DefaultProfile.getProfile(regionId, icredential)
    }
    
    public boolean doUpdate(DdnsConf conf) {
        UpdateDomainRecordRequest request = new UpdateDomainRecordRequest();
        request.setLine("");
        return false;
    }
}
