package cn.com.dplus.report.entity;

import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import cn.com.dplus.project.entity.BaseEntity;


/**
 * 
 *  @类功能:	TODO	样品数据集
 *	@文件名:	SampleSet.java
 * 	@所在包:	cn.com.dplus.sp.entity
 *	@开发者:	黄先国
 * 	@邮_件:     hsamgle@qq.com
 *  @时_间:		2016年9月26日下午1:16:56
 *	@公_司:		广州讯动网络科技有限公司
 */
@Entity(value="SAMPLE",db="sample",queryNonPrimary=true,noClassnameStored=true)
public class Sample extends BaseEntity{

	
	private static final long serialVersionUID = 929858101371629112L;

	/** 系统产生的样品编号  */
	@Id
	private String _id;

	/** 用户自定义的样品编号 */
	private String sampleNo;
	
	/** 用户的id  */
	private String userId;
	
	/** 品种的id  */
	private String breedId;
	
	/** 品种名称  */
	private String breedName;
	
	/** 当前的设备sn  */
	private String dSn;
	
	/** 当前样品的创建时间 */
	private Long createTime;
	
	/** 当前样品的采集时间   */
	private Long lastCollectTime;
	
	/** 采集机器的IP */
	private String collectIp;
	
	/** 样品的备注  */
	private String sampleRemark;
	
	/** 状态   默认    正常1 ，0 未产生光谱 ，-1已删除 */
	private Integer state;
	
	/** 该样品下的指标和属性信息  */
	private List<SampleAttrValue> sampleAttrValues;
	
	/** 当前样品关联的样本信息  */
	private List<Specimen> specimens;
	
	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getSampleNo() {
		return sampleNo;
	}

	public void setSampleNo(String sampleNo) {
		this.sampleNo = sampleNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBreedId() {
		return breedId;
	}

	public void setBreedId(String breedId) {
		this.breedId = breedId;
	}

	public String getBreedName() {
		return breedName;
	}

	public void setBreedName(String breedName) {
		this.breedName = breedName;
	}

	public String getdSn() {
		return dSn;
	}

	public void setdSn(String dSn) {
		this.dSn = dSn;
	}

	public String getCollectIp() {
		return collectIp;
	}

	public void setCollectIp(String collectIp) {
		this.collectIp = collectIp;
	}

	public String getSampleRemark() {
		return sampleRemark;
	}

	public void setSampleRemark(String sampleRemark) {
		this.sampleRemark = sampleRemark;
	}

	
	public Integer getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public Long getLastCollectTime() {
		return lastCollectTime;
	}

	public void setLastCollectTime(long lastCollectTime) {
		this.lastCollectTime = lastCollectTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public List<SampleAttrValue> getSampleAttrValues() {
		return sampleAttrValues;
	}

	public void setSampleAttrValues(List<SampleAttrValue> sampleAttrValues) {
		this.sampleAttrValues = sampleAttrValues;
	}

	public List<Specimen> getSpecimens() {
		return specimens;
	}

	public void setSpecimens(List<Specimen> specimens) {
		this.specimens = specimens;
	}

}
