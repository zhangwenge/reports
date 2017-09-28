package cn.com.dplus.report.entity;

import cn.com.dplus.project.entity.BaseEntity;

/**
 * 
 *  @类功能:	TODO	设备环境变量
 *	@文件名:	DevEnvFactor.java
 * 	@所在包:	cn.com.dplus.sp.entity
 *	@开发者:	黄先国
 * 	@邮_件:     hsamgle@qq.com
 *  @时_间:		2017年2月13日上午11:17:43
 *	@公_司:		广州讯动网络科技有限公司
 */
public class DevEnvFactor extends BaseEntity{

	private static final long serialVersionUID = -1221722352553789617L;
	
	/** 设备当前状态的id  */
	private String devStateId;
	
	/**	检测器温度  */
	private	Float detectorT;
	
	/** 检测器湿度  */
	private Float detectorH;

	public String getDevStateId() {
		return devStateId;
	}

	public void setDevStateId(String devStateId) {
		this.devStateId = devStateId;
	}

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

	/** 环境变量的名称  */
	private String name;
	
	/** 环境变量的值  */
	private Object value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
}
