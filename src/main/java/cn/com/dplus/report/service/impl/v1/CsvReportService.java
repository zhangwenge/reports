package cn.com.dplus.report.service.impl.v1;

import cn.com.dplus.mongodb.SDMongo;
import cn.com.dplus.mongodb.entity.Condition;
import cn.com.dplus.mongodb.entity.Operators;
import cn.com.dplus.project.constant.Code;
import cn.com.dplus.project.entity.ResponseEntity;
import cn.com.dplus.project.utils.DateTimeUtils;
import cn.com.dplus.report.constant.ReportCode;
import cn.com.dplus.report.entity.mongodb.*;
import cn.com.dplus.report.http.API;
import cn.com.dplus.report.service.inter.v1.ICsvReportService;
import cn.com.dplus.report.service.inter.v1.IServiceApi;
import cn.com.dplus.report.utils.CsvUtil;
import cn.com.dplus.report.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * @Package :cn.com.dplus.report.service.impl.v1
 * @Administrator : 张伟杰
 * @E-mail : zhangwj@sondon.net
 * @Description :TODO
 * @Date : 2017/12/19 14:03
 */
@Service
public class CsvReportService implements ICsvReportService {

    @Autowired
    private IServiceApi serviceApi;

    /**
     * @Author : 张伟杰
     * @Description: TODO 导出用户模型数据
     * @Date : 15:19 2017/12/21
     * @MethodNaem : csvReport
     * @Return : cn.com.dplus.project.entity.ResponseEntity
     */
    @Override
    public ResponseEntity csvReport(String ids) {
        try {
            List<DetectionModel> models = serviceApi.getModels(ids);

            if (models.isEmpty()) {
                return new ResponseEntity(Code.NO_RESULT, "模型不存在");

            }
            List<String> nameList = new ArrayList<>();


            for (DetectionModel detectionModel : models) {
                //设置头格式
                List<Object> head = new ArrayList<>();

                //设置CSV文件内容
                List<List<Object>> dataList = new ArrayList<>();
                //第二行
                List<Object> second = new ArrayList<>();

                //设置文件名称
                String fileName =
                        detectionModel.getDSn() + "_" + detectionModel.getIndustryName() + "_" +
                                detectionModel.getBreedName() + "_" + detectionModel.getIndicatorName() + "_" + DateTimeUtils.getNowInMillis();
                head.add(detectionModel.getIndicatorId());


                List<String> sampleIdsList = new ArrayList<>();
                if (detectionModel.getSampleSetId() == null) {
                    head.addAll(new ArrayList<>());
                }
                else {
                    //样品id
                    List<SampleInPool> sampleInPools = SDMongo.find(SampleInPool.class, new Condition("sampleSetId", detectionModel.getSampleSetId()));
                    sampleIdsList = sampleInPools.stream().map(a -> a.getSampleId()).collect(Collectors.toList());
                }
                //第二行
                second.add(" ");
                Condition[] conditions = new Condition[]{
                        new Condition("sampleId", Operators.in, sampleIdsList),
                        new Condition("state",1)
                };
                List<Specimen> specimenList = SDMongo.find(Specimen.class,conditions);
                List<String> specimenIds = specimenList.stream().map(a -> a.get_id()).collect(Collectors.toList());


                List<List<Float>> spectrumValue = new ArrayList<>(); //光谱集合
                for (String specimenId : specimenIds) {
                    //理化值
                    Specimen specimen = SDMongo.findOne(Specimen.class, specimenId);
                    if (specimen!=null){
                        //查询理化值存入表头
                        Condition[] condition = new Condition[]{
                                new Condition("sampleId", specimen.getSampleId()),
                                new Condition("attrId", detectionModel.getIndicatorId()),
                                new Condition("attrType", 1)
                        };
                        List<SampleAttrValue> sampleAttrValues = SDMongo.find(SampleAttrValue.class, condition);
                        if (!sampleAttrValues.isEmpty()){
                            String attrValue = sampleAttrValues.stream().findFirst().get().getAttrValue();
                            if (attrValue!=null){
                                second.add(specimenId);
                                head.add(attrValue);
                                Spectrum spectrum = SDMongo.findOne(Spectrum.class,specimenId);
                                spectrumValue.add(spectrum==null?new ArrayList<>():spectrum.getSpectrum());
                            }
                        }
                    }
                }
                dataList.add(second);
                //光谱数据
                if (!spectrumValue.isEmpty()){
                    for (int i = 0; i < spectrumValue.get(0).size(); i++) {
                        List<Object> light = new ArrayList<>();
                        light.add(950 + i);
                        for (int j = 0; j < spectrumValue.size(); j++) {
                            light.add(spectrumValue.get(j).isEmpty()?"":spectrumValue.get(j).get(i));
                        }
                        dataList.add(light);
                    }
                }
                File csvFile = CsvUtil.createCSVFile(head, dataList, API.REPORT_PATH, fileName);
                nameList.add(API.REPORT_PATH + File.separator + fileName);
            }
            String time = DateTimeUtils.getTime();
            String zipName = time.replace(":", "-").replace(" ", "_") + ".zip";
            //文件打包
            OutputStream os = new BufferedOutputStream(new FileOutputStream(API.REPORT_PATH + File.separator + zipName));
            ZipOutputStream zos = new ZipOutputStream(os);
            byte[] buf = new byte[8192];
            int len;
            for (String filename : nameList) {
                File file = new File(filename + ".csv");
                if (!file.isFile()) {
                    continue;
                }
                ZipEntry ze = new ZipEntry(file.getName());
                zos.putNextEntry(ze);
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                while ((len = bis.read(buf)) > 0) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
            }
            zos.close();

            return new ResponseEntity(API.EXPORT_PATH + zipName);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(Code.HTTP_500, e.getMessage());
        }
    }

