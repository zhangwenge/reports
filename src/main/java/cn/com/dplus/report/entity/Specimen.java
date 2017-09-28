package cn.com.dplus.report.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.com.dplus.project.entity.BaseEntity;
import cn.com.dplus.project.utils.JsonUtil;

/**
 * 
 *  @类功能:	TODO		样本的实体
 *	@文件名:	Specimen.java
 * 	@所在包:	cn.com.dplus.sp.entity
 *	@开发者:	黄先国
 * 	@邮_件:     hsamgle@qq.com
 *  @时_间:		2017年2月6日下午3:30:13
 *	@公_司:		广州讯动网络科技有限公司
 */
public class Specimen extends BaseEntity {

	private static final long serialVersionUID = 7587757003211138713L;

	/** 样本的id  */
	private String id;
	
	/** 所属样品的id  */
	private String sampleId;
	
	/** 采集的时间   */
	private Timestamp collectTime;
	
	/** 样品集id  */
	private String sampleSetId;
	
	/**  设备的sn  */
	private String dSn;
	
	/** 光谱数据  */
	private TreeMap<Integer, Float> spectrum;
	
	/** 存放在Hbase 中的光谱数据  */
	private transient String spectrumJson;
	
	/** 采集光谱时的环境变量  */
	private List<DevEnvFactor> devEnvFactors;

	/** 存放在和base中的设备环境变量json数据  */
	private transient String devEnvFactorsJson;

	private String userId;
	
	public Specimen() {}
	
	public Specimen(String id, String sampleId) {
		this.id = id;
		this.sampleId = sampleId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSampleId() {
		return sampleId;
	}

	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}

	public Timestamp getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Timestamp collectTime) {
		this.collectTime = collectTime;
	}

	public String getdSn() {
		return dSn;
	}

	public void setdSn(String dSn) {
		this.dSn = dSn;
	}

	public TreeMap<Integer, Float> getSpectrum() {
		return spectrum;
	}

	public void setSpectrum(TreeMap<Integer, Float> spectrum) {
		this.spectrum = spectrum;
		this.spectrumJson = JsonUtil.toJson(this.spectrum);
	}

	public List<DevEnvFactor> getDevEnvFactors() {
		return devEnvFactors;
	}

	public void setDevEnvFactors(List<DevEnvFactor> devEnvFactors) {
		this.devEnvFactors = devEnvFactors;
		Map<String, List<Object>> factors = new HashMap<String, List<Object>>();
		for (DevEnvFactor devEnvFactor : devEnvFactors) {
			String key = devEnvFactor.getName();
			Object value = devEnvFactor.getValue();
			List<Object> objects = factors.get(key);
			if(objects==null){
				objects = new ArrayList<Object>();
			}
			objects.add(value);
			factors.put(key, objects);
		}
		this.devEnvFactorsJson = JsonUtil.toJson(factors);
	}
	
	public String getSpectrumJson() {
		return spectrumJson;
	}

	public String getDevEnvFactorsJson() {
		return devEnvFactorsJson;
	}

	public void setSpectrumJson(String spectrumJson) {
		this.spectrumJson = spectrumJson;
	}

	public void setDevEnvFactorsJson(String devEnvFactorsJson) {
		this.devEnvFactorsJson = devEnvFactorsJson;
	}

	public String getSampleSetId() {
		return sampleSetId;
	}

	public void setSampleSetId(String sampleSetId) {
		this.sampleSetId = sampleSetId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
