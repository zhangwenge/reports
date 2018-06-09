package cn.com.dplus.report.service.inter.v1;

import cn.com.dplus.project.entity.ResponseEntity;

public interface IPDFReportService {
	 /** 生成的黄龙病的PDF*/
    ResponseEntity getCitrusPDF(String plantNo);
}
