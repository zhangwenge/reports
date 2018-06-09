package cn.com.dplus.report.service.inter.v1;

import cn.com.dplus.project.entity.ResponseEntity;

public interface IWordReportService {
	ResponseEntity getWord(String userId,String sampleId,String planId);
}
