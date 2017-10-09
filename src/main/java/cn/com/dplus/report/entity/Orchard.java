package cn.com.dplus.report.entity;

import java.sql.Timestamp;
import cn.com.dplus.project.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Orchard extends BaseEntity{
	private static final long serialVersionUID = -3883298167455659403L;
	
	/** 果园的id  */
    private String id;

    /** 果园的名称  */
    private String orcName;

    /** 种植面积  */
    private Integer auc;

    /** 种植品种 */
    private String breedName;

    /** 植株数  */
    private Integer plantAmount;

    /** 行政编号  */
    private String adCode;

    /** 详细地址  */
    private String address;

    /** 联系人  */
    private String linkMan;

    /** 联系电话  */
    private String phoneNum;

    /** 经度 */
    private Double gpsLong;

    /** 纬度 */
    private Double gpsLat;

    /** 创建时间 */
    private Timestamp createTime;

    /** 更新时间  */
    private Timestamp updateTime;

    /** 记录的状态 1为正常，-1为删除*/
    private Integer state;

}
