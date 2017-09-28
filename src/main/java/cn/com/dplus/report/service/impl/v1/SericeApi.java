package cn.com.dplus.report.service.impl.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.reflect.TypeToken;

import cn.com.dplus.mongodb.entity.PageInfo;
import cn.com.dplus.project.constant.Code;
import cn.com.dplus.project.entity.ResponseEntity;
import cn.com.dplus.project.http.HttpUtils;
import cn.com.dplus.project.utils.JsonUtil;
import cn.com.dplus.report.entity.DetectionRecord;
import cn.com.dplus.report.entity.DevInfo;
import cn.com.dplus.report.entity.Indicator;
import cn.com.dplus.report.entity.InnerQualitystandard;
import cn.com.dplus.report.entity.Sample;
import cn.com.dplus.report.entity.TokenData;
import cn.com.dplus.report.entity.UserAppInfo;
import cn.com.dplus.report.entity.UserInfo;
import cn.com.dplus.report.http.API;
import cn.com.dplus.report.service.inter.v1.IServiceApi;

/**
 * @作用: API接口服务
 * @所在包: cn.com.dplus.report.service.impl.v1
 * @开发者: 余浪
 * @邮箱: 365617581@qq.com
 * @时间: 2017/4/6
 * @公司: 广州讯动网络科技有限公司
 */
@Service
public class SericeApi implements IServiceApi {

