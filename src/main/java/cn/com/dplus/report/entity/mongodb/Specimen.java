package cn.com.dplus.report.entity.mongodb;

import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import cn.com.dplus.project.entity.BaseEntity;
import cn.com.dplus.report.entity.others.DetectValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@Data
@EqualsAndHashCode(callSuper=false)
@Entity(value="SPECIMEN",db="sample",queryNonPrimary=true,noClassnameStored=true)
public class Specimen extends BaseEntity {

	private static final long serialVersionUID = 7587757003211138713L;

	/** 样本的id  */
	@Id
	private String _id;
	
	/** 所属样品的id  */
	@Indexed
	private String sampleId;
	
	/** 采集的时间   */
	private Long collectTime;
	
	/**  设备的sn  */
	@Indexed
	private String dSn;

	/** 样本集的id  */
	private String sampleSetId;
	
	/** 用户的id  */
	private String userId;
	
	/** 默认是    1  -1 表示删除  */
	private Integer state;

	/** 检测时的状态id  */
	private List<String> devStateIds;

	/** 样本的检测值  */
	private List<DetectValue> dtValues;

	/** 数据来源  1快测，2MGK，3离线导入*/
	private Integer source;

}
