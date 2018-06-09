package cn.com.dplus.report.entity.mongodb;

import java.util.List;

import cn.com.dplus.project.annotation.ParamsValid;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import cn.com.dplus.project.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.utils.IndexDirection;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(value="ORCHARD",db="citrus",queryNonPrimary=true,noClassnameStored=true)
public class Orchard extends BaseEntity{

    private static final long serialVersionUID = -613783175896774966L;

    /** 果园主键   */
    @Id
    private String _id;

    /**果园名称*/
    @ParamsValid(notNull = true)
    private String orcName;

    /**品种名称*/
    private String breedName;

    /**行政编码*/
    @ParamsValid(notNull = true)
    private String adCode;

    /**详细地址*/
    @ParamsValid(notNull = true)
    private String address;

    /**联系人*/
    private String linkMan;

    /**联系电话*/
    private String phoneNum;

    /**植株数*/
    private Integer plantAmount;

    /**植保站*/
    @ParamsValid(notNull = true)
    private String orgId;

    /**种植面积*/
    private Double auc;

    /** 记录的gps信息  */
    @Indexed(IndexDirection.GEO2D)
    private List<Double> location;

    /** 记录的创建时间 */
    private Long createTime;

    /** 数据的更新时间  */
    @Indexed(IndexDirection.DESC)
    private Long updateTime;

    /** 记录的状态  */
    private Integer state;

}
