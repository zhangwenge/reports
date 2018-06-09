package cn.com.dplus.report.entity.mongodb;

import cn.com.dplus.project.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import java.util.List;

/**
 * @类功能: TODO
 * @文件名: Spectrum
 * @所在包: cn.com.dplus.sp.entity
 * @开发者: 黄先国
 * @邮_件: hsamgle@qq.com
 * @时_间: 2017/9/5 13:54
 * @公_司: 广州讯动网络科技有限公司
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity(value = "SPECTRUM",db="sample",queryNonPrimary=true,noClassnameStored = true)
public class Spectrum extends BaseEntity{

    private static final long serialVersionUID = 4994137525481713816L;
    /** 样本id  */
    @Id
    private String _id;

    /** 关联的样品id  */
    @Indexed
    private String sampleId;

    /** 光谱数据  */
    private List<Float> spectrum;

    public Spectrum( ) {}

    public Spectrum(String _id, String sampleId, List<Float> spectrum ) {
        this._id = _id;
        this.sampleId = sampleId;
        this.spectrum = spectrum;
    }
}
