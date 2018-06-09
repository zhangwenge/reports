package cn.com.dplus.report.entity.mongodb;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import cn.com.dplus.project.annotation.ParamsValid;
import cn.com.dplus.project.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mongodb.morphia.annotations.NotSaved;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity(value="DETECTION_MODEL",db="model",queryNonPrimary=true,noClassnameStored=true)
public class DetectionModel extends BaseEntity{
	 
	private static final long serialVersionUID = -746440803178004799L;
	
	/** 模型的id  */
	@Id
	private String _id;
	
	/** 模型的编号  */
	private String dmNo;
	
	/** 指标id  */ 
	@Indexed
	private String indicatorId;
	
	/** 指标名称  */
	private String indicatorName;
	
	/** 行业id  */
	@Indexed
	private String industryId;
	
	/** 行业名称  */
	private String industryName;
	
	/** 样品集id  */
	private String sampleSetId;
	
	/** 样品集名称  */
	private String sampleSetName;
	
	/** 建模样品总数  */
	private Integer sampleCount;
	
	/** 用户id  */
	private String userId;
	
	/** 设备的SN  */
	private String dSn;
	
	/** 模型的系统名称  */
	private String systemModelName;
	
	/** 用户定义的模型名称  */
	private String userModelName;
	
	/** 模型的创建时间  */
	private Long createTime;
	
	/** 模型的更新时间  */
	private Long updateTime;
	
	/** 创建渠道  0模型工厂或 1 MGK   */
	private Integer createChanel;
	
	/** 品种id  */
	@Indexed
	private String breedId;
	
	/** 品种名称  */
	private String breedName;//品种名称
	
	/** 建模类型  0定量 1定性*/
	@ParamsValid(reg="^[0-1]$")
	private Integer cmtId;//建模类型id 
	
	/** 建模类型名称 */
	private String cmtName;//建模类型名称
	
	/** 模型创建者的userId  */
	private String crtUserId;//创建者id
	
	/** 指标单位  */
	private String indicatUnit;//指标单位
	
	/** 是否有模型报告  */
	private Integer hasReport;
	
	/** 建模消耗的时间 */
	private Double timeConsuming;
	
	/** 模型使用统计  */
	private Integer useCount;
	
	/** 是否审核  */
	@ParamsValid(reg="^[0-1]$")
	private Integer isAudit;
	
	/** 是否上架   */
	@ParamsValid(reg="^[0-1]$")
	private Integer isActive;
	
	/** 是否已经删除  */
	@ParamsValid(reg="^[0-1]$")
	private Integer isDelete;

	/** 用户加载模型时 , 指定是否加载系统模型  */
	@NotSaved
	private transient Integer getSysModel;
}
