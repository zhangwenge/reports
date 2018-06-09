package cn.com.dplus.report.controller.v1;

import cn.com.dplus.project.entity.ResponseEntity;
import cn.com.dplus.report.service.inter.v1.ISamReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Package :cn.com.dplus.report.controller.v1
 * @Administrator : 张伟杰
 * @E-mail : zhangwj@sondon.net
 * @Description :TODO 样品导出控制器
 * @Date : 2018/3/23 9:12
 */
@RestController
public class SamReportController extends V1Controller{

    @Autowired
    private ISamReportServiceImpl samReportService;

    /**
     * @Author     : 张伟杰
     * @Description: TODO 模型管理平台样品导出
     * @Date       : 9:15 2018/3/26
     * @MethodNaem : samReport
     * @Return     : cn.com.dplus.project.entity.ResponseEntity
     * @param      ：sampleSetId
    */
    @RequestMapping(value = "/sample-report",method = RequestMethod.POST)
    public ResponseEntity samReport(String sampleSetId){
        return samReportService.samReport(sampleSetId);

    }
}
