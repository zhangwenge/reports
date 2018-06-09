package cn.com.dplus.report.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.com.dplus.project.annotation.ParamsValid;
import cn.com.dplus.project.entity.ResponseEntity;
import cn.com.dplus.report.service.inter.v1.IExcelRecordReport;

/**
 * @作用: 检测报表控制层
 * @所在包: cn.com.dplus.d.controller.v1
 * @author: 余浪
 * @邮箱: 365617581@qq.com
 * @时间: 2017/3/31
 * @公司: 广州讯动网络科技有限公司
 */
@RestController
public class ExcelReportController extends V1Controller{

    @Autowired
    private IExcelRecordReport iExcelRecordReport;

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
    public ResponseEntity detectReport(@ParamsValid(notNull = true)String userId,String dSn, String breedId, String industryId, String sampleNo, String planId,
                                       String startTime, String endTime, String sort){
       return iExcelRecordReport.getDetectListExcel(userId,dSn,breedId,industryId,sampleNo,planId,
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
    public ResponseEntity detectReportInput(@ParamsValid(notNull = true)String userId,String dSn, String breedId, String industryId, String sampleNo, String planId,
                                       String startTime, String endTime, String sort){
        return iExcelRecordReport.getDetectInputListExcel(userId,dSn,breedId,industryId,sampleNo,planId,
                startTime,endTime, null, "-1", sort);
    }
    
    //统计用户的数据--之后会出现改变
    @RequestMapping(value="/report-detectUser",method = RequestMethod.GET)
    public ResponseEntity userMessages(@ParamsValid(notNull = true)String dSns,@ParamsValid(notNull=true)String startTime,@ParamsValid(notNull=true)String endTime) {
    	return iExcelRecordReport.getUserMessage(dSns, startTime, endTime);
    }
    
    /**
     * 
     * @描__述: 参比记录导出
     * @方法功能：TODO
     * @方法名称：reportReference
     * @编写时间：2017年11月6日下午4:00:30
     * @开发者  ：张文歌
     * @方法参数：
     * @返回值：
     * @接口文档：
     */
    @RequestMapping(value="/report-reference",method = RequestMethod.GET)
    public ResponseEntity reportReference(@ParamsValid(notNull = true)String dSns,
    									 String ids) {
    	return iExcelRecordReport.getReportReference(dSns,ids);
    }
    
    /**
     * 
     * @描__述: 自检模型导出
     * @方法功能：TODO
     * @方法名称：reportSelftest
     * @编写时间：2017年11月8日上午9:18:11
     * @开发者  ：张文歌
     * @方法参数：
     * @返回值：
     * @接口文档：
     */
    @RequestMapping(value="/report-Selftest",method = RequestMethod.GET)
    public ResponseEntity reportSelftest(@ParamsValid(notNull = true)String dSns,
    		                             String ids) {
    	return iExcelRecordReport.getSelfTest(dSns,ids);
    }
}
