package cn.com.dplus.report.entity.others;

import cn.com.dplus.project.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 *  @类功能:	TODO	检测结果
 *	@文件名:	DetectionRecord.java
 * 	@所在包:	cn.com.dplus.d.entity
 *	@开发者:	黄先国
 * 	@邮_件:     hsamgle@qq.com
 *  @时_间:		2016年8月4日下午4:23:20
 *	@公_司:		广州讯动网络科技有限公司
 */
@Data
@EqualsAndHashCode(callSuper = false)
public  class DetectValue extends BaseEntity{
	
	private static final long serialVersionUID = -5926784911178460148L;

	/** 模型id  */
    private String modelId;

    /** 检测的理化值  */
    private Double value;

}
