package cn.com.dplus.report.entity.mongodb;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import cn.com.dplus.project.entity.BaseEntity;


/**
 * 
 *  @类功能:	TODO	
 *	@文件名:	InnerQualitystandard.java
 * 	@所在包:	cn.com.dplus.breed.entity
 *	@开发者:	黄先国
 * 	@邮_件:     hsamgle@qq.com
 *  @时_间:		2016年12月3日下午3:19:43
 *	@公_司:		广州讯动网络科技有限公司
 */
@Entity(value="InnerQualitystandard",db="breed",queryNonPrimary=true,noClassnameStored=true)
public class InnerQualitystandard extends BaseEntity {

	private static final long serialVersionUID = -2111060771580979206L;

	/** 记录的id */
	@Id
	private String _id;
	
	/** 用户的id  */
	@Indexed
	private String userId;
	
	/** 指标的id  */
	private String indicatorId;
	
	/** 品种的id  */
	@Indexed
	private String breedId;
	
	/** 标准的下限  */
	private Float min;
	
	/** 标准的上限  */
	private Float max;
	
	/** 0 代表关闭  1 代表开启  */
	private int state = 1;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIndicatorId() {
		return indicatorId;
	}

	public void setIndicatorId(String indicatorId) {
		this.indicatorId = indicatorId;
	}

	public String getBreedId() {
		return breedId;
	}

	public void setBreedId(String breedId) {
		this.breedId = breedId;
	}

	public Float getMin() {
		return min;
	}

	public void setMin(Float min) {
		this.min = min;
	}

	public Float getMax() {
		return max;
	}

	public void setMax(Float max) {
		this.max = max;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
}

