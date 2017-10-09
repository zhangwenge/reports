package cn.com.dplus.report.service.impl.v1;


import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.com.dplus.mongodb.SDMongo;
import cn.com.dplus.mongodb.entity.Condition;
import cn.com.dplus.mongodb.entity.Operators;
import cn.com.dplus.mongodb.entity.PageInfo;
import cn.com.dplus.project.constant.Code;
import cn.com.dplus.project.entity.ResponseEntity;
import cn.com.dplus.project.utils.DateTimeUtils;
import cn.com.dplus.report.constant.EnumList;
import cn.com.dplus.report.constant.PactCode;
import cn.com.dplus.report.constant.ReportCode;
import cn.com.dplus.report.entity.Content;
import cn.com.dplus.report.entity.DetectItem;
import cn.com.dplus.report.entity.DetectReport;
import cn.com.dplus.report.entity.DetectResult;
import cn.com.dplus.report.entity.DetectionRecord;
import cn.com.dplus.report.entity.DevEnvFactor;
import cn.com.dplus.report.entity.DevImStatus;
import cn.com.dplus.report.entity.DevInfo;
import cn.com.dplus.report.entity.DevPerformanceRecord;
import cn.com.dplus.report.entity.Head;
import cn.com.dplus.report.entity.Indicator;
import cn.com.dplus.report.entity.InnerQualitystandard;
import cn.com.dplus.report.entity.LabelEntity;
import cn.com.dplus.report.entity.Orchard;
import cn.com.dplus.report.entity.PlantDetectRecord;
import cn.com.dplus.report.entity.PlantInfo;
import cn.com.dplus.report.entity.RKeyValue;
import cn.com.dplus.report.entity.Sample;
import cn.com.dplus.report.entity.SampleAttrValue;
import cn.com.dplus.report.entity.SampleInPool;
import cn.com.dplus.report.entity.SampleSet;
import cn.com.dplus.report.entity.SpecimenRecord;
import cn.com.dplus.report.entity.UserAppInfo;
import cn.com.dplus.report.entity.UserReport;
import cn.com.dplus.report.http.API;
import cn.com.dplus.report.service.inter.v1.IDetectRecordReport;
import cn.com.dplus.report.service.inter.v1.IServiceApi;
import cn.com.dplus.report.utils.DigitalUtil;
import cn.com.dplus.report.utils.FileUtil;
import cn.com.dplus.report.utils.FreeMarkerUtil;
import cn.com.dplus.report.utils.ItextPDFExportUtil;
import cn.com.dplus.report.utils.PoiExcelExportUtil;
import cn.com.dplus.report.utils.TimeUtil;

/**
 * @作用: 检测记录报表
 * @所在包: cn.com.dplus.d.service.impl.v1
 * @开发者: 余浪
 * @邮箱: 365617581@qq.com
 * @时间: 2017/3/31
 * @公司: 广州讯动网络科技有限公司
 */
@Service
public class DetectRecordReport implements IDetectRecordReport {

    @Autowired
    private IServiceApi iServiceApi;
   
