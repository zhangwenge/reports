package cn.com.dplus.report.entity;



import java.util.Map;

import lombok.Data;

@Data
public class Head {
		
		//指标名称
		private String indicatorName;
		//指标ID
		private String indicatorId;
		//内控标准最小值
		private Float innerCtrValueMin;
		//内控标准最大值
		private Float innerCtrValueMax;
		//单位
		private String unit;
		//模型ID和模型名称
		private Map<String, String> models;
		//理化值保留字段
		private Double samAtterValue;
		//指标属性 0是定量  1是定性
		private Integer indicatorType;
		//设置反面结果
		private String labelFalse;
		//设置正面结果
		private String labelTrue;
		
}
