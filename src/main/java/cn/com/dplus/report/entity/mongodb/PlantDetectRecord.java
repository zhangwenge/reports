package cn.com.dplus.report.entity.mongodb;

import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.NotSaved;
import org.mongodb.morphia.utils.IndexDirection;

import cn.com.dplus.project.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity(value="PLANT_DETECT_RECORD",db="citrus",queryNonPrimary=true,noClassnameStored=true)
public class PlantDetectRecord extends BaseEntity{
	private static final long serialVersionUID = 959076809932150864L;

    /** 样本的id */
    @Id
    private String _id;

    /** 植株id  等同于  样品id */
    @Indexed
    private String plantId;

    /** 植株编号 等同样品编号 */
    @Indexed
    private String plantNo;

    /** 果园的Id  */
    private String orchardId;

    /** 品种id */
    private String breedId;

    /** 品种名称 */
    private String breedName;

    /** 所属行业的id  */
    private String industryId;

    /** 所属行业的名称  */
    private String industryName;

    /** 该记录关联的用户的id */
    @Indexed
    private String userId;

    /** 该记录的状态 ，默认就是1 ：有效，0：无效 */
    private Integer state;

    /** 设备的SN码  */
    @Indexed(IndexDirection.DESC)
    private String dSn;

    /** 记录创建的时间  */
    @Indexed(IndexDirection.DESC)
    private Long createTime;

    /** 记录更新的时间 */
    @Indexed(IndexDirection.DESC)
    private Long updateTime;

    /** 所用的模型id  */
    private String modelId;

    /** 指标id */
    private String indicatorId;

    /** 检测值  */
    private Float value;

    /** 行政编号  */
    private String adCode;

    /** 当前检测的地里坐标  */
    @Indexed(IndexDirection.GEO2D)
    private List<Double> location;

    /** 检测地址 */
    private String address;

    /** 检测是设备的状态id  */
    private String devStateRecordId;
    
    /** 设置最后的检测结果   正反面结果*/
    @NotSaved
    private String labelState;
}