    /**
     * @author 张文歌
     * @date 2018/4/3 0003 下午 2:28
     * @Parameters [sampleSetId]
     * @return cn.com.dplus.project.entity.ResponseEntity
     * @description 导出理化值模板
     */
    @Override
    public ResponseEntity reportValueTemplate(String sampleSetId) {
        try {
            //样品集信息
            SampleSet sampleSet = SDMongo.findOne(SampleSet.class, sampleSetId);
            if (sampleSet == null) {
                return new ResponseEntity(ReportCode.NO_SAMPLESET);
            }
            //样品集-指标
            List<SampleSetIndicator> sampleSetIndicators = SDMongo.find(SampleSetIndicator.class, new Condition("sampleSetId", sampleSetId));
            List<Indicator> indicatorList = null;
            if (sampleSetIndicators == null || sampleSetIndicators.size() == 0) {
                indicatorList = SDMongo.find(Indicator.class,new Condition("breedId",sampleSet.getBreedId()));
            }else{
                List<String> indicatorIds = sampleSetIndicators.stream().map(SampleSetIndicator::getIndicatorId).collect(Collectors.toList());
                indicatorList = SDMongo.find(Indicator.class, new Condition("_id", Operators.in, indicatorIds));
            }

            //系统--自建
            List<String> firstRow = new ArrayList<>();
            firstRow.add("");
            //指标
            List<String> twoRow = new ArrayList<>();
            twoRow.add("样品编号");

            for (Indicator indicator : indicatorList) {
                if ("0".equals(indicator.getUserId())) {
                    firstRow.add("系统");
                } else {
                    firstRow.add("自建");
                }
                twoRow.add(indicator.getIndicatorName());
            }
            String[] firstArray = new String[firstRow.size()];
            String[] twoArray = new String[twoRow.size()];

            firstArray = firstRow.toArray(firstArray);
            twoArray = twoRow.toArray(twoArray);

            byte[] bytes = CsvUtil.exportValueTemplate(firstArray, twoArray);

            //导出样品集模板-名称
            String fileName = sampleSet.getDSn() + "_" + sampleSet.getIndustryName() + "_" + sampleSet.getBreedName() + ".csv";
            FileUtil.saveFile(bytes, API.REPORT_PATH, fileName);
            return new ResponseEntity(API.EXPORT_PATH + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(ReportCode.FAILED, ReportCode.FILED_MSG);
        }

    }
}
