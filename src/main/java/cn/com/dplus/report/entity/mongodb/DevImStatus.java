package cn.com.dplus.report.entity.mongodb;

import java.util.Set;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import cn.com.dplus.project.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(value="DEV_IM_STATUS",db="device",queryNonPrimary=true,noClassnameStored=true)
public class DevImStatus extends BaseEntity{
	
	private static final long serialVersionUID = -2902262854988552917L;

	@Id
	private String _id;
	
	/** 设备的sn */
	@Indexed
	private String dSn;
	
	/** 设备的类型id  */
	@Indexed
	private String dTypeId;
	
	/** 设备的类型名称  */
	private String dTypeName;
	
	/** 当前的运行态id  */
	private String currentStateId;
	
	/** 连接服务器的ip 地址  */
	private String connectIp;
	
	/**  设备的ip  */
	private String ip;
	
	/** 设备的状态  */
	private Integer status;

	/** 警告代码  */
	private Set<Integer> warningCode;
	
	/** 当前更新的时间  */
	private Long currentTime;
	
	/** 持有人的id */
	@Indexed
	private String ownerId;
	
	/** 持有人的名称  */
	private String ownerName;
	
	/** 设备的备注名称  */
	private String deviceUserLabel;
	
	/** 用户自定义备注信息 */
	private String userRemark;
	
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
	
	/** 程序的版本号  */
	private String devSoftVersion;
	
	/** 设备的sn参考值  */
	private String dSnRef;
	
	/** 开机时间  */
	private Long starupTime;
	
	/** 平台的id  */
	private String platformId;
	
	/** GPS 的状态  */
	private String GPS;
	
	/** 生产日期  */
	private Long productionDate;
	
	/** 生产批次  */
	private String batch;
	
	/** 流转状态  */
	private Integer flowState;
	
	/** 设备添加的时间  */
	private Long devAddTime;
	
	/** 上一次的离线时间  */
	private Long lastOffLineTime;
	
	/** 设备的MAC地址  */
	private String MAC;
	
	/** 当前设备被何人锁定  */
	private String lockBy;

	/** 设备的预热状态  0 预热中   1 预热完成  2 用户关闭卤灯 */
	private Integer preheatState;

}
