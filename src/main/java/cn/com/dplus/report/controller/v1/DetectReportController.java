package cn.com.dplus.report.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.com.dplus.project.annotation.ParamsValid;
import cn.com.dplus.project.entity.ResponseEntity;
import cn.com.dplus.report.service.inter.v1.IDetectRecordReport;

/**
 * @作用: 检测报表控制层
 * @所在包: cn.com.dplus.d.controller.v1
 * @开发者: 余浪
 * @邮箱: 365617581@qq.com
 * @时间: 2017/3/31
 * @公司: 广州讯动网络科技有限公司
 */
@RestController
public class DetectReportController extends V1Controller{

    @Autowired
    private IDetectRecordReport iDetectRecordReport;

    /**
     * 检测报告
     * @param sampleId
     * @param planId
     * @param response
     * @return
     */
    @RequestMapping(value = "/report-detect")
    public ResponseEntity DetectReport(
            @ParamsValid(notNull = true)String userId,
            @ParamsValid(notNull = true)String sampleId,
            @ParamsValid(notNull = true)String planId){
        return iDetectRecordReport.detectRecordWord(userId,sampleId,planId);
    }

    /**
     * 检测记录导出
     * @param dSn
     * @param breedId
     * @param industryId
     * @param sampleNo
     * @param planId
     * @param startTime
     * @param endTime
     * @param sort
     * @return
     */
    @RequestMapping(value = "/report-detectrecord",method = RequestMethod.GET)
    public ResponseEntity DetectReport(@ParamsValid(notNull = true)String userId,String dSn, String breedId, String industryId, String sampleNo, String planId,
                                       String startTime, String endTime, String sort){
       return iDetectRecordReport.getDetectListExcel(userId,dSn,breedId,industryId,sampleNo,planId,
                             startTime,endTime, null, "-1", sort);
    }

    /**
     * 检测记录参考值导出
     * @param userId
     * @param dSn
     * @param breedId
     * @param industryId
     * @param sampleNo
     * @param planId
     * @param startTime
     * @param endTime
     * @param sort
     * @return
     */
    @RequestMapping(value = "/report-detectreference",method = RequestMethod.GET)
    public ResponseEntity DetectReportInput(@ParamsValid(notNull = true)String userId,String dSn, String breedId, String industryId, String sampleNo, String planId,
                                       String startTime, String endTime, String sort){
        return iDetectRecordReport.getDetectInputListExcel(userId,dSn,breedId,industryId,sampleNo,planId,
                startTime,endTime, null, "-1", sort);
    }
    
    //统计用户的数据--之后会出现改变
    @RequestMapping(value="/report-detectUser",method = RequestMethod.GET)
    public ResponseEntity userMessages(@ParamsValid(notNull = true)String dSns,@ParamsValid(notNull=true)String startTime,@ParamsValid(notNull=true)String endTime) {
    	return iDetectRecordReport.getUserMessage(dSns, startTime, endTime);
    }
    /**
     * 
     * @描__述: 导出单个植株PDF信息
     * @方法功能：TODO
     * @方法名称：CitrusReport
     * @编写时间：2017年9月26日下午3:24:15
     * @开发者  ：张文歌
     * @方法参数：植株编号
     * @返回值：
     * @接口文档：
     */
    @RequestMapping(value="/report-citrus",method = RequestMethod.GET)
    public ResponseEntity CitrusReport(@ParamsValid(notNull = true)String plantId) {
    	return iDetectRecordReport.getCitrusPDF(plantId);
    }
}
