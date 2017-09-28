package cn.com.dplus.report.entity;

/**
 * @作用:
 * @所在包: com.dvkan.entity
 * @开发者: 余浪
 * @邮箱: 365617581@qq.com
 * @时间: 2017/4/1
 * @公司: 广州讯动网络科技有限公司
 */
public class DetectItem {

    /** 成分名称  */
    private String indicatorName;

    /** 检测的理化值  */
    private String value;

    /** 结果值的单位  */
    private String unit;

    private String standard;

	/** 不合格 */
    private Boolean unflag;

    /** 和standard 对比结果*/
    private String judge;
    
    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public Boolean getUnflag() {
        return unflag;
    }

    public void setUnflag(Boolean unflag) {
        this.unflag = unflag;
    }
    
    public String getJudge() {
		return judge;
	}

	public void setJudge(String judge) {
		this.judge = judge;
	}
}
