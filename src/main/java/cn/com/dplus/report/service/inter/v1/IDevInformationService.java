package cn.com.dplus.report.service.inter.v1;

import cn.com.dplus.project.entity.ResponseEntity;

public interface IDevInformationService {
	/**
	 * 
	 * @描__述: 社备详细信息导出
	 * @方法功能：TODO
	 * @方法名称：getDevInformation
	 * @编写时间：2017年11月8日下午4:36:15
	 * @开发者  ：张文歌
	 * @方法参数：logo=0 页面列表   logo=1导出Excel
	 * @返回值：
	 * @接口文档：
	 */
	ResponseEntity getDevInformation(String dSn,
			String userId,String startTime, String endTime,Integer pNow,Integer pSize,String sort,Integer logo);
}
