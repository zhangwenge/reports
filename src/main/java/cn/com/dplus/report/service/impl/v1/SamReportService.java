package cn.com.dplus.report.service.impl.v1;

import cn.com.dplus.mongodb.SDMongo;
import cn.com.dplus.mongodb.entity.Condition;
import cn.com.dplus.mongodb.entity.Operators;
import cn.com.dplus.project.constant.Code;
import cn.com.dplus.project.entity.ResponseEntity;
import cn.com.dplus.project.utils.DateTimeUtils;
import cn.com.dplus.report.entity.mongodb.*;
import cn.com.dplus.report.entity.others.Content;
import cn.com.dplus.report.http.API;
import cn.com.dplus.report.service.inter.v1.ISamReportServiceImpl;
import cn.com.dplus.report.utils.CsvUtil;
import cn.com.dplus.report.utils.FileUtil;
import cn.com.dplus.report.utils.PoiExcelExportUtil;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Package :cn.com.dplus.report.service.impl.v1
 * @Administrator : 张伟杰
 * @E-mail : zhangwj@sondon.net
 * @Description :TODO
 * @Date : 2018/3/23 9:17
 */
@Service
public class SamReportService implements ISamReportServiceImpl{

    /**
     * @Author     : 张伟杰
     * @Description: TODO 模型管理平台样品导出
     * @Date       : 9:26 2018/3/26
     * @MethodNaem : samReport
     * @Return     : cn.com.dplus.project.entity.ResponseEntity
     * @param      ：sampleSetId
    */
    @Override
    public ResponseEntity samReport(String sampleSetId) {
        try {

            List<String> nameList = new ArrayList<>();
            Condition [] cons = new Condition[]{
                    new Condition("sampleSetId", sampleSetId),
                    new Condition("state",1)
            };

            //查询样品集内的样品
            List<SampleInPool> sampleInPools = SDMongo.find(SampleInPool.class,cons);
            if (sampleInPools.isEmpty()){
                return new ResponseEntity(Code.NO_RESULT,"样品集内没有样品");
            }
            SampleSet sampleSet = SDMongo.findOne(SampleSet.class, sampleSetId);
            String sn = sampleSet.getDSn();
            //判断样品集里面的指标是定量还是定性
//            List<String> sampleIdList = sampleInPools.stream().map(a -> a.getSampleId()).collect(Collectors.toList());
//            List<Sample> samples = SDMongo.find(Sample.class, new Condition("_id", Operators.in, sampleIdList));
//            List<String> breedIdList = samples.stream().map(a -> a.getBreedId()).collect(Collectors.toList());
//            List<Indicator> indicatorList = SDMongo.find(Indicator.class, new Condition("breedId", Operators.in, breedIdList));

            List<SampleSetIndicator> sampleSetIndicatorList = SDMongo.find(SampleSetIndicator.class, new Condition("sampleSetId", sampleSetId));
            List<String> indicatorIdList = sampleSetIndicatorList.stream().map(a -> a.getIndicatorId()).collect(Collectors.toList());
            List<Indicator> indicatorList = SDMongo.find(Indicator.class, new Condition("_id", Operators.in, indicatorIdList));
            List<Indicator> indicators = new ArrayList<>();
            for (Indicator indicator : indicatorList) {
                if (indicator.getIndicatorType() == 0&&indicator.getState() ==1){
                    //定量
                    indicators.add(indicator);
                }else if (indicator.getIndicatorType() == 1&&indicator.getState() ==1){
                    //定性
                    String fileName = ReportCsv(sampleInPools, sn, indicator);
                    nameList.add(API.REPORT_PATH +File.separator +fileName);
                }
            }
            if (indicators.size() != 0){
                String fileName = ExcelRepor(sampleInPools, sn, indicators);
                nameList.add(API.REPORT_PATH +File.separator +fileName);
            }

            String time = DateTimeUtils.getTime();
            String zipName = time.replace(":", "-").replace(" ", "_") + ".zip";
            //文件打包
            OutputStream os = new BufferedOutputStream(new FileOutputStream(API.REPORT_PATH + File.separator + zipName));
            ZipOutputStream zos = new ZipOutputStream(os);
            byte[] buf = new byte[8192];
            int len;
            for (String filename : nameList) {
                File file = new File(filename);
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
            return new ResponseEntity(Code.HTTP_500,e.getMessage());
        }
    }

    //导出定性的样品集-csv
    public String ReportCsv(List<SampleInPool> sampleInPools,String sn,Indicator indicator){
        try {
            //设置头格式
            List<Object> head = new ArrayList<>();

            //设置CSV文件内容
            List<List<Object>> dataList = new ArrayList<>();

            head.add("次数");
            head.add("标签");
            head.add("样品编号");
            head.add("设备SN");

            //波长
            DevWaveLength waveLength = SDMongo.findOne(DevWaveLength.class, new Condition("_id", sn));
            if (waveLength != null){
                TreeSet<Float> length = waveLength.getWaveLength();
                for (Float aFloat : length) {
                    head.add(aFloat);
                }
            }

            List<String> vale = new ArrayList<>();
            vale.add("0");
            vale.add("1");
            //拥有同一指标的样品
            //List<String> sampleIdList = getSampleIdList(sampleInPools, indicator);
            List<Sample> samples = getSamples(sampleInPools, sn);

            for (Sample sample : samples) {
                String sampleNo = sample.getSampleNo();
                Condition[] conds = new Condition[]{
                        new Condition("sampleId",sample.get_id()),
                        new Condition("dSn",sn),
                        new Condition("state",1)
                };
                List<Specimen> specimenList = SDMongo.find(Specimen.class, conds);
                Object attrValue;

                //理化值
                Condition[] conditions = new Condition[]{
                        new Condition("sampleId",sample.get_id()),
                        new Condition("attrValue",Operators.in,vale)
                };
                SampleAttrValue sampleAttrValue = SDMongo.findOne(SampleAttrValue.class, conditions);
                if (sampleAttrValue!=null){
                    attrValue = sampleAttrValue.getAttrValue();
                }else{
                    attrValue = "";
                }
                //样本列表
                for(Specimen specimen:specimenList){
                    //内容第一行
                    List<Object> first = new ArrayList<>();
                    //样品编号
                    first.add(sampleNo);
                    first.add(attrValue);
                    first.add(sampleNo);
                    first.add(sn);
                    //光谱的吸光度
                    Spectrum spectrum = SDMongo.findOne(Spectrum.class, specimen.get_id());
                    if (spectrum!=null) {
                        List<Float> floats = spectrum.getSpectrum();
                        for (Float aFloat : floats) {
                            first.add(aFloat);
                        }
                    }
                dataList.add(first);
                }
            }
            //获取时间
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
            String createdate = sdf.format(date);

            SampleSet sampleSet = SDMongo.findOne(SampleSet.class, sampleInPools.get(0).getSampleSetId());
            String fileName ="Sondon_"+sn+"_"+sampleSet.getIndustryName()+"_"+sampleSet.getBreedName()+"_00_"+indicator.getIndicatorName()+"_"+createdate;
            CsvUtil.createCSVFile(head, dataList, API.REPORT_PATH, fileName);
            return fileName+".csv";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //导出定性的样品集-Excel
    public String ExcelRepor(List<SampleInPool> sampleInPools,String sn,List<Indicator> indicators){
        try {
            //设置CSV文件内容
            List<List<Object>> dataList = new ArrayList<>();
            //文本内容第一行
            List<Object> first = new ArrayList<>();
            first.add("样品编号");

            List<Object> data = new ArrayList<>();
            //波长
            DevWaveLength waveLength = SDMongo.findOne(DevWaveLength.class, new Condition("_id", sn));
            if (waveLength != null){
                data.add(waveLength.getWaveLength());
            }
            //拥有同一指标的样品
            //List<String> sampleIdList = sampleInPools.stream().map(SampleInPool::getSampleId).collect(Collectors.toList());
            List<Sample> samples = getSamples(sampleInPools, sn);
            List<String> sampleIdList = samples.stream().map(Sample::get_id).collect(Collectors.toList());
            List<String> sampleIds = new ArrayList<>();
            for (Sample sample : samples) {
                //样品编号
                Condition[] conds = new Condition[]{
                        new Condition("sampleId",sample.get_id()),
                        new Condition("dSn",sn),
                        new Condition("state",1)
                };
                List<Specimen> specimenList = SDMongo.find(Specimen.class, conds);

                for(Specimen specimen:specimenList){
                    Spectrum spectrum = SDMongo.findOne(Spectrum.class, specimen.get_id());
                    first.add(sample.getSampleNo());
                    sampleIds.add(sample.get_id());
                    if (spectrum!=null) {
                        data.add(spectrum.getSpectrum());
                    }
                }
            }
            //设置头内容
            for (Indicator indicator : indicators) {
                List<Object> headList = new ArrayList<>();
                headList.add(indicator.getIndicatorName());
                //理化值
                for(String sampleId : sampleIds){
                        Condition[] conditions = new Condition[]{
                                new Condition("sampleId",sampleId),
                                new Condition("attrId",indicator.get_id())
                        };
                        SampleAttrValue sampleAttrValue = SDMongo.findOne(SampleAttrValue.class, conditions);
                        if (sampleAttrValue !=null) {
                            headList.add(sampleAttrValue.getAttrValue());
                        }else{
                            headList.add("");
                        }

                }

                dataList.add(headList);
            }
            Condition[] conds = new Condition[]{
                    new Condition("sampleId",Operators.in,sampleIdList),
                    new Condition("dSn",sn),
                    new Condition("state",1)
            };
            long macthCount = SDMongo.getMacthCount(Specimen.class, conds);
            dataList.add(first);
            int count = 1;
            TreeSet<Float> wave = (TreeSet<Float>)data.get(0);
            Iterator it =wave.iterator();
            while(it.hasNext()){
                //文本内容第二行
                List<Object> second = new ArrayList<>();
                second.add(it.next());
                for (int j = 1 ; j <=macthCount;j++) {
                    List<Float> sampum = (List<Float>) data.get(j);
                    second.add(sampum.get(count-1));
                }
                dataList.add(second);
                count++;
            }

            //获取时间
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
            String createdate = sdf.format(date);

            SampleSet sampleSet = SDMongo.findOne(SampleSet.class, sampleInPools.get(0).getSampleSetId());
            String fileName ="Sondon_"+sn+"_"+sampleSet.getIndustryName()+"_"+sampleSet.getBreedName()+"_00_"+createdate+".xlsx";

            Content content = new Content();
            content.setBodyList(dataList);
            content.setBreedName("样品集导出");
            byte[] bytes = PoiExcelExportUtil.exportSelfSampleSet(content, fileName);
            FileUtil.saveFile(bytes, API.REPORT_PATH, fileName);
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }


    //拥有同一指标的样品
    public List<String> getSampleIdList(List<SampleInPool> sampleInPools,Indicator indicator){
        try {
            List<String> sampleIdList = new ArrayList<>();
            for (SampleInPool sampleInPool : sampleInPools) {
                List<SampleAttrValue> sampleAttrValues = SDMongo.find(SampleAttrValue.class, new Condition("sampleId", sampleInPool.getSampleId()));
                if (!sampleAttrValues.isEmpty()){
                    for (SampleAttrValue sampleAttrValue : sampleAttrValues) {
                        if (sampleAttrValue.getAttrId().equals(indicator.get_id())){
                            sampleIdList.add(sampleAttrValue.getSampleId());
                        }
                    }
                }
            }
            return sampleIdList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Sample> getSamples(List<SampleInPool> sampleInPools, String sn) throws Exception {
        List<String> sampleIdList = sampleInPools.stream().map(SampleInPool::getSampleId).collect(Collectors.toList());
        Condition[] conds = new Condition[]{
                new Condition("_id", Operators.in,sampleIdList),
                new Condition("dSn",sn),
                new Condition("state",1),
                new Condition("-lastCollectTime",Operators.sort)
        };
        return SDMongo.find(Sample.class, conds);
    }
}

