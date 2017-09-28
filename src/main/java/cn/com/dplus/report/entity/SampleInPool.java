package cn.com.dplus.report.entity;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import cn.com.dplus.project.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity(value="SAMPLE_INPOOL",db="sample",queryNonPrimary=true,noClassnameStored=true)
public class SampleInPool extends BaseEntity{
	private static final long serialVersionUID = -9151616686973721131L;
	
	/** 记录的主键  */
	@Id
	private String _id;
	
	/** 样品的id  */
	@Indexed
	private String sampleId;
	
	/** 样品集的id */
	@Indexed
	private String sampleSetId;

	/** 状态  */
	private Integer state;

}
