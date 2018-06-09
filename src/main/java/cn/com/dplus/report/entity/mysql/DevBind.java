package cn.com.dplus.report.entity.mysql;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.dplus.project.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table(name="d_bind_record",catalog="dp_device")
@EqualsAndHashCode(callSuper=false)
public class DevBind extends BaseEntity{
	 
	private static final long serialVersionUID = 1350420275848265669L;
	
	/** 设备的id  */
	@Id
	@Column(name="d_id",nullable=true)
	private String dId;
	
	/** 设备的sn  */
	@Column(name="d_sn")
	private String dSn;
	
	/** 用户的id  */
	@Column(name="owner_id")
	private String ownerId;
	
	/** 用户的名称  */
	@Column(name="owner_name")
	private String ownerName;
	
	/** 绑定的时间  */
	@Column(name="bind_time")
	private Timestamp bindTime;

	/** 记录更新的时间  */
	@Column(name="update_time")
	private Timestamp updateTime;
	
	/** 用户自定义的设备名称  */
	@Column(name="device_user_label")
	private String deviceUserLabel;
	
	/** 用户自定义备注信息 */
	@Column(name="user_remark")
	private String userRemark;
	
	/** 参比总数*/
	@Transient
	private int referenceSum;
	
	/** 自检总数*/
	@Transient
	private int selfTestSum;
	
	/** 参比失败次数*/
	@Transient
	private int referenceFail;
	
	/** 自检失败次数*/
	@Transient
	private int selfTestFail;
	
	/** MGK扫描次数*/
	@Transient
	private int mgkScanningSum;

	/** MGK理化值录入*/
	@Transient
	private int mgkEnter;
	
	/** 快测扫描录入*/
	@Transient
	private int fastTestScanningSum;
	
	/** 样品异常*/
	@Transient
	private int abnormalSamples;
	

}
