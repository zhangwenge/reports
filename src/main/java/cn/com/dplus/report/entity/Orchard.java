package cn.com.dplus.report.entity;

import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.utils.IndexDirection;

import cn.com.dplus.project.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(value="Orchard",db="organization",queryNonPrimary=true,noClassnameStored=true)
public class Orchard extends BaseEntity{
	private static final long serialVersionUID = -3883298167455659403L;
	
	/** 果园的id  */
    @Id
    private String _id;

    /** 果园的名称  */
    private String orcName;

    /** 种植面积  */
    private Integer auc;

    /** 种植品种 */
    private String breedName;

    /** 行政编号  */
    private String adCode;

    /** 详细地址  */
    private String address;

    /** 联系人  */
    private String linkMan;

    /** 联系电话  */
    private String phoneNum;

    /** gps信息  */
    @Indexed(IndexDirection.GEO2D)
    private List<Double> location;

    /** 创建时间 */
    private Long createTime;

    /** 更新时间  */
    @Indexed(IndexDirection.DESC)
    private Long updateTime;

    /** 记录的状态 */
    private Integer state;
}
