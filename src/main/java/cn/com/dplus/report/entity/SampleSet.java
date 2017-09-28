package cn.com.dplus.report.entity;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import cn.com.dplus.project.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity(value="SAMPLE_SET",db="sample",queryNonPrimary=true,noClassnameStored=true)
public class SampleSet extends BaseEntity{

	private static final long serialVersionUID = -2592894420807939636L;
	
	/** 样品集的id  */
	@Id
	private String _id;
	
	/** 样品集名称  */
	private String sampleSetName;
	
	/** 样品集描述  */
	private String sampleSetDesc;
	
	/** 品种的id  */
	@Indexed
	private String breedId;
	
	/** 品种的名称  */
	private String breedName;
	
	/** 行业的id  */
	private String industryId;
	
	/** 行业的名称  */
	private String industryName;
	
	/** 用户的id  */
	@Indexed
	private String userId;
	
	/** 设备的SN  */
	private String dSn;

	/** 设备的名称  */
	private String dName;
	
	/** 创建时间  */
	private Long createTime;
	
	/** 更新时间  */
	@Indexed
	private Long updateTime;
	
	/** 是不是系统创建的样品集 */
	private Integer isSystemAutoCreated;
	
	/** 样品集中样品数统计  */
	private Integer sampleCount;
	
	/** 状态标识为  -1 表示删除了   默认1  状态正常 */
	private Integer state;
}
