package cn.com.dplus.report.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.com.dplus.project.entity.ResponseEntity;
import cn.com.dplus.report.service.inter.v1.IPDFReportService;

@RestController
public class PDFReportController extends V1Controller{
	@Autowired
	private IPDFReportService iPDFReportService;
	
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
    @RequestMapping(value="/report-citrus/{plantNo}",method = RequestMethod.GET)
    public ResponseEntity csitrusReport(@PathVariable("plantNo") String plantNo) {
    	return iPDFReportService.getCitrusPDF(plantNo);
    }
}
