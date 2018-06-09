package cn.com.dplus.report.entity.others;

import cn.com.dplus.mongodb.annatation.MFuture;
import cn.com.dplus.mongodb.annatation.MGroup;
import cn.com.dplus.project.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SGroup extends BaseEntity{
	
	private static final long serialVersionUID = -6264319769136171729L;
	
	/** 设备的sn*/
	@MGroup(mId = true)
	private String dSn;
	
	/** 数据源   1快测  2MGK 3离线导入*/
	@MGroup(mId = true)
	private Integer source;
	
	/** 数量统计*/
	@MGroup(func = MFuture.count)
	private Integer count;
}
