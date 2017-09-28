package cn.com.dplus.report.utils;

import java.math.BigDecimal;

/**
 * @作用: 数字格式化类
 * @所在包: cn.com.dplus.d.utils
 * @开发者: 余浪
 * @邮箱: 365617581@qq.com
 * @时间: 2017/4/6
 * @公司: 广州讯动网络科技有限公司
 */
public class DigitalUtil {

    /**
     * 四舍五入保留小数
     * @param d
     * @param num 保留位数
     * @return
     */
    public static Double doubleDecimal(Double d,int num) {
        if (d!=null&&num > 0) {
            BigDecimal bg = new BigDecimal(d);
            Double f1 = bg.setScale(num, BigDecimal.ROUND_HALF_UP).doubleValue();
            return f1;
        } else {
            return d;
        }

    }
    
    public static Float floatDecimal(Float f,int num) {
    	if (f!=null&&num > 0) {
    		BigDecimal bg = new BigDecimal(f);
    		Float f1 = bg.setScale(num, BigDecimal.ROUND_HALF_UP).floatValue();
    		return f1;
    	}else {
    		return f;
    	}
    }
}
