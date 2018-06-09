package cn.com.dplus.report.entity.others;

import cn.com.dplus.mongodb.annatation.MFuture;
import cn.com.dplus.mongodb.annatation.MGroup;
import cn.com.dplus.project.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DevPRGroup extends BaseEntity{
	 
	private static final long serialVersionUID = 1L;

	@MGroup(mId = true)
    private String dSn;

    @MGroup(mId = true)
    private Integer type;

    @MGroup(mId = true)
    private Integer errCode;

    @MGroup(func = MFuture.count)
    private Integer count;
}
