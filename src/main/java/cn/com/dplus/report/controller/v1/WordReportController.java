package cn.com.dplus.report.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.com.dplus.project.annotation.ParamsValid;
import cn.com.dplus.project.entity.ResponseEntity;
import cn.com.dplus.report.service.inter.v1.IWordReportService;

@RestController
public class WordReportController extends V1Controller{
	
	@Autowired
	private IWordReportService iWordReportService;
	/**
	 * 
	 * @描__述: word 模板导出
	 * @方法功能：TODO
	 * @方法名称：getWord
	 * @编写时间：2017年11月14日上午11:11:34
	 * @开发者  ：张文歌
	 * @方法参数：
	 * @返回值：
	 * @接口文档：
	 */
	@RequestMapping(value="/report-detect")
	public ResponseEntity getWord(
			@ParamsValid(notNull = true)String userId,
            @ParamsValid(notNull = true)String sampleId,
            String planId) {
		return iWordReportService.getWord(userId, sampleId, planId);
	}
}
