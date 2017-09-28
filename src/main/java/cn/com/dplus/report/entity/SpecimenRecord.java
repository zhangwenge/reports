package cn.com.dplus.report.entity;


import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import cn.com.dplus.project.entity.BaseEntity;
import lombok.EqualsAndHashCode;

/**
 * 
 *  @类功能:	TODO	检测记录中样本的检测记录
 *	@文件名:	SpecimenRecord.java
 * 	@所在包:	cn.com.dplus.d.entity
 *	@开发者:	黄先国
 * 	@邮_件:     hsamgle@qq.com
 *  @时_间:		2017年3月16日上午10:33:59
 *	@公_司:		广州讯动网络科技有限公司
 */
@Entity(value="SPECIMEN_RECORD",db="detect-record",queryNonPrimary=true,noClassnameStored=true)
@EqualsAndHashCode(callSuper = false)
public class SpecimenRecord extends BaseEntity implements Comparable<SpecimenRecord>{

	private static final long serialVersionUID = -941656511624813984L;

	/** 样本的id  */
	@Id
	private String _id;
	
	/** 关联的样品记录的id  */
	@Indexed
	private String sampleRecordId;
	
	/** 当前样本的检测时间  */
	private Long detectTime;
	
	/** 检测时的设备状态  */
	private List<DevEnvFactor> devEnvFactors;
	
	/** 检测的详情  */
	@Embedded
	private List<DetectResult> results;

	public List<DevEnvFactor> getDevEnvFactors() {
		return devEnvFactors;
	}

	public void setDevEnvFactors(List<DevEnvFactor> devEnvFactors) {
		this.devEnvFactors = devEnvFactors;
	}

	/** 模型校验记录  */
	private Double modelVerifyValue;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getSampleRecordId() {
		return sampleRecordId;
	}

	public void setSampleRecordId(String sampleRecordId) {
		this.sampleRecordId = sampleRecordId;
	}

	public Long getDetectTime() {
		return detectTime;
	}

	public void setDetectTime(Long detectTime) {
		this.detectTime = detectTime;
	}

	public List<DetectResult> getResults() {
		return results;
	}

	public void setResults(List<DetectResult> results) {
		this.results = results;
	}

	public Double getModelVerifyValue() {
		return modelVerifyValue;
	}
	
	public void setModelVerifyValue(Double modelVerifyValue) {
		this.modelVerifyValue = modelVerifyValue;
	}
	
	@Override
	public int compareTo(SpecimenRecord o) {
		return this.detectTime.compareTo(o.getDetectTime());
	}


	
	
	
}

