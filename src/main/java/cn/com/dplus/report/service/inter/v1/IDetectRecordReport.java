package cn.com.dplus.report.service.inter.v1;

import cn.com.dplus.project.entity.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

/**
 * @作用:
 * @所在包: cn.com.dplus.d.service.inter.v1
 * @开发者: 余浪
 * @邮箱: 365617581@qq.com
 * @时间: 2017/3/31
 * @公司: 广州讯动网络科技有限公司
 */
public interface IDetectRecordReport {
	/** 导出word*/
    ResponseEntity detectRecordWord(String userId, String sampleId, String planId);
    
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
    
    /** 生成的黄龙病的PDF*/
    ResponseEntity getCitrusPDF(String plantId);
}
