package cn.com.dplus.report.entity.mongodb;

import java.util.TreeSet;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity(value="DEV_WAVE_LENGTH",db="device",queryNonPrimary=true,noClassnameStored=true)
public class DevWaveLength {
	/** 等同设备的sn  */
	@Id
	private String _id;
	
	/** 设备的波长  */
	private TreeSet<Float> waveLength;
	
	/** 创建时间  */
	private Long createTime;
	
	/** 更新的时间  */
	private Long updateTime;
}
