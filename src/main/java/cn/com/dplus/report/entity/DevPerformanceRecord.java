package cn.com.dplus.report.entity;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import cn.com.dplus.project.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity(value="DEV_PERFORMANCE_RECORD",db="device",queryNonPrimary=true,noClassnameStored=true)
public class DevPerformanceRecord extends BaseEntity{
	private static final long serialVersionUID = 3717510629903266838L;
	@Id
	private String _id;
	
	/** 对应的设备的sn  */
	@Indexed
	private String dSn;
	
	/** 当前记录的类型  */
	private Integer type;
	
	/** 当前记录状态   -5 时代表的是出厂时的参数    0 未生效    1 记录已经生效  -1 删除 */
	private Integer state;
	
	/** 检测异常码   0  时代表成功  其他代表异常 */
	private Integer errCode;
	
	/** 开始的时间 */
	private Long startTime;
	
	/** 开启时的设备状态id  */
	private String startStateId;
	
	/** 结束时间  */
	private Long endTime;
	
	/** 结束时的状态id  */
	private String finishStateId;
	
	/** 备注  通常 在设备自检上生效*/
	@Embedded
	private Object remark;
	
}
