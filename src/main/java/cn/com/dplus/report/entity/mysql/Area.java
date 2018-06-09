package cn.com.dplus.report.entity.mysql;

import ex.cn.com.dplus.mysql.base.BaseExample;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Set;

@Data
@Table(name="dp_area",catalog = "dp_organization")
@EqualsAndHashCode(callSuper = false)
public class Area extends BaseExample{
    /** 行政区域编码 */
    @Id
    @Column(name = "code")
    private String code;


    /** 行政区域编码上级ID */
    @Column(name = "pcode")
    private String pcode;


    /** 行政区域名称 */
    @Column(name = "name")
    private String name;


    /** 行政级别 */
    @Column(name = "level")
    private Integer level;

    /** 经度 */
    @Column(name = "gps_long")
    private Double gpsLong;

    /** 纬度 */
    @Column(name = "gps_lat")
    private Double gpsLat;


    /** 上级行政区域名称 */
    @Transient
    private String pname;

    @Transient
    private Set<Area> childArea;

    @Transient
    private Area parentArea;
}
