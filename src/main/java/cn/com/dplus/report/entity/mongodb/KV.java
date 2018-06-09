package cn.com.dplus.report.entity.mongodb;

import cn.com.dplus.project.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class KV extends BaseEntity{

	/**
	 *
	 */
	private static final long serialVersionUID = 6399754913416623606L;

	/** 键 */
    private String key;

    /** 值 */
    private Object value;

    public KV ( ) { }

    public KV ( String key, Object value){
		this.key = key;
		this.value = value;
	}

}
