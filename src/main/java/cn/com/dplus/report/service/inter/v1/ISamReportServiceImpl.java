package cn.com.dplus.report.service.inter.v1;

import cn.com.dplus.project.entity.ResponseEntity;

/**
 * @Package :cn.com.dplus.report.service.inter.v1
 * @Administrator : 张伟杰
 * @E-mail : zhangwj@sondon.net
 * @Description :TODO
 * @Date : 2018/3/23 9:17
 */
public interface ISamReportServiceImpl {

    ResponseEntity samReport(String sampleSetId);
}
