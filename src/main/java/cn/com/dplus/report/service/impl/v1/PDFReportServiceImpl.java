package cn.com.dplus.report.service.impl.v1;

import cn.com.dplus.mongodb.SDMongo;
import cn.com.dplus.mongodb.entity.Condition;
import cn.com.dplus.mongodb.entity.Operators;
import cn.com.dplus.project.constant.Code;
import cn.com.dplus.project.entity.ResponseEntity;
import cn.com.dplus.project.utils.LogUtil;
import cn.com.dplus.report.constant.ReportCode;
import cn.com.dplus.report.dao.IArea;
import cn.com.dplus.report.entity.mongodb.Indicator;
import cn.com.dplus.report.entity.mongodb.Orchard;
import cn.com.dplus.report.entity.mongodb.PlantDetectRecord;
import cn.com.dplus.report.entity.mongodb.PlantInfo;
import cn.com.dplus.report.entity.mysql.Area;
import cn.com.dplus.report.http.API;
import cn.com.dplus.report.service.inter.v1.IPDFReportService;
import cn.com.dplus.report.utils.FileUtil;
import cn.com.dplus.report.utils.ItextPDFExportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.joining;

@Service
public class PDFReportServiceImpl implements IPDFReportService{
	@Autowired
	private IArea areaDao;

	@Override
	public ResponseEntity getCitrusPDF(String plantNo) {
		try {

            //根据植株编号查询植株信息
			PlantInfo plantInfo = SDMongo.findOne(PlantInfo.class,new Condition("plantNo", plantNo));
			//植株信息不能 不存在
			if(plantInfo==null) {
				return new ResponseEntity(Code.NO_RESULT, ReportCode.NO_DATA);
			}
			
			//查询果园信息
			Orchard orchard = null;
			if(plantInfo.getOrchardId()!= null){
			 	orchard = SDMongo.findOne(Orchard.class, plantInfo.getOrchardId());
				if(orchard != null){
					String adCode = orchard.getAdCode();
					Area area = areaDao.selectByPrimaryKey(adCode);
					List<String> areaNameList = new ArrayList<>();
					areaNameList.add(area.getName());
					while (area.getLevel()>0){
						Area parentArea = areaDao.selectByPrimaryKey(area.getPcode());
						areaNameList.add(parentArea.getName());
						area=parentArea;
					}
					Collections.reverse(areaNameList);
					String address = areaNameList.stream().collect(joining());
					orchard.setAddress(address+orchard.getAddress());
				}
			}

			//查询检测记录
			Condition conds[] = new Condition[] {
				new Condition("plantNo", plantNo),
				new Condition("-createTime", Operators.sort)
			};
			List<PlantDetectRecord> plantDetectRecordList = SDMongo.find(PlantDetectRecord.class,conds);
			
			if(plantDetectRecordList.isEmpty()) {
				return new ResponseEntity(Code.NO_RESULT, ReportCode.NO_RECORD);
			}
			String indicatorId = plantDetectRecordList.get(0).getIndicatorId();
			Condition conds4[] = new Condition[] {
					new Condition("_id", indicatorId),
					new Condition("state",Operators.eq,1)
			};
			Indicator indicator = SDMongo.findOne(Indicator.class,conds4);
			
			if(indicator==null) {
				return new ResponseEntity(Code.NO_RESULT, ReportCode.NO_INDICATOR);
			}
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
			String fileName="检测报告单"+plantInfo.getPlantNo()+".pdf";
			byte[] bytes = ItextPDFExportUtil.exportPDF(plantInfo, orchard, plantDetectRecordList);
			FileUtil.saveFile(bytes, API.REPORT_PATH, fileName);
			return new ResponseEntity(API.EXPORT_PATH + fileName);
		} catch (Exception e) {
			e.getStackTrace();
			LogUtil.Error(e.getMessage());
			return new ResponseEntity(ReportCode.FAILED, ReportCode.FILED_MSG);
		}
	}

}
