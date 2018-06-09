package cn.com.dplus.report.entity.mongodb;

import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.NotSaved;

import cn.com.dplus.project.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


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
@Data
@EqualsAndHashCode(callSuper = false)
@Entity(value="SAMPLE",db="sample",queryNonPrimary=true,noClassnameStored=true)
public class Sample extends BaseEntity{

	
	private static final long serialVersionUID = 929858101371629112L;

	/** 系统产生的样品编号  */
	@Id
	private String _id;

	/** 用户自定义的样品编号 */
	@Indexed
	private String sampleNo;
	
	/** 用户的id  */
	@Indexed
	private String userId;

	/** 行业id */
	private String industryId;

	/** 品种的id  */
	private String breedId;
	
	/** 品种名称  */
	private String breedName;
	
	/** 当前的设备sn  */
	@Indexed
	private String dSn;
	
	/** 当前样品的创建时间 */
	private Long createTime;
	
	/** 当前样品的采集时间   */
	private Long lastCollectTime;
	
	/** 样品的备注  */
	private String sampleRemark;
	
	/** 状态   默认    正常1 ，0 未产生光谱 ，-1已删除 */
	private Integer state;
	
	/** 该样品下的指标和属性信息  */
	@NotSaved
	private List<SampleAttrValue> sampleAttrValues;
	
	/** 当前样品关联的样本信息  */
	@NotSaved
	private List<Specimen> specimens;

}
