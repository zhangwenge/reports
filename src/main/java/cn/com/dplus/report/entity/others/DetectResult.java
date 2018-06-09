package cn.com.dplus.report.entity.others;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.NotSaved;

import cn.com.dplus.project.entity.BaseEntity;

/**
 * 
 *  @类功能:	TODO	检测结果
 *	@文件名:	DetectionRecord.java
 * 	@所在包:	cn.com.dplus.d.entity
 *	@开发者:	黄先国
 * 	@邮_件:     hsamgle@qq.com
 *  @时_间:		2016年8月4日下午4:23:20
 *	@公_司:		广州讯动网络科技有限公司
 */
@Embedded
public  class DetectResult extends BaseEntity implements Comparable<DetectResult>{
	
	private static final long serialVersionUID = 2507830333902965694L;

	// 临时的变量
	@NotSaved
	private transient String sampleId;
	
	/** 当前用到的模型id*/
	private String modelId;
	
	/** 模型的名称  */
	private String modelName;
	
	/** 当前用到的成分id */
	private String indicatorId;
	
	/** 成分名称  */
	private String indicatorName;
	
	/** 检测的理化值  */
	private Double value;
	
	/** 结果值的单位  */
	private String unit;
	
	/** 异常标识 */
	private Integer flag;
	
	/** 建模类型  */
	private Integer	 cmt;
	
	/** 统计用的临时变量 ，不做保存*/
	@NotSaved
	private transient Integer count;
	
	public String getSampleId() {
		return sampleId;
	}

	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getIndicatorId() {
		return indicatorId;
	}

	public void setIndicatorId(String indicatorId) {
		this.indicatorId = indicatorId;
	}

	public String getIndicatorName() {
		return indicatorName;
	}

	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
	}
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getCmt() {
		return cmt;
	}

	public void setCmt(Integer cmt) {
		this.cmt = cmt;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * 
	 * @方法功能：	TODO	计算平均的检测值
	 * @方法名称：	computeAVG
	 * @编写时间：	2017年3月22日上午10:15:06
	 * @开发者  ：	  黄先国
	 * @方法参数：	
	 * @返回值  :	void
	 */
	public void computeAVG(){
		this.value = getValue() / getCount();
	}
	
	
	@Override
	public int compareTo(DetectResult o) {
		return this.indicatorId.compareTo(o.getIndicatorId());
	}

}
