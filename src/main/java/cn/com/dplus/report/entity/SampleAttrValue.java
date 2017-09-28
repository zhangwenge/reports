package cn.com.dplus.report.entity;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import cn.com.dplus.project.entity.BaseEntity;

/**
 *
 *  @类功能:	TODO	样品属性值表
 *	@文件名:	SampleAttrValue.java
 * 	@所在包:	cn.com.dplus.sp.entity
 *	@开发者:	黄先国
 * 	@邮_件:     hsamgle@qq.com
 *  @时_间:		2017年2月13日上午11:24:40
 *	@公_司:		广州讯动网络科技有限公司
 */
@Entity(value="SAMPLE_ATTRVALUE",db="sample",queryNonPrimary=true,noClassnameStored=true)
public class SampleAttrValue extends BaseEntity implements Comparable<SampleAttrValue>{

	private static final long serialVersionUID = -3319948623744002167L;

	// 基础类型
	public static final int BASE_TYPE = 0;
	
	// 指标类型
	public static final int  INDICATOR_TYPE = 1;
	
	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	@Id
	private String _id;
	
	/** 样品的id  */
	private String sampleId;

	/** 属性的id */
	private String attrId;

	/** 属性的名称  */
	private String attrName;

	/** 属性的值  */
	private Double attrValue;

	/** 属性的类型  */
	private Integer attrType;


	public String getSampleId() {
		return sampleId;
	}

	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}

	public String getAttrId() {
		return attrId;
	}

	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public Double getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(Double attrValue) {
		this.attrValue = attrValue;
	}

	public Integer getAttrType() {
		return attrType;
	}

	public void setAttrType(Integer attrType) {
		this.attrType = attrType;
	}
	

	public static class SimpleSampleIndicator{
		
		private String indicatorId;
		
		private String indicatorName;
		
		private int count;

		public String getIndicatorId() {
			return indicatorId;
		}

		public void setIndicatorId(String indicatorId) {
			this.indicatorId = indicatorId;
		}

		public String getIndicatorName() {
			return indicatorName;
		}

		public void setIndicatorName(String indicatorName) {
			this.indicatorName = indicatorName;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}
		
	}


	@Override
	public int compareTo(SampleAttrValue o) {
		return this.attrId.compareTo(o.getAttrId());
	}
}
