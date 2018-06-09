package cn.com.dplus.report.service.impl.v1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.com.dplus.mongodb.SDMongo;
import cn.com.dplus.mongodb.entity.Condition;
import cn.com.dplus.mongodb.entity.Operators;
import cn.com.dplus.project.constant.Code;
import cn.com.dplus.project.entity.ResponseEntity;
import cn.com.dplus.project.utils.DateTimeUtils;
import cn.com.dplus.report.constant.PactCode;
import cn.com.dplus.report.dao.IDevBind;
import cn.com.dplus.report.entity.mongodb.DetectionModel;
import cn.com.dplus.report.entity.mongodb.DevPerformanceRecord;
import cn.com.dplus.report.entity.mongodb.Specimen;
import cn.com.dplus.report.entity.mysql.DevBind;
import cn.com.dplus.report.entity.others.DetectValue;
import cn.com.dplus.report.entity.others.DevPRGroup;
import cn.com.dplus.report.entity.others.SGroup;
import cn.com.dplus.report.entity.others.SampleGroup;
import cn.com.dplus.report.http.API;
import cn.com.dplus.report.service.inter.v1.IDevInformationService;
import cn.com.dplus.report.utils.FileUtil;
import cn.com.dplus.report.utils.PoiExcelExportUtil;
import cn.com.dplus.report.utils.TimeUtil;

@Service
public class DevInformationServiceImpl implements IDevInformationService{
	
	@Autowired
	private IDevBind iDevBind;
	
