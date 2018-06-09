package cn.com.dplus.report.entity.mongodb;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Property;

/**
 * @Description: 样品集指标
 * @Author: 詹军政|zhanjz@sondon.net
 * @Date: Created in 下午3:50 17-10-23
 * @Modified By:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity(value="SAMPLE_SET_INDICATOR",db="sample",queryNonPrimary=true,noClassnameStored=true)
public class SampleSetIndicator {

    @Id
    @Property("_id")
    private String id;

    /** 指标ID */
    @Indexed
    private String indicatorId;

    /** 样品集ID */
    @Indexed
    private String sampleSetId;

    /** 创建时间 */
    private Long createTime;
}
