package cn.com.dplus.report.service.inter.v1;


import cn.com.dplus.project.entity.ResponseEntity;
import cn.com.dplus.report.entity.mongodb.DetectionModel;

/**
 * @Package :cn.com.dplus.report.service.inter
 * @Administrator : 张伟杰
 * @E-mail : zhangwj@sondon.net
 * @Description :TODO
 * @Date : 2017/12/19 13:59
 */
public interface  ICsvReportService {

    ResponseEntity csvReport(String ids);

    ResponseEntity reportValueTemplate(String sampleSetId);
}