    /**
     * 导出检测记录 导出word
     *
     * @param sampleId
     * @param planId
     */
    @Override
    public ResponseEntity detectRecordWord(String userId, String sampleId, String planId) {
        try {
            DetectionRecord record = iServiceApi.getRecord(sampleId, planId);
            DevInfo info = iServiceApi.getDevInfo(record.getDSn());
            UserAppInfo userAppInfo = iServiceApi.getUserAppInfo(userId);
            if (record != null && userAppInfo != null) {
                //不在内控标准内
                String unqualified = "";
                DetectReport dReport = new DetectReport();
                String company = userAppInfo.getCompanyName() != null ? userAppInfo.getCompanyName() : "";
                dReport.setCompany(company);
                dReport.setBreedName(record.getBreedName());
                dReport.setSampleNo(record.getSampleNo());
                dReport.setDetectTime(DateTimeUtils.long2FormatString(record.getCreateTime(), "yyyy-MM-dd hh:mm:ss"));
                
                //查询出温度和湿度，假如没有则记为空值。
                List<SpecimenRecord> results = record.getResults();
                SpecimenRecord specimenRecord = results.get(0);
                List<DevEnvFactor> devEnvFactors = specimenRecord.getDevEnvFactors();
                if(devEnvFactors != null && devEnvFactors.size()>0) {
                	DevEnvFactor devEnvFactor = devEnvFactors.get(0);
                	Float detectorH = devEnvFactor.getDetectorH();
                	Float detectorT = devEnvFactor.getDetectorT();
                	dReport.setDetectorH(DigitalUtil.floatDecimal(detectorH, 2));
                	dReport.setDetectorT(DigitalUtil.floatDecimal(detectorT, 2));
                }else {
                	dReport.setDetectorH(null);
                	dReport.setDetectorT(null);
                }
                //属性
                dReport.setAttriSet(getSampleAttri(sampleId));
                //内控标准
                TreeMap<String, InnerQualitystandard> listStandard = iServiceApi.standards(record.getBreedId(), userId);
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
                    dReport.setTypeName(info!=null?info.getTypeName():"");
                    dReport.setDeviceUserLabel(info!=null?info.getDeviceUserLabel():"");
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

    /**
     * 检测记录列表Excel导出
     *
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
     */
    @Override
    public ResponseEntity getDetectListExcel(String userId, String dSn, String breedId, String industryId, String sampleNo, String planId,
                                             String startTime, String endTime, String pNow, String pSize, String sort) {
    	PageInfo<DetectionRecord> piDetectionRecord = getPIDetectionRecord(userId, dSn, breedId, industryId, sampleNo, planId, startTime, endTime, pNow, pSize, sort);
    	List<DetectionRecord> detectionRecords = piDetectionRecord.getData();
    	if(detectionRecords!=null&&!detectionRecords.isEmpty()) {
    		//对检测记录分类 安照品种分类   key:品种名称   value：检测
    		TreeMap<String,List<DetectionRecord>> groupListRecord = groupListRecord(detectionRecords);
    		//创建所需要的content
    		List<Content> contents = new ArrayList<>();
    		for (Map.Entry<String,List<DetectionRecord>> entry : groupListRecord.entrySet()) {
    			//创建一个内容      --sheet
    			Content content = new Content();
    			content.setBreedName(entry.getKey());
    			//得到样本    sampleRecordId 的集合
				List<String> SRids = entry.getValue().stream().map(dr -> dr.get_id()).collect(Collectors.toList());
				//创建头部存储信息
				ArrayList<Head> headList = new ArrayList<>();
				List<List<Object>> bodyList = new ArrayList<>();
				//将头部信息独立出来
				List<Head> headListR = getHeadList(userId, SRids, headList);
				content.setHeadList(headListR);
				List<DetectionRecord> DrList = entry.getValue();
				for (DetectionRecord detectionRecord : DrList) {
					try {
						//对内控标准做出判断     
						List<Object> body = new ArrayList<>();
						//设置样品的编码    --黑色 字体 白色背景 
						body.add(detectionRecord.getSampleNo()+PactCode.BLACK_WORD+PactCode.BLUE_BACKGROUND);
						//查询设备，假如设备没有名称的话就就设为SN码     --黑色
						String dSn2 = detectionRecord.getDSn();
						String deviceUserLabel = SDMongo.findOne(DevImStatus.class, detectionRecord.getDSn()).getDeviceUserLabel();
						body.add(deviceUserLabel==null?dSn2+PactCode.BLACK_WORD+PactCode.BLUE_BACKGROUND:deviceUserLabel+PactCode.BLACK_WORD+PactCode.BLUE_BACKGROUND);
						//设置检测日期    --黑色
						body.add(DateTimeUtils.long2FormatString(detectionRecord.getLastUpdateTime() , "yyyy/MM/dd HH:mm")+PactCode.BLACK_WORD+PactCode.BLUE_BACKGROUND);
						//设置检测方案     --黑色
						body.add(detectionRecord.getPlanName()+PactCode.BLACK_WORD+PactCode.BLUE_BACKGROUND);
						//样本记录
						String sampleRecordId = detectionRecord.get_id();
						//一条件检测记录的下的  多个样本的检测值--- 为导出检测记录做预留准备
						List<List<DetectResult>> SrONEList = SDMongo.find(SpecimenRecord.class, new Condition("sampleRecordId", sampleRecordId)).stream().map(Sr -> Sr.getResults()).collect(Collectors.toList());
						//将全部的检测结果聚合
						ArrayList<DetectResult> results = new ArrayList<>();
						for (List<DetectResult> list : SrONEList) {
							for (DetectResult detectResult : list) {
								results.add(detectResult);
							}
						}
							//求平均值 ，定性没有看做   --
							for(Head head : headListR) {
								String indicatorId = head.getIndicatorId();
								String unit = head.getUnit();
								Integer indicatorType = head.getIndicatorType();
								Float innerCtrValueMax = head.getInnerCtrValueMax();
								Float innerCtrValueMin = head.getInnerCtrValueMin();
								Map<String, String> models = head.getModels();
								for(Map.Entry<String, String> entry2 :models.entrySet()) {
									String modelId = entry2.getKey();
									//判断是定性
									if(PactCode.QUALITATIVE.equals(indicatorType)) {
										//设置格式为黑色的
										body.add("--"+PactCode.BLACK_WORD+PactCode.BLUE_BACKGROUND);
									}else {
										//将符合标准的模型id和指标id提出来
										List<DetectResult> collect = results.stream().filter(result -> indicatorId.equals(result.getIndicatorId())&&modelId.equals(result.getModelId())).collect(Collectors.toList());
										if(collect==null||collect.isEmpty()) {
											//设置格式为黑色
											body.add("--"+PactCode.BLACK_WORD+PactCode.BLUE_BACKGROUND);
										}else {
											Double double1 = collect.stream().map(a -> a.getValue()).reduce(Double::sum).get();
											Double doubleDecimal = DigitalUtil.doubleDecimal(double1/collect.size(), 2);
											if("%".equals(unit)) {
												if(doubleDecimal>100||doubleDecimal<0) {
													body.add("异常"+PactCode.BLACK_WORD+PactCode.BLUE_BACKGROUND);
												}else {
													if(innerCtrValueMax!=null&&innerCtrValueMin!=null) {
														//将平均值加入
															if(doubleDecimal>=innerCtrValueMin&&doubleDecimal<=innerCtrValueMax) {
																body.add(doubleDecimal+PactCode.BLACK_WORD+PactCode.BLUE_BACKGROUND);
															}else {
																body.add(doubleDecimal+PactCode.RED_WORD+PactCode.BLUE_BACKGROUND);
															}
														}else {
															//将平均值加入
															body.add(doubleDecimal+PactCode.BLACK_WORD+PactCode.BLUE_BACKGROUND);
														}
												}
											}else {
												if(innerCtrValueMax!=null&&innerCtrValueMin!=null) {
												//将平均值加入
													if(doubleDecimal>=innerCtrValueMin&&doubleDecimal<=innerCtrValueMax) {
														body.add(doubleDecimal+PactCode.BLACK_WORD+PactCode.BLUE_BACKGROUND);
													}else {
														body.add(doubleDecimal+PactCode.RED_WORD+PactCode.BLUE_BACKGROUND);
													}
												}else {
													//将平均值加入
													body.add(doubleDecimal+PactCode.BLACK_WORD+PactCode.BLUE_BACKGROUND);
												}
											}
										}
									}
								}
							}
							
							//添加多条样本记录给bodyList
							bodyList.add(body);
							Condition[] conditions = new Condition[] {
									new Condition("sampleRecordId",sampleRecordId),
									new Condition("-detectTime", Operators.sort)
							};
							List<SpecimenRecord> sRList = SDMongo.find(SpecimenRecord.class, conditions);
							int size = sRList.size();
							for (SpecimenRecord specimenRecord : sRList) {
								List<Object> bodysp = new ArrayList<>();
								//设置样品编号
								bodysp.add("#"+size+PactCode.BLACK_WORD+PactCode.WHITE_BACKGROUND);
								//设置设备型号
								bodysp.add(deviceUserLabel==null?dSn2+PactCode.BLACK_WORD+PactCode.WHITE_BACKGROUND:deviceUserLabel+PactCode.BLACK_WORD+PactCode.WHITE_BACKGROUND);
								//设置检测时间
								bodysp.add(DateTimeUtils.long2FormatString(specimenRecord.getDetectTime() , "yyyy/MM/dd HH:mm")+PactCode.BLACK_WORD+PactCode.WHITE_BACKGROUND);
								//设置检测方案     --黑色
								if(detectionRecord.getPlanName()!=null) {
									bodysp.add(detectionRecord.getPlanName()+PactCode.BLACK_WORD+PactCode.WHITE_BACKGROUND);
								}else {
									bodysp.add("--"+PactCode.BLACK_WORD+PactCode.WHITE_BACKGROUND);
								}
								//样品   相应的检测项
								List<DetectResult> results2 = specimenRecord.getResults();
								for(Head head : headListR) {
									//指标ID
									String indicatorId = head.getIndicatorId();
									//0是定量  1是定性
									Integer indicatorType = head.getIndicatorType();
									//内存指标的最大值
									Float innerCtrValueMax = head.getInnerCtrValueMax();
									//内存指标的最小值
									Float innerCtrValueMin = head.getInnerCtrValueMin();
									//单位
									String unit = head.getUnit();
									Map<String, String> models = head.getModels();
									for(Map.Entry<String, String> entry2 :models.entrySet()) {
										//理论上只有一个，不可能有多个
										List<DetectResult> collect = results2.stream().filter(result -> indicatorId.equals(result.getIndicatorId())&&entry2.getKey().equals(result.getModelId())).collect(Collectors.toList());
										if(collect==null||collect.isEmpty()) {
											bodysp.add("--"+PactCode.BLACK_WORD+PactCode.WHITE_BACKGROUND);
										}else {
											Double value = collect.get(0).getValue();
												//判断是定性
												if(PactCode.QUALITATIVE.equals(indicatorType)) {
													if(value>0.5) {
														bodysp.add(head.getLabelTrue()+PactCode.BLACK_WORD+PactCode.WHITE_BACKGROUND);
													}else {
														bodysp.add(head.getLabelFalse()+PactCode.BLACK_WORD+PactCode.WHITE_BACKGROUND);
													}
												}else {
													if("%".equals(unit)) {
														if(value>100||value<0) {
															bodysp.add("异常"+PactCode.BLACK_WORD+PactCode.WHITE_BACKGROUND);
														}else {
															if(innerCtrValueMin!=null&&innerCtrValueMax!=null) {
																if(value>=innerCtrValueMin&&value<=innerCtrValueMax) {
																	bodysp.add(value+PactCode.BLACK_WORD+PactCode.WHITE_BACKGROUND);
																}else {
																	bodysp.add(value+PactCode.RED_WORD+PactCode.WHITE_BACKGROUND);
																}
															}else {
																bodysp.add(value+PactCode.BLACK_WORD+PactCode.WHITE_BACKGROUND);
															}
														}
													}else {
														if(innerCtrValueMin!=null&&innerCtrValueMax!=null) {
															if(value>=innerCtrValueMin&&value<=innerCtrValueMax) {
																bodysp.add(value+PactCode.BLACK_WORD+PactCode.WHITE_BACKGROUND);
															}else {
																bodysp.add(value+PactCode.RED_WORD+PactCode.WHITE_BACKGROUND);
															}
														}else {
															bodysp.add(value+PactCode.BLACK_WORD+PactCode.WHITE_BACKGROUND);
														}
													}
												}
										}
									}
								}
								bodyList.add(bodysp);
								size--;
							}
					} catch (Exception e) {
						e.printStackTrace();
						return new ResponseEntity(ReportCode.FAILED, ReportCode.FILED_MSG);
					}
				}
				content.setBodyList(bodyList);
				contents.add(content);
    		}
    		try {
    			String fileName = "检测记录-" + TimeUtil.getNowTime() + ".xls";
    			byte[] bytes = PoiExcelExportUtil.exportExcel1(contents, fileName);
    			FileUtil.saveFile(bytes, API.REPORT_PATH, fileName);
    			return new ResponseEntity(API.EXPORT_PATH + fileName);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity(ReportCode.FAILED, ReportCode.FILED_MSG);
			}
    	}else {
    		return new ResponseEntity(Code.NO_RESULT, ReportCode.NO_DATA);
    	}
    }


    /**
     * 参考值导出
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
    public ResponseEntity getDetectInputListExcel(String userId, String dSn, String breedId, String industryId, String sampleNo, String planId,
                                                  String startTime, String endTime, String pNow, String pSize, String sort) {   	
    	PageInfo<DetectionRecord> piDetectionRecord = getPIDetectionRecord(userId, dSn, breedId, industryId, sampleNo, planId, startTime, endTime, pNow, pSize, sort);
    	List<DetectionRecord> detectionRecords = piDetectionRecord.getData();
    	if(detectionRecords!=null) {
    		//对检测记录分类 安照品种分类   key:品种名称   value：检测
    		TreeMap<String,List<DetectionRecord>> groupListRecord = groupListRecord(detectionRecords);
    		//创建所需要的content
    		List<Content> contents = new ArrayList<>();
    		
    		for (Map.Entry<String,List<DetectionRecord>> entry : groupListRecord.entrySet()) {
    			//创建一个内容      --sheet
    			Content content = new Content();
    			content.setBreedName(entry.getKey());
    			//得到样本    sampleRecordId 的集合
				List<String> SRids = entry.getValue().stream().map(dr -> dr.get_id()).collect(Collectors.toList());
				//创建头部存储信息
				ArrayList<Head> headList = new ArrayList<>();
				List<List<Object>> bodyList = new ArrayList<>();
				//将头部信息独立出来
				List<Head> headListR = getHeadList(userId, SRids, headList);
				content.setHeadList(headListR);
				//
				List<DetectionRecord> DrList = entry.getValue();
				for (DetectionRecord detectionRecord : DrList) {
					try {
						//对内控标准做出判断    0是黑色，1是红色  
					List<Object> body = new ArrayList<>();
					//设置样品的编码    --黑色
					body.add(detectionRecord.getSampleNo()+PactCode.BLACK_WORD);
					//查询设备，假如设备没有名称的话就就设为SN码     --黑色
					String dSn2 = detectionRecord.getDSn();
					String deviceUserLabel = SDMongo.findOne(DevImStatus.class, detectionRecord.getDSn()).getDeviceUserLabel();
					body.add(deviceUserLabel==null?dSn2+PactCode.BLACK_WORD:deviceUserLabel+PactCode.BLACK_WORD);
					//设置检测日期    --黑色
					body.add(DateTimeUtils.long2FormatString(detectionRecord.getLastUpdateTime() , "yyyy/MM/dd HH:mm")+PactCode.BLACK_WORD);
					//设置检测方案     --黑色
					if(detectionRecord.getPlanName()==null) {
						body.add("--"+PactCode.BLACK_WORD);
					}else {
						body.add(detectionRecord.getPlanName()+PactCode.BLACK_WORD);
					}
					//样本记录
					String sampleRecordId = detectionRecord.get_id();
					//一条件检测记录的下的  多个样本的检测值--- 为导出检测记录做预留准备
					List<List<DetectResult>> SrONEList = SDMongo.find(SpecimenRecord.class, new Condition("sampleRecordId", sampleRecordId)).stream().map(Sr -> Sr.getResults()).collect(Collectors.toList());
					//将全部的检测结果聚合
					ArrayList<DetectResult> results = new ArrayList<>();
					for (List<DetectResult> list : SrONEList) {
						for (DetectResult detectResult : list) {
							results.add(detectResult);
						}
					}
						//求平均值 ，定性没有看做   --
						for(Head head : headListR) {
							String indicatorId = head.getIndicatorId();
							Integer indicatorType = head.getIndicatorType();
							Float innerCtrValueMax = head.getInnerCtrValueMax();
							Float innerCtrValueMin = head.getInnerCtrValueMin();
							String unit = head.getUnit();
							Map<String, String> models = head.getModels();
							for(Map.Entry<String, String> entry2 :models.entrySet()) {
								//判断标准是定性还是定量  0是定量  1是定性   0是黑色 1是红色
								String modelId = entry2.getKey();
								if(PactCode.QUALITATIVE.equals(indicatorType)) {
									//设置格式为黑色的
									body.add("--"+PactCode.BLACK_WORD);
								}else {
									//将符合标准的模型id和指标id提出来
									List<DetectResult> collect = results.stream().filter(result -> indicatorId.equals(result.getIndicatorId())&&modelId.equals(result.getModelId())).collect(Collectors.toList());
									if(collect==null||collect.isEmpty()) {
										//设置格式为黑色
										body.add("--"+PactCode.BLACK_WORD);
									}else {
										Double double1 = collect.stream().map(a -> a.getValue()).reduce(Double::sum).get();
										Double doubleDecimal = DigitalUtil.doubleDecimal(double1/collect.size(), 2);
										if("%".equals(unit)) {
											if(doubleDecimal>0||doubleDecimal<100) {
												body.add("异常"+PactCode.BLACK_WORD);
											}else {
												if(innerCtrValueMax!=null&&innerCtrValueMin!=null) {
													//将平均值加入
													if(doubleDecimal>=innerCtrValueMin&&doubleDecimal<=innerCtrValueMax) {
														body.add(doubleDecimal+PactCode.BLACK_WORD);
													}else {
														body.add(doubleDecimal+PactCode.RED_WORD);
													}
												}else {
													//将平均值加入
													body.add(doubleDecimal+PactCode.BLACK_WORD);
												}
											}
										}else {
											if(innerCtrValueMax!=null&&innerCtrValueMin!=null) {
												//将平均值加入
												if(doubleDecimal>=innerCtrValueMin&&doubleDecimal<=innerCtrValueMax) {
													body.add(doubleDecimal+PactCode.BLACK_WORD);
												}else {
													body.add(doubleDecimal+PactCode.RED_WORD);
												}
											}else {
												//将平均值加入
												body.add(doubleDecimal+PactCode.BLACK_WORD);
											}
										}
									}
								}
							}
							//在这里加入理化值
							SampleAttrValue sampleAttrValue = SDMongo.findOne(SampleAttrValue.class,new Condition("sampleId", detectionRecord.getSampleId()));
							if(sampleAttrValue==null) {
								body.add("--"+PactCode.BLACK_WORD);
							}else {
								if(PactCode.QUALITATIVE.equals(indicatorType)) {
								//定性指标
									if(sampleAttrValue.getAttrValue()<=0.5) {
										body.add(head.getLabelFalse()+PactCode.BLACK_WORD);
									}else {
										body.add(head.getLabelTrue()+PactCode.BLACK_WORD);
									}
							}else {
								if(innerCtrValueMax!=null&&innerCtrValueMin!=null) {
									if(DigitalUtil.doubleDecimal(sampleAttrValue.getAttrValue(), 2)<=innerCtrValueMax&&DigitalUtil.doubleDecimal(sampleAttrValue.getAttrValue(), 2)>=innerCtrValueMin) {
										body.add(DigitalUtil.doubleDecimal(sampleAttrValue.getAttrValue(), 2)+PactCode.BLACK_WORD);
									}else {
										body.add(DigitalUtil.doubleDecimal(sampleAttrValue.getAttrValue(), 2)+PactCode.RED_WORD);
									}
								}else {
									body.add(DigitalUtil.doubleDecimal(sampleAttrValue.getAttrValue(), 2)+PactCode.BLACK_WORD);
								 }
								}
							}
						}
						//添加多条记录给bodyList
						bodyList.add(body);
					} catch (Exception e) {
						e.printStackTrace();
						return new ResponseEntity(ReportCode.FAILED, ReportCode.FILED_MSG);
					}
				}
    		content.setBodyList(bodyList);
    		contents.add(content);
    		}
    		//
    		String fileName = "理化值-" + TimeUtil.getNowTime() + ".xls";
    		try {
    			byte[] bytes = PoiExcelExportUtil.exportExcel(contents, fileName);
    			FileUtil.saveFile(bytes, API.REPORT_PATH, fileName);
    			return new ResponseEntity(API.EXPORT_PATH + fileName);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity(ReportCode.FAILED, ReportCode.FILED_MSG);
			}
    	}else {
    		return new ResponseEntity(Code.NO_RESULT, ReportCode.NO_DATA);
    	}
    }
    
    //
	private List<Head> getHeadList(String userId, List<String> SRids, ArrayList<Head> headList) {
		try {
			//查询单一品种下的   样本集合  --- 需要判断是否为空
			List<SpecimenRecord> specimenRecordList = SDMongo.find(SpecimenRecord.class, new Condition("sampleRecordId", Operators.in, SRids));
			//将指标模型和指标id对应好
			for (SpecimenRecord specimenRecord : specimenRecordList) {
				//每个样本下的 指标检测数据集合
				List<DetectResult> results = specimenRecord.getResults();
				for (DetectResult detectResult : results) {
					detectResult.getIndicatorId();
					String indicatorId = detectResult.getIndicatorId();
					String modelId = detectResult.getModelId();
					String modelName = detectResult.getModelName();
					List<String> indicatorIdList = headList.stream().map(head -> head.getIndicatorId()).collect(Collectors.toList());
					if(!indicatorIdList.contains(indicatorId)) {
						TreeMap<String, String> models = new TreeMap<>();
						//新建一个头部
						Head head = new Head();
						//设置成分id
						head.setIndicatorId(indicatorId);
						
						//---需要判断
						Condition conds4[] = new Condition[] {
								new Condition("_id", indicatorId),
								new Condition("state",Operators.eq,1)
						};
						Indicator indicator = SDMongo.findOne(Indicator.class, conds4);
						models.put(modelId, modelName==null?indicator.getIndicatorName():modelName);
						//设置模型id和名称
						head.setModels(models);
						//设置 指标名称，
						head.setIndicatorName(indicator.getIndicatorName());
						//设置指标  
						head.setIndicatorType(indicator.getIndicatorType());
						//当是定量的时候，设置相应的值
						if(PactCode.RATION.equals(indicator.getIndicatorType())) {
							head.setUnit(indicator.getUnit());
							Condition[] cons = new Condition[] {
								new Condition("userId", userId),
								new Condition("indicatorId", indicatorId)
							};
							InnerQualitystandard innerQualitystandard = SDMongo.findOne(InnerQualitystandard.class,cons);
							if(innerQualitystandard!=null) {
							if(innerQualitystandard.getMin()!=null&&innerQualitystandard.getMax()!=null) {
								head.setInnerCtrValueMin(DigitalUtil.floatDecimal(innerQualitystandard.getMin(), 2));
								head.setInnerCtrValueMax(DigitalUtil.floatDecimal(innerQualitystandard.getMax(), 2));
							}
						  }//设置定性 检测实际情况
						}else {
							head.setLabelFalse(indicator.getValueLabels().get("labelFalse").getKey());
							head.setLabelTrue(indicator.getValueLabels().get("labelTrue").getKey());
						}
						headList.add(head);
					}else {
						//原则上只能有一个不允许出现多个
						List<Head> list = headList.stream().filter(head -> head.getIndicatorId().equals(indicatorId)).collect(Collectors.toList());
						Head head = list.get(0);
						Map<String, String> models = head.getModels();
						//判断模型是不是单一
						if(!models.containsKey(modelId)) {
							models.put(modelId,modelName==null?head.getIndicatorName():modelName);
						}
					}
				}
			}
		return headList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		//发生异常返回空
		return null;
	}

    /**
     * 下载文件
     *
     * @param file
     * @param response
     * @return
     */
    @Override
    public ResponseEntity downLoad(String file, HttpServletResponse response) {
        String filePath = API.REPORT_PATH + file;
        try {
            byte[] bytes = FileUtil.readFile(filePath);
            if (bytes != null) {
                outputResponse(bytes, file, response);
                return new ResponseEntity("成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FileUtil.deleteFile(filePath);
        }
        return new ResponseEntity(Code.NO_RESULT, "找不到相应文件");
    }

   
    /**
     * 检测记录按品种分组
     *
     * @param listRecord
     * @return
     */
    private TreeMap<String, List<DetectionRecord>> groupListRecord(List<DetectionRecord> listRecord) {
        TreeMap<String, List<DetectionRecord>> map = new TreeMap<>();
        TreeMap<String, String> breedMap = new TreeMap<>();
        for (DetectionRecord record : listRecord) {
            if (!breedMap.containsKey(record.getBreedId())) {
                breedMap.put(record.getBreedId(), record.getBreedName());
            }
        }
        for (Map.Entry<String, String> entry : breedMap.entrySet()) {
            List<DetectionRecord> temp = new ArrayList<DetectionRecord>();
            for (DetectionRecord record : listRecord) {
                if (entry.getKey().equals(record.getBreedId())) {
                    temp.add(record);
                }
            }
            map.put(entry.getValue(), temp);
        }
        return map;
    }


    /**
     * 响应流写入数据
     *
     * @param response
     */
    private void outputResponse(byte[] bytes, String fileName, HttpServletResponse response) {
        //输出word内容文件流，提供下载
        String fileType = fileName.substring(fileName.lastIndexOf('.'));
        switch (fileType) {
            case ".xls":
                //需要导出的是97~2003 版本的excel表
                response.setContentType("application/vnd.ms-excel");
                break;
            case ".xlsx":
                //需要导出的为2007版本以上的excel表
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                break;
            case ".doc":
                response.setContentType("application/x-msdownload");
                break;
            default:
                response.setContentType("application/x-msdownload");
                break;

        }
        response.reset();
        response.setContentType("application/x-msdownload");
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 定性样品检测结果集
     *
     * @param record
     * @return
     */
    private List<DetectItem> getQualitative(DetectionRecord record) {
        List<DetectItem> items = new ArrayList<>();
        List<SpecimenRecord> specimenRecords = record.getResults();

        Map<String, List<Indicator>> mapListIndiator = iServiceApi.getIndicatroBid(record.getBreedId());
        List<Indicator> listIndicator = mapListIndiator.get(record.getBreedId());

        int cout = specimenRecords.size();
        for (int i = 0; i < specimenRecords.size(); i++) {
            List<DetectResult> detectResults = specimenRecords.get(i).getResults();
            for (DetectResult dr : detectResults) {
                if (dr.getCmt() == EnumList.ModelType.QUALITATIVE.getValue()) {
                    DetectItem item = new DetectItem();
                    item.setIndicatorName(dr.getIndicatorName() + "#" + cout);
                    item.setValue(getQuiveValueText(listIndicator, dr.getIndicatorId(), dr.getValue()));
                    items.add(item);
                }
            }
            cout--;
        }
        return items;
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
                    Map<String, LabelEntity> mapLabel = ir.getValueLabels();
                    LabelEntity labelTrue = mapLabel.get("labelTrue");
                    LabelEntity labelFalse = mapLabel.get("labelFalse");
                    if (labelTrue != null && labelFalse != null) {
                        if (0 <= value && value < 0.5) {
                            return labelFalse.getKey();
                        } else if (value >= 0.5 && value <= 1) {
                            return labelTrue.getKey();
                        } else {
                            return "异常";
                        }
                    } else {
                        return "";
                    }
                }
            }
        }
        return "";
    }

    /**
     * 获取属性集
     *
     * @param sampleId
     * @return
     */
    private List<RKeyValue> getSampleAttri(String sampleId) {
        List<RKeyValue> attriSet = new ArrayList<>();
        TreeMap<String, Sample> listSample = iServiceApi.getSample(sampleId, null, 0, "0");


        if (listSample != null && listSample.size() == 1) {
            Sample frist = listSample.entrySet().iterator().next().getValue();
            List<SampleAttrValue> listAttrValue = frist.getSampleAttrValues();
            if (listAttrValue != null) {
                for (SampleAttrValue attr : listAttrValue) {
                    Double attrValue = attr.getAttrValue();
                    attriSet.add(new RKeyValue(attr.getAttrName(), attrValue==null ? "" : attrValue+""));
                }
            }
            //默认添加一个样品属性
            String remark = frist.getSampleRemark();
            attriSet.add(new RKeyValue("样品描述", remark == null ? "" : remark));
        }
        return attriSet;
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
    
    //查询DETECTRECORD根据相应的条件查询相应的
    private PageInfo<DetectionRecord> getPIDetectionRecord(String userId, String dSn, String breedId, String industryId, String sampleNo, String planId,
             String startTime, String endTime, String pNow, String pSize, String sort){
    	try {

			//默认当前页
			int pageNow = 1;
			
			//默认返回
			int pageSize = 10;
			
			// 默认的开始时间
			long sTime = 0;
			boolean haveStartTime = false;
			
			
			//默认的结束时间
			long eTime = System.currentTimeMillis();
			boolean haveEndTime = false;
			
			if(!StringUtils.isEmpty(pNow)){
				pageNow = Integer.valueOf(pNow);
			}
			
			if(!StringUtils.isEmpty(pSize)){
				pageSize = Integer.valueOf(pSize);
			}
			
			if(!StringUtils.isEmpty(startTime)){
				haveStartTime = true;
				sTime = DateTimeUtils.string2Long(startTime);
			}
			
			if(!StringUtils.isEmpty(endTime)){
				haveEndTime = true;
				// 结束时间加一天
				eTime = DateTimeUtils.string2Long(endTime)+PactCode.ONE_DAY;
			}
			
			//封装查询条件
			List<Condition> conditions = new ArrayList<Condition>();
			conditions.add(new Condition("userId", userId));
			conditions.add(new Condition("state", 1));
			
			
			if(haveStartTime && haveEndTime){
				conditions.add(new Condition("lastUpdateTime", Operators.gte, sTime));
				conditions.add(new Condition("lastUpdateTime", Operators.lte, eTime));
			}else {
				if(haveStartTime){
					conditions.add(new Condition("lastUpdateTime", Operators.gte,sTime));
				}
				if(haveEndTime){
					conditions.add(new Condition("lastUpdateTime", Operators.lte, eTime));
				}
			}
			
			// 查询指定的行业
			if(!StringUtils.isEmpty(industryId)){
				conditions.add(new Condition("industryId", Operators.in,Arrays.asList(industryId.split(","))));
			}
		
			//　查询指定设备的记录
			if(!StringUtils.isEmpty(dSn)){
				conditions.add(new Condition("dSn", dSn));
			}
			
			// 查询的指定品种的记录
			if(!StringUtils.isEmpty(breedId)){
				conditions.add(new Condition("breedId", breedId));
			}
			
			// 指定排序的
			if(!StringUtils.isEmpty(sort)){
				conditions.add(new Condition(sort, Operators.sort));			
			}else{
				conditions.add(new Condition("-lastUpdateTime", Operators.sort));			//默认按检测时间来降序排序
			}
			
			// 通过用户的自定义的编号来模糊匹配
			if(!StringUtils.isEmpty(sampleNo)){
				conditions.add(new Condition("sampleNo", Operators.like,sampleNo));
			}
			
			//方案id
			if(!StringUtils.isEmpty(planId)){
				conditions.add(new Condition("planId", planId));
			}

			// 执行分页查询
			PageInfo<DetectionRecord> detectionRecords = SDMongo.findInPage(DetectionRecord.class, pageNow, pageSize, conditions.toArray(new Condition[]{}));
    		
    		return detectionRecords;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

    /**
     * 
     * @方法功能：到设备在一定时间的内的  参比，检测记录，扫描的光谱的信息
     * @方法名称：getUserMessage
     * @编写时间：2017年9月22日上午9:54:32
     * @开发者 ： 张文歌
     * @方法参数：
     * @返回参数：
     */
	@Override
	public ResponseEntity getUserMessage(String dSns, String startTime, String endTime) {
	    List<List<String>> content = new ArrayList<>();
		List<String> asList = Arrays.asList(dSns.split(","));
		List<UserReport> userReportList = new ArrayList<>();
		long sTime = 0;
	    long eTime = System.currentTimeMillis();
	    try {
	    	 if(!StringUtils.isEmpty(startTime)) {
	 	    	sTime = DateTimeUtils.string2Long(startTime);
	 	    }
	 	    if(!StringUtils.isEmpty(endTime)) {
	 	    	eTime = DateTimeUtils.string2Long(endTime)+PactCode.ONE_DAY;
	 	    }
	 		for (String dSn : asList) {
	 			//导出用户信息的
	 			UserReport userReport = new UserReport();
	 			//设置设备信息的
				userReport.setDevMessage(dSn);
	 			//设置时间
	 			userReport.setStatisticsTime(startTime+"至"+endTime);
 				ArrayList<Condition> conds = new ArrayList<>();
 				conds.add(new Condition("dSn",dSn));
 				conds.add(new Condition("type", 2));
 				conds.add(new Condition("state",1));
 				conds.add(new Condition("endTime", Operators.lte,eTime));
 				conds.add(new Condition("endTime",Operators.gte,sTime));
 				
 				/** 参比阶段*/
 				List<DevPerformanceRecord> DevPFRlist = SDMongo.find(DevPerformanceRecord.class, conds.toArray(new Condition[]{}));
 				if(DevPFRlist.isEmpty()||DevPFRlist==null) {
 					//设置--参比次数
 					userReport.setNumberOfReferences(0);
 					//设置--参比失败次数
 					userReport.setNumberOfReferencesFailure(0);
 					//设置--参比失败时间
 					userReport.setReferencesFailureTime("");
 				}else {
 					//设置--参比次数
 					userReport.setNumberOfReferences(DevPFRlist.size());
 					List<DevPerformanceRecord>  DevPFRlistFaile = DevPFRlist.stream().filter(a -> a.getErrCode()!=0).collect(Collectors.toList());
 					//设置--参比失败次数
 					userReport.setNumberOfReferencesFailure(DevPFRlistFaile.size());
 					//设置--参比失败时间    DateTimeUtils.long2FormatString(detectionRecord.getLastUpdateTime() , "yyyy/MM/dd HH:mm")
 					userReport.setReferencesFailureTime(DevPFRlistFaile.stream().map(a -> DateTimeUtils.long2FormatString(a.getEndTime(),"yyyy-MM-dd HH:mm:ss")).collect(Collectors.joining(",")));
 				}
 				
 				/** 检测总记录数*/
 				List<Condition> conds1 = new ArrayList<>();
 				conds1.add(new Condition("dSn",dSn));
 				conds1.add(new Condition("lastUpdateTime",Operators.lte,eTime));
 				conds1.add(new Condition("lastUpdateTime",Operators.gte,sTime));
 				conds1.add(new Condition("state", 1));
 				List<DetectionRecord> detectionRecordList = SDMongo.find(DetectionRecord.class,conds1.toArray(new Condition[]{}));
 				if(detectionRecordList.isEmpty()||detectionRecordList==null) {
 					userReport.setNumberOfRecord(0);
 				}else {
 					userReport.setNumberOfRecord(detectionRecordList.size());
 				}
 				
 				/** 样品集扫描光谱数*/
 				//查询样品集
 				List<Condition> conds2 = new ArrayList<>();
 				conds2.add(new Condition("dSn",dSn));
 				conds2.add(new Condition("state",1));
 				conds2.add(new Condition("updateTime",Operators.lte,eTime));
 				conds2.add(new Condition("updateTime",Operators.gte,sTime));
 				List<SampleSet> SampleSetList = SDMongo.find(SampleSet.class,conds2.toArray(new Condition[]{}));
 				
 				//查询样品
 				List<Condition> conds3 = new ArrayList<>();
 				conds3.add(new Condition("dSn", dSn));
 				conds3.add(new Condition("state",1));
 				conds3.add(new Condition("lastCollectTime",Operators.gte,sTime));
 				conds3.add(new Condition("lastCollectTime",Operators.lte,eTime));
 				List<Sample> SampleList = SDMongo.find(Sample.class,conds3.toArray(new Condition[]{}));
 				//得到样本的ID集合
 				List<String> sampleIdList = SampleList.stream().map(a -> a.get_id()).collect(Collectors.toList());
 				
 				String name = "";
 				for(SampleSet sampleSet:SampleSetList) {
 					sampleSet.get_id();
 					sampleSet.getSampleSetName();
 					List<Condition> conds4 = new ArrayList<>();
 					conds4.add(new Condition("sampleSetId",sampleSet.get_id()));
 					conds4.add(new Condition("sampleId",Operators.in,Arrays.asList(sampleIdList)));
 					List<SampleInPool> SampleInPoolList = SDMongo.find(SampleInPool.class, conds4.toArray(new Condition[]{}));
 					name = name + sampleSet.getSampleSetName()+"("+SampleInPoolList.size()+")"+",";
 				}
 				userReport.setSpectrum(name);
	 		userReportList.add(userReport);
	 		}
	 		//转换成适合POI的形式
	 		content.add(userReportList.stream().map(a -> a.getDevMessage()).collect(Collectors.toList()));
	 		content.add(userReportList.stream().map(a -> a.getStatisticsTime()).collect(Collectors.toList()));
	 		content.add(userReportList.stream().map(a -> a.getNumberOfReferences()+"").collect(Collectors.toList()));
	 		content.add(userReportList.stream().map(a -> a.getNumberOfReferencesFailure()+"").collect(Collectors.toList()));
	 		content.add(userReportList.stream().map(a -> a.getReferencesFailureTime()).collect(Collectors.toList()));
	 		content.add(userReportList.stream().map(a -> a.getNumberOfRecord()+"").collect(Collectors.toList()));
	 		content.add(userReportList.stream().map(a -> a.getSpectrum()).collect(Collectors.toList()));
	 		
	 		/** 生成Excel*/
	 		String fileName = "用户检测信息统计" + TimeUtil.getNowTime() + ".xls";
			byte[] bytes = PoiExcelExportUtil.exportExcel2(content, fileName);
			FileUtil.saveFile(bytes, API.REPORT_PATH, fileName);
			return new ResponseEntity(API.EXPORT_PATH + fileName);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(ReportCode.FAILED, ReportCode.FILED_MSG);
		}
	}
	
	/**
	 * 
	 * @方法功能：获取要打印的PDF数据
	 * @方法名称：getCitrusPDF
	 * @编写时间：2017年9月28日上午9:48:09
	 * @开发者 ： 张文歌
	 * @方法参数：
	 * @返回参数：
	 */
	@Override
	public ResponseEntity getCitrusPDF(String plantId) {
		try {
			//根据植株编号查询植株信息
			Condition conds1[] = new Condition[] {
					new Condition("_id", plantId),
					new Condition("state",Operators.eq,1)
					};
			PlantInfo plantInfo = SDMongo.findOne(PlantInfo.class,conds1);
			//植株信息不能 不存在
			if(plantInfo==null) {
				return new ResponseEntity(Code.NO_RESULT, ReportCode.NO_DATA);
			}
			
			//查询果园信息
			Orchard orchard = iServiceApi.getOrchard(plantInfo.getOrchardId());
			
//			//查询果园信息
//			Condition conds2[] = new Condition[] {
//					new Condition("_id", plantInfo.getOrchardId()),
//					new Condition("state",Operators.eq,1)
//			};
//			Orchard orchard = SDMongo.findOne(Orchard.class, conds2);
			
			//查询检测记录
			Condition conds3[] = new Condition[] {
				new Condition("plantId", plantId),
				new Condition("state",Operators.eq,1),
				new Condition("-createTime", Operators.sort)
			};
			List<PlantDetectRecord> plantDetectRecordList = SDMongo.find(PlantDetectRecord.class,conds3);
			
			String indicatorId = plantDetectRecordList.get(0).getIndicatorId();
			Condition conds4[] = new Condition[] {
					new Condition("_id", indicatorId),
					new Condition("state",Operators.eq,1)
			};
			Indicator indicator = SDMongo.findOne(Indicator.class,conds4);
			//正面结果
			String labelTrue = indicator.getValueLabels().get("labelTrue").getKey();
			//反面结果
			String labelFalse = indicator.getValueLabels().get("labelFalse").getKey();
			
			//给植株加检测状态
			if(plantInfo.getDiagnoseValue()!=null) {
				plantInfo.setLabelState(plantInfo.getDiagnoseValue()>0.5?labelTrue:labelFalse);
			}else {
				plantInfo.setLabelState("--");
			}
			
			plantDetectRecordList.forEach(record->{
				record.setLabelState(record.getValue()>0.5?labelTrue:labelFalse);
			});
			String fileName = "检测报告单"+plantInfo.getPlantNo()+".pdf";
			byte[] bytes = ItextPDFExportUtil.exportPDF(plantInfo, orchard, plantDetectRecordList);
			FileUtil.saveFile(bytes, API.REPORT_PATH, fileName);
			return new ResponseEntity(API.EXPORT_PATH + fileName);
		} catch (Exception e) {
			e.getStackTrace();
			return new ResponseEntity(ReportCode.FAILED, ReportCode.FILED_MSG);
		}
	}
}

