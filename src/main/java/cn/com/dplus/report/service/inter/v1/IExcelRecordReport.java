package cn.com.dplus.report.service.inter.v1;

import javax.servlet.http.HttpServletResponse;

import cn.com.dplus.project.entity.ResponseEntity;

/**
 * @作用:
 * @所在包: cn.com.dplus.d.service.inter.v1
 * @开发者: 余浪
 * @邮箱: 365617581@qq.com
 * @时间: 2017/3/31
 * @公司: 广州讯动网络科技有限公司
 */
public interface IExcelRecordReport {
    /** 导出的检测记录*/
    ResponseEntity getDetectListExcel(String userId, String dSn, String breedId, String industryId, String sampleNo, String planId,
                                      String startTime, String endTime, String pNow, String pSize, String sort
                                      );
    /** 导出理化值*/
    ResponseEntity getDetectInputListExcel(String userId, String dSn, String breedId, String industryId, String sampleNo, String planId,
                                           String startTime, String endTime, String pNow, String pSize, String sort);
   
    ResponseEntity downLoad(String file,HttpServletResponse response);
    
    /** 导出用户的基本信息*/
    ResponseEntity getUserMessage(String dSn, String startTime,String endTime);
    
    /** 参比用户导出*/
    ResponseEntity getReportReference(String dSns,String ids);
    
    /** 自检信息导出*/
    ResponseEntity getSelfTest(String dSns,String ids);
}
