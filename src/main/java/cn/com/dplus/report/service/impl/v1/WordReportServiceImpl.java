package cn.com.dplus.report.service.impl.v1;

import java.util.*;
import java.util.stream.Collectors;

import cn.com.dplus.report.dao.IUserApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.dplus.mongodb.SDMongo;
import cn.com.dplus.mongodb.entity.Condition;
import cn.com.dplus.mongodb.entity.Operators;
import cn.com.dplus.project.constant.Code;
import cn.com.dplus.project.entity.ResponseEntity;
import cn.com.dplus.project.utils.DateTimeUtils;
import cn.com.dplus.report.constant.EnumList;
import cn.com.dplus.report.constant.PactCode;
import cn.com.dplus.report.constant.ReportCode;
import cn.com.dplus.report.entity.mongodb.DetectionRecord;
import cn.com.dplus.report.entity.mongodb.DevImStatus;
import cn.com.dplus.report.entity.mongodb.DevRuntimeStatus;
import cn.com.dplus.report.entity.mongodb.Indicator;
import cn.com.dplus.report.entity.mongodb.InnerQualitystandard;
import cn.com.dplus.report.entity.mongodb.KV;
import cn.com.dplus.report.entity.mongodb.Sample;
import cn.com.dplus.report.entity.mongodb.SampleAttrValue;
import cn.com.dplus.report.entity.mongodb.SpecimenRecord;
import cn.com.dplus.report.entity.others.DetectItem;
import cn.com.dplus.report.entity.others.DetectReport;
import cn.com.dplus.report.entity.others.DetectResult;
import cn.com.dplus.report.entity.others.RKeyValue;
import cn.com.dplus.report.entity.mysql.UserAppInfo;
import cn.com.dplus.report.http.API;
import cn.com.dplus.report.service.inter.v1.IWordReportService;
import cn.com.dplus.report.utils.DigitalUtil;
import cn.com.dplus.report.utils.FileUtil;
import cn.com.dplus.report.utils.FreeMarkerUtil;
import cn.com.dplus.report.utils.TimeUtil;

@Service
public class WordReportServiceImpl implements IWordReportService{
	@Autowired
    private IUserApp iUserApp;

