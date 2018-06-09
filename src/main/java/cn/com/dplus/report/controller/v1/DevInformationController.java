package cn.com.dplus.report.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.com.dplus.project.annotation.ParamsValid;
import cn.com.dplus.project.entity.ResponseEntity;
import cn.com.dplus.report.service.inter.v1.IDevInformationService;

@RestController
public class DevInformationController extends V1Controller{
	
	@Autowired
	private IDevInformationService iDevInformationService;
	
	/**
	 * 
	 * @描__述: 设备信息统计和Excel的导出
	 * @方法功能：TODO
	 * @方法名称：devAllInformation
	 * @编写时间：2017年11月14日上午11:09:16
	 * @开发者  ：张文歌
	 * @方法参数：
	 * @返回值：
	 * @接口文档：
	 */
	@RequestMapping(value="/devAllInformation",method=RequestMethod.GET)
	public ResponseEntity devAllInformation(String dSn,
			String userId,String startTime, String endTime,Integer pNow,Integer pSize,String sort,@ParamsValid(notNull=true,reg="^[0-1]{1}$")Integer logo) {
		return iDevInformationService.getDevInformation(dSn, userId, startTime, endTime, pNow, pSize, sort, logo);
	}
}
