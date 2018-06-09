package cn.com.dplus.report.entity.others;

import java.util.List;

import cn.com.dplus.mongodb.annatation.MFuture;
import cn.com.dplus.mongodb.annatation.MGroup;
import cn.com.dplus.project.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SampleGroup extends BaseEntity{

	private static final long serialVersionUID = 9214526032139715446L;

	@MGroup(mId=true)
	private String dSn;
	
	@MGroup(mId=true)
	private String sampleId;

	@MGroup(func=MFuture.add2set,source="dtValues")
	private List<List<DetectValue>> dtValues;
	
}
