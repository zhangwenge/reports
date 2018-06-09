package cn.com.dplus.report.controller.v1;

import cn.com.dplus.project.annotation.ParamsValid;
import cn.com.dplus.project.constant.Code;
import cn.com.dplus.project.entity.ResponseEntity;
import cn.com.dplus.report.entity.mongodb.DetectionModel;
import cn.com.dplus.report.service.inter.v1.ICsvReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Package :cn.com.dplus.report.controller.v1
 * @Administrator : 张伟杰
 * @E-mail : zhangwj@sondon.net
 * @Description :TODO CSV导出
 * @Date : 2017/12/19 13:57
 */
@RestController
public class CsvReportController extends V1Controller{
    @Autowired
    private ICsvReportService csvReportService;

    /**
     * @Author     : 张伟杰
     * @Description: TODO 导出用户模型数据
     * @Date       : 15:18 2017/12/21
     * @MethodNaem : csvReport
     * @Return     : cn.com.dplus.project.entity.ResponseEntity
     * @param      ：model
    * @param      ：startTime
    * @param      ：endTime
    * @param      ：pNow
    * @param      ：pSize
    * @param      ：sort
    * @param      ：ids
    */
    @RequestMapping(value = "/report-csv",method = RequestMethod.POST)
    public ResponseEntity csvReport(String ids) {
        return csvReportService.csvReport(ids);
    }

    /**
     * @author 张文歌
     * @date 2018/4/3 0003 下午 2:27
     * @Parameters [sampleSetId]
     * @return cn.com.dplus.project.entity.ResponseEntity
     * @description 导出理化值模板 csv文件
     */
    @RequestMapping(value = "/report/value/template/{sampleSetId}",method = RequestMethod.GET)
    public ResponseEntity reportValueTemplate(@PathVariable("sampleSetId") String sampleSetId){
        return csvReportService.reportValueTemplate(sampleSetId);
    }

}