	/**
	 * 
	 * @方法功能：
	 * @方法名称：getDevInformation
	 * @编写时间：2017年11月13日下午4:36:35
	 * @开发者 ： 张文歌
	 * @方法参数：logo=0 页面列表   logo=1导出Excel
	 * @返回参数：
	 */
	@Override
	public ResponseEntity getDevInformation(String dSn, String userId, String startTime, String endTime, Integer pNow,
			Integer pSize, String sort,Integer logo) {
		try {
		//设置时间	
		long sTime = 0;
	    long eTime = System.currentTimeMillis();
		if(!StringUtils.isEmpty(startTime)) {
	    	sTime = DateTimeUtils.string2Long(startTime);
	    }
	    if(!StringUtils.isEmpty(endTime)) {
	    	eTime = DateTimeUtils.string2Long(endTime)+PactCode.ONE_DAY;
	    }
	    //分页做准备
		PageHelper.startPage(pNow == null ? 0 : pNow, pSize == null ? 20 : pSize);
		//用户设备绑定表
		List<DevBind> devBindList = iDevBind.selectUserBind(dSn, userId);
		
		//拿出所有的dSn
		List<String> dSns = devBindList.stream().map(DevBind::getDSn).collect(Collectors.toList());
		
			//取出所有的 参比 自检信息
			Condition[] conditions = new Condition[]{
                    new Condition("dSn", Operators.in, dSns),
                    new Condition("state",1),
                    new Condition("type",Operators.in,Arrays.asList(new Integer[]{2,6})),
                    new Condition("startTime", Operators.gte,sTime),
                    new Condition("startTime",Operators.lte,eTime)
            };
			//分类型 统计   dSn type errorCode
			List<DevPRGroup> devPRGroups = SDMongo.findByGroup(DevPerformanceRecord.class, DevPRGroup.class, conditions);
			//安照 dSn号  对devPRGroups进行在次分类  形成一个map列表  
			Map<String, List<DevPRGroup>> devPs = devPRGroups.stream().collect(Collectors.groupingBy(DevPRGroup::getDSn));
			
			Condition[] conditions1 = new Condition[]{
				new Condition("dSn", Operators.in, dSns),
				new Condition("state",1),
				new Condition("source",Operators.in,Arrays.asList(new Integer[]{1,2})),
				new Condition("collectTime",Operators.gte,sTime),
				new Condition("collectTime",Operators.lte,eTime)
			};
			//分类统计    dSn source 
			List<SGroup> sGroups = SDMongo.findByGroup(Specimen.class, SGroup.class, conditions1);
			//对sGroups安照  dsn进行分类 
			Map<String, List<SGroup>> sGroupmap = sGroups.stream().collect(Collectors.groupingBy(SGroup::getDSn));
			
			//
			Condition[] conditions2 = new Condition[]{
					new Condition("dSn",Operators.in,dSns),
					new Condition("state",1),
					new Condition("source",1),
					new Condition("dtValues",Operators.exists),
					new Condition("dtValues",Operators.request),
					new Condition("collectTime",Operators.gte,sTime),
					new Condition("collectTime",Operators.lte,eTime)
			};
			// dsn sampleId model
			List<SampleGroup> SAGroup = SDMongo.findByGroup(Specimen.class, SampleGroup.class, conditions2);
			//安照 dSn 分类
			Map<String, List<SampleGroup>> SAGroupMap = SAGroup.stream().collect(Collectors.groupingBy(SampleGroup::getDSn));
			
			//设置异常样品数量
			int a = 0;
			//添加返回信息 
			for (DevBind devBind : devBindList) {
				String sn = devBind.getDSn();
				
				//设备操作统计
				List<DevPRGroup> devgs = devPs.get(sn);
				if(devgs!=null) {
					devgs.forEach(d->{
						Integer count = d.getCount();
						if(d.getType()==2) {
							// 参比
							if(d.getErrCode() == null ||  d.getErrCode()!=0) {
								devBind.setReferenceFail(devBind.getReferenceFail()+count);
							}
							devBind.setReferenceSum(devBind.getReferenceSum()+count);
						}else {
							// 自检
							if(d.getErrCode() == null || d.getErrCode()!=0) {
								devBind.setSelfTestFail(devBind.getSelfTestFail()+count);
							}
							devBind.setSelfTestSum(devBind.getSelfTestSum()+count);
						}
					});
				}
				
				// 扫描光谱次数
				List<SGroup> sGroupList = sGroupmap.get(sn);
				
				if(sGroupList!=null) {
					sGroupList.forEach(s ->{
						Integer count = s.getCount();
						if(s.getSource()==1) {
							//快测
							devBind.setFastTestScanningSum(count);
						}else {
							devBind.setMgkScanningSum(count);
						}
					});
				}
				
				
				// 检测异常情况统计
				List<SampleGroup> list = SAGroupMap.get(sn);
				if(list!=null) {
			        for (SampleGroup sampleGroup : list) {
			        	List<List<DetectValue>> dtValues = sampleGroup.getDtValues();
			        	if(dtValues!=null){
							List<DetectValue> changeModel = changeModel(dtValues);
							for (DetectValue detectValue : changeModel) {
								String modelId = detectValue.getModelId();
								Double value = detectValue.getValue();
								Condition []condition3 =new Condition[] {
									new Condition("_id", modelId),
									new Condition("dSn", sn),
									new Condition("cmtId",Operators.request)
								};
								DetectionModel detectionModel = SDMongo.findOne(DetectionModel.class, condition3);
								Integer cmtId = detectionModel.getCmtId();
								if(cmtId==0) {
									if(value>100||value<0) {
										a++;
										break;
									}
								}else {
									if(value<0||value>1) {
										a++;
										break;
									}
								}
							}
						}
			            //设置样品异常数
			        	devBind.setAbnormalSamples(a);
					 }
			   }
			}
			
			if(logo==0) {
				PageInfo<DevBind> pageInfo = new PageInfo<>(devBindList);
				return new ResponseEntity(pageInfo);
			}else {
				String fileName = "设备统计-" + TimeUtil.getNowTime() + ".xls";
				byte[] bytes = PoiExcelExportUtil.exportEquipmentStatistics(devBindList, fileName);
				FileUtil.saveFile(bytes, API.REPORT_PATH, fileName);
    			return new ResponseEntity(API.EXPORT_PATH + fileName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(Code.HTTP_500,e.getMessage());
		}
		
	}
	
	/**
	 * 
	 * @描__述: 二维转一维
	 * @方法功能：TODO
	 * @方法名称：changeModelsss123231322323xkjdasjkdjd
	 * @编写时间：2017年11月10日下午4:47:36
	 * @开发者  ：张文歌
	 * @方法参数：
	 * @返回值：
	 * @接口文档：
	 */
	private List<DetectValue> changeModel(List<List<DetectValue>> dtValues){
		List<DetectValue> newList = new ArrayList<>();
		for (List<DetectValue> list : dtValues) {
			for (DetectValue detectValue : list) {
				newList.add(detectValue);
			}
		}
		return newList;
	}
}
