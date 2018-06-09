package cn.com.dplus.report.entity.mongodb;

import ex.cn.com.dplus.mysql.base.BaseExample;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.utils.IndexDirection;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(value="ORCHARD_USER",db="citrus",queryNonPrimary=true,noClassnameStored=true)
public class OrchardUser extends BaseExample{
    /** 主键   */
    @Id
    private String _id;

    /**果园id*/
    private String orchardId;
    /**用户id*/
    private String userId;

    /** 记录的创建时间 */
    private Long createTime;

    /** 数据的更新时间  */
    @Indexed(IndexDirection.DESC)
    private Long updateTime;
}
