package cn.com.dplus.report.entity.mongodb;

import java.util.List;
import java.util.Set;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import cn.com.dplus.project.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity(value="DEV_RUNTIME_STATUS",db="device",queryNonPrimary=true,noClassnameStored=true)
@EqualsAndHashCode(callSuper = false)
public class DevRuntimeStatus extends BaseEntity{
	private static final long serialVersionUID = -4801066038427317263L;

	@Id
	private String _id;
	
	/** 对应的设备sn */
	@Indexed
	private String dSn;
	
	/** 各种状态  */
	private Integer status;
	
	/**  警告标识代号  */
	private Set<Integer> warningCode;
	
	/** GPS 状态  */
	private List<Double> GPS;
	
	/** 设备的MAC地址  */
	private String MAC;
	
	/** 设备温度  */
	private Float  devTemper;
	
	/**  设备的湿度  */
	private Float  devHumidity; 
	
	/** 检测器温度  */
	private Float  detectorTemper;
	
	/** 检测器的湿度  */
	private Float detectorHumidity;
	
	/** 光源的温度 */
	private Float  lampTemper;
	
	/** 更新时间  */
	private Long updateTime;
	
	/** 程序的版本  */
	private String devSoftVersion;
	
	/** 当前设备连接的服务器ip */
	private String connectIp;

	/** 设备的预热状态  0 预热中   1 预热完成  2 用户关闭卤灯 */
	private Integer preheatState;
}
