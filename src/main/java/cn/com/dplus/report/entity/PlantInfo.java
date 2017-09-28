package cn.com.dplus.report.entity;

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
@Entity(value="PLANT_INFO",db="citrus-record",queryNonPrimary=true,noClassnameStored=true)
public class PlantInfo extends BaseEntity{
	private static final long serialVersionUID = 4687904216970470486L;
	
	/** 植株id  */
    @Id
    private String _id;

    /**  植株编号  */
    @Indexed
    private String plantNo;

    /** 品种id  */
    private String breedId;

    /** 品种名称  */
    private String breedName;

    /** 所属的果园  */
    @Indexed
    private String orchardId;

    /** 植株状态  */
    @ParamsValid(reg = "^[1-6]$")
    private Integer plantState;

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

    /** 植株所在的位置  */
    @Indexed(IndexDirection.GEO2D)
    private List<Double> location;

    /** 当前记录的状态  1 标识正常   -1 标识删除  0 未生效 */
    private Integer state;

    /** 经度 临时变量 */
    @NotSaved
    private transient Double longitude;

    /** 纬度 临时变量 */
    @NotSaved
    private transient Double latitude;

    /** 操作用户的id 临时变量 */
    @NotSaved
    private transient String userId;

    /** 检测机构的id */
    private transient String structureId;
    
    /** 设置最后的检测结果   正反面结果*/
    @NotSaved
    private String labelState;

}
