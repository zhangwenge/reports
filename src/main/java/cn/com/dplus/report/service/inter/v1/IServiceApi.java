package cn.com.dplus.report.service.inter.v1;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.com.dplus.report.entity.mongodb.*;
import cn.com.dplus.report.entity.others.DevInfo;
import cn.com.dplus.report.entity.others.TokenData;
import cn.com.dplus.report.entity.mysql.UserAppInfo;
import cn.com.dplus.report.entity.others.UserInfo;

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

    List<DetectionModel> getModelList(DetectionModel model, String startTime, String endTime, Integer pNow, Integer pSize, String sort);

    List<DetectionModel> getModels(String ids);
}
