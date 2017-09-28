package cn.com.dplus.report.entity;

import java.util.Map;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import cn.com.dplus.project.annotation.ParamsValid;
import cn.com.dplus.project.entity.BaseEntity;

/**
 * 
 *  @作用:	TODO 指标持久化
 *	@文件名:	Indicator.java
 * 	@所在包:	cn.com.dplus.sample.entity
 *	@开发者: 	余浪
 * 	@邮件: 	365617581@qq.com
 *  @时间:	2016年10月8日
 *	@公司:	广州讯动网络科技有限公司
 */
@Entity(value="INDICATOR",db="breed",queryNonPrimary=true,noClassnameStored=true)
public class Indicator extends BaseEntity implements Comparable<Indicator>{
	
	private static final long serialVersionUID = 7886305332658122055L;

	/** 指标ID	*/
	@Id
	private String _id;
	
	/** 指标名称	*/
	@ParamsValid(maxLen = 20)
	private String indicatorName;
	
	/** 品种ID	*/
	private String breedId;
	
	/** 品种名称 */
	@ParamsValid(notNull=true,maxLen = 20)
	private String breedName;
	
	/** 行业ID	*/
	@ParamsValid(notNull=true)
	private String industryId;
	
	/** 行业名称	*/
	@ParamsValid(notNull=true,maxLen = 20)
	private String industryName;
	
	/** 用户ID	*/
	private String userId;
	
	/** 指标类型 0定量和1定性 	*/
	@ParamsValid(notNull=true)
	private Integer indicatorType;
	
	/** 类型名称 */
	private String typeName;
	
	/** 单位	*/
	@ParamsValid(maxLen = 20)
	private String unit;
	
	/** 创建日期	*/
	private Integer createdAt;
	
	/** 更新日期	*/
	private Integer updatedAt;

	/** 删除标记 */
	private Integer flag;
	
	/** 值标签  定性和定级用  定级支持理化值范围 例 低：10-20 ; 中：20-30*/
	private Map<String,LabelEntity> valueLabels;
	
	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getIndicatorName() {
		return indicatorName;
	}

	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
	}

	public String getBreedId() {
		return breedId;
	}

	public void setBreedId(String breedId) {
		this.breedId = breedId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getIndicatorType() {
		return indicatorType;
	}

	public void setIndicatorType(Integer indicatorType) {
		this.indicatorType = indicatorType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Integer createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Integer updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getBreedName() {
		return breedName;
	}

	public void setBreedName(String breedName) {
		this.breedName = breedName;
	}

	public String getIndustryId() {
		return industryId;
	}

	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}


	public Map<String,LabelEntity> getValueLabels() {
		return valueLabels;
	}

	public void setValueLabels(Map<String,LabelEntity> valueLabels) {
		this.valueLabels = valueLabels;
	}

	@Override
	public int compareTo(Indicator o) {
		return this._id.compareTo(o.get_id());
	}


	
}
