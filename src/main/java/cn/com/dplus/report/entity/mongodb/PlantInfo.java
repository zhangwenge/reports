package cn.com.dplus.report.entity.mongodb;

import java.util.Collection;
import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.NotSaved;
import org.mongodb.morphia.utils.IndexDirection;

import cn.com.dplus.project.annotation.ParamsValid;
import cn.com.dplus.project.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity(value="PLANT_INFO",db="citrus",queryNonPrimary=true,noClassnameStored=true)
public class PlantInfo extends BaseEntity{
    /** 植株id  */
    @Id
    private String _id;

    /**  植株编号  */
    @Indexed
    private String plantNo;

    /** 品种id  */
    @Indexed
    private String breedId;

    /** 品种名称  */
    private String breedName;

    /** 所属的果园  */
    private String orchardId;

    /** 当前所属的行政编号  */
    private String adCode;

    /** 创建时间 */
    private Long createTime;

    /** 更新时间  */
    @Indexed(IndexDirection.DESC)
    private Long updateTime;

    /** 诊断结果  */
    private Integer diagnoseValue;

    /** 植株诊断时间  */
    @Indexed(IndexDirection.DESC)
    private Long diagnoseTime;

    /** 区域植株感染率 */
    private Float infectRate;

    /** 标识是否已经标识 */
    @ParamsValid(reg = "^[0-1]$")
    private Integer hasMarked;

    /** 状态  -1 标记无效  默认 1 没检测结果   2  正常    3 待砍树  4 已砍树    */
    private Integer state;

    /** 所在的中心位置  */
    @Indexed(IndexDirection.GEO2D)
    private List<Double> location;

    /** 植株所在的位置的四至 */
    private Collection boundary;

    @NotSaved
    private String labelState;
}
