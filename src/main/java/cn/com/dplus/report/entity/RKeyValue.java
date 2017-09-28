package cn.com.dplus.report.entity;

/**
 * @作用:
 * @所在包: cn.com.dplus.d.entity
 * @开发者: 余浪
 * @邮箱: 365617581@qq.com
 * @时间: 2017/4/5
 * @公司: 广州讯动网络科技有限公司
 */
public class RKeyValue {

    private String key;
    private String value;

    public RKeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
