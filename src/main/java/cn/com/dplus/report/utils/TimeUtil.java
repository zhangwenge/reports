package cn.com.dplus.report.utils;

import cn.com.dplus.project.utils.DateTimeUtils;

/**
 * @作用:
 * @所在包: cn.com.dplus.report.utils
 * @开发者: 余浪
 * @邮箱: 365617581@qq.com
 * @时间: 2017/4/20
 * @公司: 广州讯动网络科技有限公司
 */
public class TimeUtil {
    /**
     * 获取当前时间
     * @return
     */
    public static String getNowTime(){
       return DateTimeUtils.long2FormatString(System.currentTimeMillis(), "yyyyMMddhhmmssSSS");
    }
}
