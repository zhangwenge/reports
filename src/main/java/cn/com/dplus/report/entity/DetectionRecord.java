package cn.com.dplus.report.entity;

import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.NotSaved;

import cn.com.dplus.project.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 *  作    用:	TODO	检测记录的实体类封装类，数据将存放在mongodb中
 *	文件名:	DetectionRecord.java
 * 	所在包:	net.sondon.dplus.storage.entity
 *	开发者:	黄先国
 * 	邮    件:   hsamgle@qq.com
 *  时    间:	2015年8月6日上午11:45:30
 *	公    司:	广州讯动网络科技有限公司
 */
@Data
@Entity(value="DETECT_RECORD",db="detect-record",queryNonPrimary=true,noClassnameStored=true)
@EqualsAndHashCode(callSuper = false)
public class DetectionRecord extends BaseEntity implements Comparable<DetectionRecord>{


	private static final long serialVersionUID = -3954254205279071660L;

	/** 检测记录的id*/
	@Id
	private String _id;

	/** 样品的id */
	@Indexed
	private String sampleId;
	
	/** 检测样品的自定义编号 */
	@Indexed
	private String sampleNo;
	
	/** 品种的名称 */
	private String breedName;
	
	/** 品种的id*/
	private String breedId;
	
	/** 所属行业的id  */
	private String industryId;
	
	/** 所属行业的名称  */
	private String industryName;
	
	/** 该记录关联的用户的id */
	@Indexed
	private String userId;
	
	/** 检测记录的对外封装    */
	@NotSaved
	private List<SpecimenRecord> results;
	
	/** 该记录的状态 ，默认就是1 ：有效，0：无效 */
	private Integer state;
	
	/** 设备的SN码  */
	private String dSn;
	
	/** 检测方案的id  */
	private String planId;
	
	/** 检测方案的名称  */
	private String planName;
	
	/** 第一次创建的时间  */
	private Long createTime;
	
	/** 最后一次的更新时间 */
	private Long lastUpdateTime;
	
	/** 指标的平均检测值  */
	@NotSaved
	private List<DetectResult> avgValue;
	

	@Override
	public int compareTo(DetectionRecord o) {
		return (this.getLastUpdateTime()).compareTo((o.getLastUpdateTime()));
	}

}
