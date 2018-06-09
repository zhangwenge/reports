package cn.com.dplus.report.entity.others;

import java.util.List;

import lombok.Data;

@Data
public class Content {
	//品种名称   sheet的名称
	private String breedName;
	//表头的集合  存属性
	private List<Head> headList;
	//身体 对应的属性值
	private List<List<Object>> bodyList;
	//无属性表头
	private List<Object> headListNO;
}