	@Override
	public ResponseEntity getWord(String userId, String sampleId, String planId) {
		//查询检测记录的值 -- 样本的id 
		try {
	           //获取的检测记录id
	        	List<Condition> conditions = new ArrayList<Condition>();
	            conditions.add(new Condition("sampleId", sampleId));
	            if(planId!=null) {
	            	conditions.add(new Condition("planId", planId));
	            }
	            DetectionRecord record = SDMongo.findOne(DetectionRecord.class,conditions.toArray(new Condition[]{}));
	            //查询用户详情信息
	            //UserAppInfo userAppInfo = iServiceApi.getUserAppInfo(userId);
				UserAppInfo userAppInfo = iUserApp.selectByPrimaryKey(userId);

			if (record != null && userAppInfo != null) {
	                //不在内控标准内
	                String unqualified = "";
	                DetectReport dReport = new DetectReport();
	                String company = userAppInfo.getCompanyName() != null ? userAppInfo.getCompanyName() : "";
	                dReport.setCompany(company);
	                dReport.setBreedName(record.getBreedName());
	                dReport.setSampleNo(record.getSampleNo());
	                dReport.setDetectTime(DateTimeUtils.long2FormatString(record.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
	                
	                List<SpecimenRecord> results = SDMongo.find(SpecimenRecord.class,new Condition("sampleRecordId",record.get_id()));
	                record.setResults(results);
	                //取得最后一个样本
	                SpecimenRecord specimenRecord = results.get(results.size()-1);
	                List<String> devStateIds = specimenRecord.getDevStateIds();
	                if(devStateIds==null) {
	                	dReport.setDetectorT(null);
	            		dReport.setDetectorH(null);
	                }else {
	                	if((devStateIds.size()-2)<0) {
	                		String devStateId = devStateIds.get(devStateIds.size()-1);
	                    	DevRuntimeStatus devRuntimeStatus = SDMongo.findOne(DevRuntimeStatus.class, devStateId);
							getTH(dReport, devRuntimeStatus);
						}else {
	                		String devStateId = devStateIds.get(devStateIds.size()-2);
	                		DevRuntimeStatus devRuntimeStatus = SDMongo.findOne(DevRuntimeStatus.class, devStateId);
							getTH(dReport, devRuntimeStatus);
						}
	                }
	                String dSn = record.getDSn();
	                DevImStatus devImStatus = SDMongo.findOne(DevImStatus.class, dSn);
	                dReport.setTypeName(devImStatus!=null?devImStatus.getDTypeName():"");
	                dReport.setDeviceUserLabel(devImStatus!=null?devImStatus.getDeviceUserLabel():"");
	                //属性
	                dReport.setAttriSet(getSampleAttri(sampleId));
	                //平均值得检测记录的平均值
	                record.setAvgValue(computeAVGValue(results));
	                //内控标准
	                Condition[] conditions2 = new Condition[] {
	                		new Condition("breedId",record.getBreedId()),
	                		new Condition("userId",userId)
	                };
	                List<InnerQualitystandard> list = SDMongo.find(InnerQualitystandard.class,conditions2);
	                TreeMap<String, InnerQualitystandard> listStandard = new TreeMap<>();
	                for (InnerQualitystandard standard : list) {
	                	listStandard.put(standard.getIndicatorId(), standard);
	                }
	                
	                if (listStandard != null) {
	                    //定量
	                    if (record.getAvgValue() != null) {
	                        List<DetectItem> detectList = new ArrayList<>();
	                        for (DetectResult dr : record.getAvgValue()) {
	                            if (dr.getCmt() == EnumList.ModelType.RATION.getValue()) {
	                                DetectItem item = new DetectItem();
	                                item.setUnflag(false);
	                                item.setIndicatorName(dr.getIndicatorName());
	                                item.setValue(getRationValueText(DigitalUtil.doubleDecimal(dr.getValue(), 2), dr.getUnit()));
	                                InnerQualitystandard standard = listStandard.get(dr.getIndicatorId());
	                                if (standard != null) {
	                                    item.setStandard(standard.getMin() + dr.getUnit() + "-" + standard.getMax() + dr.getUnit());
	                                    //设置返回的判断 （指标达标，偏低，偏高）
	                                    item.setJudge(isUnqualifiedPlus(dr.getValue(), standard.getMin(), standard.getMax()));
	                                } else {
	                                    item.setStandard("");
	                                    item.setJudge("");
	                                }
	                                detectList.add(item);
	                            }
	                        }
	                        //定量结果
	                        dReport.setRationList(detectList);
	                        //定性结果
	                        dReport.setQualitativeList(getQualitative(record));
	                    }
	                    if (unqualified != "") {
	                        unqualified = unqualified.substring(1);
	                    }
	                    dReport.setUnqualified(unqualified);
	                    byte[] buffer = FreeMarkerUtil.getInstance("/").createByte(dReport, "detect-report.ftl");
	                    String fileName = "[" + record.getIndustryName() + "]"
	                            + record.getBreedName() + "检测报告单-" +
	                            TimeUtil.getNowTime()
	                            + ".doc";
	                    FileUtil.saveFile(buffer, API.REPORT_PATH, fileName);
	                    return new ResponseEntity(API.EXPORT_PATH + fileName);
	                }
	            } else {
	                return new ResponseEntity(Code.NO_RESULT, ReportCode.NO_DATA);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return new ResponseEntity(ReportCode.FAILED, ReportCode.FILED_MSG);
	}

	private void getTH(DetectReport dReport, DevRuntimeStatus devRuntimeStatus) {
		if(devRuntimeStatus!=null) {
			dReport.setDetectorT(DigitalUtil.floatDecimal(devRuntimeStatus.getDevTemper(), 2));
			dReport.setDetectorH(DigitalUtil.floatDecimal(devRuntimeStatus.getDevHumidity(), 2));
		}else {
			dReport.setDetectorT(null);
			dReport.setDetectorH(null);
		}
	}

	/**
     * 获取属性集
     *
     * @param sampleId
     * @return
     */
    private List<RKeyValue> getSampleAttri(String sampleId) {
    	try {
    		List<RKeyValue> attriSet = new ArrayList<>();

    		//固定--产地，供应商，进货日期
    		attriSet.add(new RKeyValue(PactCode.ORIGIN,""));
    		attriSet.add(new RKeyValue(PactCode.supplier,""));
    		attriSet.add(new RKeyValue(PactCode.PURCHASE_DATE,""));


			Condition[] conditions = new Condition[]{
                    new Condition("_id",sampleId),
                    new Condition("state", Operators.ne, -1)
            };
    		Sample sample = SDMongo.findOne(Sample.class,conditions);
    		
    		conditions = new Condition[]{
                    new Condition("sampleId", sampleId),
                    new Condition("attrType", 0)  // 0 是只加载基础属性   1 只加载指标属性
            };

    		List<SampleAttrValue> attributes = SDMongo.find(SampleAttrValue.class,conditions);
			//样品描述准备数据
    		Map<String, String> collect = attributes.stream().collect(Collectors.toMap(SampleAttrValue::getAttrName, SampleAttrValue::getAttrValue));
			if(!attributes.isEmpty()){
    			for(RKeyValue RK:attriSet){
					String value = collect.get(RK.getKey());
					RK.setValue(value==null?"":value);
				}
			}

			//样品描述
			attriSet.add(new RKeyValue(PactCode.SAMPLE_DISCRIPTION,""));

    		if(sample!=null) {
    			String remark = sample.getSampleRemark();
				attriSet.get(attriSet.size()-1).setValue(remark==null?"":remark);
    		}
    		return attriSet;
		} catch (Exception e) {
			return null;
		}
    }
    
    /**
     * 
     * @描__述: 检测记录的平均值
     * @方法功能：TODO
     * @方法名称：computeAVGValue
     * @编写时间：2017年11月6日上午11:43:10
     * @开发者  ：张文歌
     * @方法参数：
     * @返回值：
     * @接口文档：
     */
    private List<DetectResult> computeAVGValue(List<SpecimenRecord> specimenRecords){
		// 拿出来统计指标的平均理化值
		List<DetectResult> detectResults = null;
		if(specimenRecords!=null){
			// 按模型id分组
			Map<String, DetectResult> indicatorMap = new HashMap<String, DetectResult>();
			for (SpecimenRecord specimenRecord : specimenRecords) {
				List<DetectResult> results = specimenRecord.getResults();
				if(results==null){
					continue;
				}
				Collections.sort(results);
				for (DetectResult detectResult : results) {
					String indicatorId = detectResult.getIndicatorId();  // 指标id
					String modelId = detectResult.getModelId();		// 模型的id
					
					Double value = detectResult.getValue();
					Integer cmt = detectResult.getCmt();  // 建模类型
					String unit = detectResult.getUnit(); // 指标单位
					
					// 这里只有定量的检测值才能计算平均值
					DetectResult avgIndicator = indicatorMap.get(modelId);
					if(avgIndicator==null){
						// 为了满足，无论结果如何都要返回指标名称的期望，这里做了判断
						avgIndicator = new DetectResult();
						avgIndicator.setCount(0);
						avgIndicator.setIndicatorId(indicatorId);
						avgIndicator.setCmt(cmt);
						avgIndicator.setUnit(unit);
						avgIndicator.setValue(0d);
						avgIndicator.setIndicatorName(detectResult.getIndicatorName());
						indicatorMap.put(modelId, avgIndicator);
					}
					
					avgIndicator.setValue(avgIndicator.getValue()+value);
					avgIndicator.setCount(avgIndicator.getCount()+1);
					
					indicatorMap.put(modelId, avgIndicator);
				}
			}
			detectResults = new ArrayList<>(indicatorMap.values());
			if(detectResults!=null){
				Collections.sort(detectResults);
			}
			for (DetectResult detectResult : detectResults) {
				// 计算平均的检测值
				detectResult.computeAVG();
				detectResult.setCount(null);
			}
		}
		return detectResults;
	} 
    /**
     * 定量文本验证
     *
     * @param value
     * @param unit
     * @return
     */
    private String getRationValueText(Double value, String unit) {
        if (unit.equals("%")) {
            if (0 <= value && value <= 100) {
                return String.valueOf(value) + unit;
            }
        } else {
            if (0 <= value) {
                return String.valueOf(value) + unit;
            }
        }
        return "异常";
    }
    
    /**
     * 是否在内控标准内
     *
     * @param value
     * @param min
     * @param max
     * @return
     */
    private String isUnqualifiedPlus(Double value,Float min,Float max) {
    	if(value >= min && value <= max) {
    		return "达标";
    	}else if(value < min) {
    		return "偏低";
    	}else {
    		return "偏高";
    	}
    }
    
    /**
     * 定性样品检测结果集
     *
     * @param record
     * @return
     */
    private List<DetectItem> getQualitative(DetectionRecord record) {
     try {
	    	List<DetectItem> items = new ArrayList<>();
	        List<SpecimenRecord> specimenRecords = record.getResults();
        
	        Condition [] condition = new Condition[] {
	        		new Condition("state", 1),
	        		new Condition("createdAt,updatedAt,state", Operators.ingnore),
	        		new Condition("breedId",record.getBreedId())
	        };
        	List<Indicator> list = SDMongo.find(Indicator.class,condition);
        	Map<String, List<Indicator>> mapListIndiator = list.stream().collect(Collectors.groupingBy(Indicator::getBreedId));
        	List<Indicator> listIndicator = mapListIndiator.get(record.getBreedId());
             int cout = specimenRecords.size();
             for (int i = 0; i < specimenRecords.size(); i++) {
                 List<DetectResult> detectResults = specimenRecords.get(i).getResults();
                 for (DetectResult dr : detectResults) {
                     if (dr.getCmt() == EnumList.ModelType.QUALITATIVE.getValue()) {
                         DetectItem item = new DetectItem();
                         item.setIndicatorName(dr.getIndicatorName() + PactCode.HASHTAG + cout);
                         item.setValue(getQuiveValueText(listIndicator, dr.getIndicatorId(), dr.getValue()));
                         items.add(item);
                     }
                 }
                 cout--;
             }
             return items;
		} catch (Exception e) {
			return null;
		}
       
    }
    /**
     * 正反面文本显示转换
     *
     * @param listIndicator
     * @param id
     * @param value
     * @return
     */
    private String getQuiveValueText(List<Indicator> listIndicator, String id, Double value) {
        if (value != null) {
            for (Indicator ir : listIndicator) {
                if (ir.get_id().equals(id)) {
                    Map<String, KV> mapLabel = ir.getValueLabels();
                    KV labelTrue = mapLabel.get("labelTrue");
                    KV labelFalse = mapLabel.get("labelFalse");
                    if (labelTrue != null && labelFalse != null) {
                        if (value < 0.5) {
                            return labelFalse.getKey();
                        } else if (value >= 0.5) {
                            return labelTrue.getKey();
                        }
                    } else {
                        return "";
                    }
                }
            }
        }
        return "";
    }
}
