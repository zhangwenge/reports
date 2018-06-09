package cn.com.dplus.report.entity.mongodb;

import java.util.Map;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import cn.com.dplus.project.annotation.ParamsValid;
import cn.com.dplus.project.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@Data
@EqualsAndHashCode(callSuper=false)
@Entity(value="INDICATOR",db="breed",queryNonPrimary=true,noClassnameStored=true)
public class Indicator extends BaseEntity implements Comparable<Indicator>{
	
	private static final long serialVersionUID = -4086628254199216208L;

	/** 指标ID	*/
	@Id
	private String _id;
	
	/** 指标名称	*/
	@ParamsValid(maxLen = 20)
	private String indicatorName;

	/** 英文代号  */
	private String code;

	/** 品种ID	*/
	@Indexed
	private String breedId;
	
	/** 品种名称 */
	private String breedName;
	
	/** 行业ID	*/
	private String industryId;
	
	/** 行业名称	*/
	private String industryName;
	
	/** 用户ID	*/
	private String userId;
	
	/** 指标类型 0定量和1定性 	*/
	@ParamsValid(notNull=true,reg="^[0-1]$")
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
	private Integer state;
	
	/** 值标签  定性和定级用  定级支持理化值范围 例 低：10-20 ; 中：20-30*/
	private Map<String,KV> valueLabels;

	@Override
	public int compareTo(Indicator o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
