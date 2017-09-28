package cn.com.dplus.report.entity;

import java.util.List;

import lombok.Data;

@Data
public class Content {
	//品种名称
	private String breedName;
	//表头的集合  存属性
	private List<Head> headList;
	//身体 对应的属性值
	private List<List<Object>> BodyList;
}
