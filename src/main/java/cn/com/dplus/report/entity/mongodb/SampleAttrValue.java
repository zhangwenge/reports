package cn.com.dplus.report.entity.mongodb;
import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import cn.com.dplus.project.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@Data
@EqualsAndHashCode(callSuper = false)
@Entity(value="SAMPLE_ATTRVALUE",db="sample",queryNonPrimary=true,noClassnameStored=true)
public class SampleAttrValue extends BaseEntity implements Comparable<SampleAttrValue>{

	private static final long serialVersionUID = -3319948623744002167L;

	// 基础类型
	public static final int BASE_TYPE = 0;
	
	// 指标类型
	public static final int  INDICATOR_TYPE = 1;
	
	/** id的生成规则是 md5(sampleId,attrId)  */
	@Id
	private String _id;

	/** 样品的id  */
	@Indexed
	private String sampleId;

	/** 属性的id */
	@Indexed
	private String attrId;

	/** 属性的名称  */
	private String attrName;

	/** 属性的值  */
	private String attrValue;

	/** 属性的类型  */
	private Integer attrType;

	@Data
	public static class SimpleSampleIndicator{
		
		private String indicatorId;
		
		private String indicatorName;
		
		private int count;

		private List<Object> values;

	}

	@Override
	public int compareTo(SampleAttrValue o) {
		return this.attrId.compareTo(o.getAttrId());
	}
}
