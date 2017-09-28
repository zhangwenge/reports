package cn.com.dplus.report.entity;

import java.util.List;

/**
 * @作用:
 * @所在包: cn.com.dplus.d.entity
 * @开发者: 余浪
 * @邮箱: 365617581@qq.com
 * @时间: 2017/4/5
 * @公司: 广州讯动网络科技有限公司
 */
public class DetectReport {
    /** 企业或公司名称 */
    private String company;

    /** 品种名称 */
    private String breedName;

    /** 用户自定义样品编号*/
    private String sampleNo;

    /** 检测时间 */
    private String detectTime;

    /** 不合格 */
    private String unqualified;

    /**	检测器温度  */
	private	Float detectorT;
	
	/** 检测器湿度  */
	private Float detectorH;
	
	/** 设备型号*/
	private String typeName;
	
	/** 检测设备的名称*/
	private String deviceUserLabel;
    
    public Float getDetectorT() {
		return detectorT;
	}

	public void setDetectorT(Float detectorT) {
		this.detectorT = detectorT;
	}

	public Float getDetectorH() {
		return detectorH;
	}

	public void setDetectorH(Float detectorH) {
		this.detectorH = detectorH;
	}

	/** 属性集 */
    private List<RKeyValue> attriSet;

    /** 定量结果集 */
    private List<DetectItem> rationList;

    /** 定性结果集 */
    private List<DetectItem> qualitativeList;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBreedName() {
        return breedName;
    }

    public void setBreedName(String breedName) {
        this.breedName = breedName;
    }

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public String getDetectTime() {
        return detectTime;
    }

    public void setDetectTime(String detectTime) {
        this.detectTime = detectTime;
    }

    public String getUnqualified() {
        return unqualified;
    }

    public void setUnqualified(String unqualified) {
        this.unqualified = unqualified;
    }

    public  List<RKeyValue> getAttriSet() {
        return attriSet;
    }

    public void setAttriSet( List<RKeyValue> attriSet) {
        this.attriSet = attriSet;
    }

    public List<DetectItem> getRationList() {
        return rationList;
    }

    public void setRationList(List<DetectItem> rationList) {
        this.rationList = rationList;
    }

    public List<DetectItem> getQualitativeList() {
        return qualitativeList;
    }

    public void setQualitativeList(List<DetectItem> qualitativeList) {
        this.qualitativeList = qualitativeList;
    }

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getDeviceUserLabel() {
		return deviceUserLabel;
	}

	public void setDeviceUserLabel(String deviceUserLabel) {
		this.deviceUserLabel = deviceUserLabel;
	}
    
}
