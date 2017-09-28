package cn.com.dplus.report.entity;

import cn.com.dplus.project.entity.BaseEntity;

public class LabelEntity extends BaseEntity{

	/**  */
	private static final long serialVersionUID = -8104514110884096040L;

	
	public LabelEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public LabelEntity(String key,String value){
		this.key = key;
		this.value = value;
	}
	
	/** 键 */
	private String key;
	
	/** 值 */
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	

}
