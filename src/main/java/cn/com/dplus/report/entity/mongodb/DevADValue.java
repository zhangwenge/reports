package cn.com.dplus.report.entity.mongodb;


import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import cn.com.dplus.project.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity(value="DEV_AD_VALUE",db="device",queryNonPrimary=true,noClassnameStored=true)
public class DevADValue extends BaseEntity{
	private static final long serialVersionUID = -3583816704980656614L;

	@Id
	private String _id;
	
	/** 对应的记录id  */
	@Indexed
	private String recordId;
	
	/** 对应的ad值  */
	private List<Integer> adValue;
	
	/** 采集的时间  */
	@Indexed
	private Long time;
	
	/** 当前记录的状态    */
	private Integer state;
}