    /**
     * 获取内控标准
     *
     * @param breedId
     * @param userId
     * @return
     */
    @Override
    public TreeMap<String, InnerQualitystandard> standards(String breedId, String userId) {
        try {
            //内控标准
            Map<String, Object> urlParams = new HashMap<>();
            urlParams.put("breedId", breedId);
            urlParams.put("userId", userId);
            ResponseEntity obj = HttpUtils.get(API.GET_STANDARDS, urlParams);
            if (obj != null && obj.getCode() == Code.SUCCESS) {
                List<InnerQualitystandard> listStandard = JsonUtil.toObject(JsonUtil.toJson(obj.getResult()),
                        new TypeToken<List<InnerQualitystandard>>() {
                        }.getType());
                TreeMap<String, InnerQualitystandard> map = new TreeMap<>();
                for (InnerQualitystandard standard : listStandard) {
                    map.put(standard.getIndicatorId(), standard);
                }
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检测记录
     *
     * @param sampleId
     * @param planId
     * @return
     */
    @Override
    public DetectionRecord getRecord(String sampleId, String planId) {
        try {
            ResponseEntity obj = HttpUtils.get(API.GET_RECORD + "/" + sampleId + "/" + planId);
            if (obj.getCode() == Code.SUCCESS) {
                DetectionRecord listStandard = JsonUtil.toObject(JsonUtil.toJson(obj.getResult()), DetectionRecord.class);
                return listStandard;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询检测记录
     *
     * @param userId
     * @param dSn
     * @param breedId
     * @param industryId
     * @param sampleNo
     * @param planId
     * @param startTime
     * @param endTime
     * @param pNow
     * @param pSize
     * @param sort
     * @return
     */
    @Override
    public List<DetectionRecord> getRecord(String userId, String dSn, String breedId, String industryId, String sampleNo, String planId,
                                           String startTime, String endTime, String pNow, String pSize, String sort) {
        try {

            Map<String, Object> urlParams = new HashMap<>();
            urlParams.put("userId", userId);
            if (!StringUtils.isEmpty(dSn))
                urlParams.put("dSn", dSn);
            if (!StringUtils.isEmpty(breedId))
                urlParams.put("breedId", breedId);
            if (!StringUtils.isEmpty(industryId))
                urlParams.put("industryId", industryId);
            if (!StringUtils.isEmpty(sampleNo))
                urlParams.put("sampleNo", sampleNo);
            if (!StringUtils.isEmpty(planId))
                urlParams.put("planId", planId);
            if (!StringUtils.isEmpty(startTime))
                urlParams.put("startTime", startTime);
            if (!StringUtils.isEmpty(endTime))
                urlParams.put("endTime", endTime);
            if (!StringUtils.isEmpty(pNow))
                urlParams.put("pNow", pNow);
            if (!StringUtils.isEmpty(pSize))
                urlParams.put("pSize", pSize);
            if (!StringUtils.isEmpty(sort))
                urlParams.put("sort", sort);
            ResponseEntity obj = HttpUtils.get(API.GET_RECORD, urlParams);
            if (obj != null && obj.getCode() == Code.SUCCESS) {
                PageInfo<DetectionRecord> list = JsonUtil.toObject(JsonUtil.toJson(obj.getResult()),
                        new TypeToken<PageInfo<DetectionRecord>>() {
                        }.getType());
                return list.getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 批量品种查询
     *
     * @param bIds
     * @return
     */
    @Override
    public Map<String, List<Indicator>> getIndicatroBid(String bIds) {
        try {
            Map<String, Object> urlParams = new HashMap<>();
            urlParams.put("id", bIds);
            ResponseEntity obj = HttpUtils.get(API.GET_INDICATORS_BID, urlParams);
            if (obj.getCode() == Code.SUCCESS) {
                Map<String, List<Indicator>> temp = JsonUtil.toObject(JsonUtil.toJson(obj.getResult()),
                        new TypeToken<Map<String, List<Indicator>>>() {
                        }.getType());
                return temp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 用户详细信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserInfo getUserInfo(String userId) {
        try {
            ResponseEntity obj = HttpUtils.get(API.GET_USERINFO + userId);
            if (obj.getCode() == Code.SUCCESS) {
                UserInfo temp = JsonUtil.toObject(JsonUtil.toJson(obj.getResult()), UserInfo.class);
                return temp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询token信息
     *
     * @param token
     * @return
     */
    @Override
    public TokenData getToken(String token) {
        try {
            ResponseEntity obj = HttpUtils.get(API.GET_TOKEN + token);
            if (obj.getCode() == Code.SUCCESS) {
                TokenData temp = JsonUtil.toObject(JsonUtil.toJson(obj.getResult()), TokenData.class);
                return temp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询样品记录
     *
     * @param sampleIds   样品ID
     * @param specimenIds 样本的ids
     * @param getSpectrum 是否加载光谱数据,0不加载，1加载
     * @param getAttr     null 是不加载样品属性 0 是加载基础样品属性 1 加载样品指标属性 2 加载所有的属性
     * @return
     */
    @Override
    public TreeMap<String, Sample> getSample(String sampleIds, String specimenIds, Integer getSpectrum, String getAttr) {
        try {
            Map<String, Object> urlParams = new HashMap<>();
            if (sampleIds == null) {
                return null;
            }
            urlParams.put("sampleIds", sampleIds);
            if (specimenIds != null)
                urlParams.put("specimenIds", specimenIds);
            if (getSpectrum != null)
                urlParams.put("getSpectrum", getSpectrum);
            if (getAttr != null)
                urlParams.put("getAttr", getAttr);
            ResponseEntity obj = HttpUtils.post(API.GET_SAMPLES, urlParams);
            if (obj != null && obj.getCode() == Code.SUCCESS) {
                List<Sample> temp = JsonUtil.toObject(JsonUtil.toJson(obj.getResult()),
                        new TypeToken<List<Sample>>() {
                        }.getType());
                TreeMap<String, Sample> map = new TreeMap<>();
                for (Sample sample : temp) {
                    map.put(sample.get_id(), sample);
                }
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public UserAppInfo getUserAppInfo(String userId) {
        try {
            ResponseEntity obj = HttpUtils.get(API.GET_USER_APP_INFO.replace("{userId}", userId));
            if (obj.getCode() == Code.SUCCESS) {
                UserAppInfo temp = JsonUtil.toObject(JsonUtil.toJson(obj.getResult()), UserAppInfo.class);
                return temp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询设备的信息
     *    设备的ID
     *    设备的名称 
     */
	@Override
	public DevInfo getDevInfo(String dsn) {
		try {
			ResponseEntity obj = HttpUtils.get(API.GET_DEV_INFO.replace("{dns}", dsn));
			
			if(obj.getCode() == Code.SUCCESS && obj!=null) {
				Map<String, DevInfo> map = JsonUtil.toObject(JsonUtil.toJson(obj.getResult()),
						new TypeToken<Map<String, DevInfo>>(){}.getType());
				List<DevInfo> list = new ArrayList<>();
				for(String key:map.keySet()) {
					DevInfo devInfo = map.get(key);
					list.add(devInfo);
				}
				DevInfo devInfo = list.get(0);
				if(devInfo.getDeviceUserLabel()==""||devInfo.getDeviceUserLabel()==null) {
					devInfo.setDeviceUserLabel(devInfo.getTypeName());
				}
				return devInfo;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
    
    
}
