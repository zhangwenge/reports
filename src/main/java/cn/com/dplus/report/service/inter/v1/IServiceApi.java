package cn.com.dplus.report.service.inter.v1;

import cn.com.dplus.report.entity.*;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @作用:
 * @所在包: cn.com.dplus.report.service.inter.v1
 * @开发者: 余浪
 * @邮箱: 365617581@qq.com
 * @时间: 2017/4/6
 * @公司: 广州讯动网络科技有限公司
 */
public interface IServiceApi {
    TreeMap<String, InnerQualitystandard> standards(String breedId, String userId);

    DetectionRecord getRecord(String sampleId, String planId);

    List<DetectionRecord> getRecord(String userId, String dSn, String breedId, String industryId, String sampleNo, String planId,
                                    String startTime, String endTime, String pNow, String pSize, String sort);

    Map<String, List<Indicator>> getIndicatroBid(String bIds);

    UserInfo getUserInfo(String userId);

    TokenData getToken(String token);

    TreeMap<String, Sample> getSample(String sampleIds, String specimenIds, Integer getSpectrum, String getAttr);

    UserAppInfo getUserAppInfo(String userId);
    
    DevInfo getDevInfo(String dsn);
    
    Orchard getOrchard(String id);
}
