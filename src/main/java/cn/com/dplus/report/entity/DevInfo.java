package cn.com.dplus.report.entity;

import cn.com.dplus.project.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author 张文歌
 * @Date 2017年7月28日16:16:31
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class DevInfo extends BaseEntity{
	private static final long serialVersionUID = -4118059149617763975L;

	/** 设备类型id*/
	private String typeId;
	
	/** 设备名称*/
	private String typeName;
	
	private String deviceUserLabel;
}
