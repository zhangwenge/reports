package cn.com.dplus.report.entity.others;

import lombok.Data;

@Data
public class UserReport {
	//设备信息
	private String devMessage;
	//统计时间段
	private String statisticsTime;
	//参比次数
	private Integer numberOfReferences;
	//参比失败的次数
	private Integer numberOfReferencesFailure;
	//参比失败时间
	private String referencesFailureTime;
	//检测总数
	private Integer numberOfRecord;
	//样品集扫描光谱总数
	private String spectrum;
}
