
package cn.com.dplus.report.http;

import cn.com.dplus.project.utils.EnvUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @作用:
 * @所在包: cn.com.dplus.d.http
 * @开发者: 余浪
 * @邮箱: 365617581@qq.com
 * @时间: 2017/4/5
 * @公司: 广州讯动网络科技有限公司
 */

@Component
public class API {

    private static final String HTTP = "http://";

    /************************** report 地址*******************************/
    public static String REPORT_PATH;

    @Value("${report.path}")
    public void setReportPath(String reportPath) {
        REPORT_PATH = reportPath;
    }

    /**
     * 下载地址
     */
    public static String EXPORT_PATH;

    @Value("${report.export}")
    public void setExportPath(String exportPath) {
        EXPORT_PATH = exportPath;
    }

    /************************** 品种服务**********************************/

    @Value("${spring.http.api.breed_serivce_host}")
    private String BREED_SERIVCE_HOST;

    private String breedUrl() {
        return HTTP + EnvUtils.getVal(BREED_SERIVCE_HOST, BREED_SERIVCE_HOST);
    }

    /**
     * 内控标准URL
     */
    public static String GET_STANDARDS;

    @Value("${spring.http.api.get_standards}")
    public void setGetStandards(String getStandards) {
        GET_STANDARDS = breedUrl() + getStandards;
    }

    /**
     * 品种服记录
     */
    public static String GET_INDICATORS_BID;

    @Value("${spring.http.api.get_indicators_bid}")
    public void setGetIndicatorsBid(String getIndicatorsBid) {
        GET_INDICATORS_BID = breedUrl() + getIndicatorsBid;
    }


    /************************** 检测记录**********************************/

    @Value("${spring.http.api.detect_record_serivce_host}")
    private String DETECT_RECORD_SERIVCE_HOST;

    private String detectRecordUrl() {
        return HTTP + EnvUtils.getVal(DETECT_RECORD_SERIVCE_HOST, DETECT_RECORD_SERIVCE_HOST);
    }

    /**
     * 检测记录
     */
    public static String GET_RECORD;

    @Value("${spring.http.api.get_record}")
    public void setGetRecord(String getRecord) {
        GET_RECORD = detectRecordUrl() + getRecord;
    }


    /************************** 样品服务**********************************/

    @Value("${spring.http.api.sample_serivce_host}")
    private String SAMPLE_SERIVCE_HOST;

    private String sampleUrl() {
        return HTTP + EnvUtils.getVal(SAMPLE_SERIVCE_HOST, SAMPLE_SERIVCE_HOST);
    }

    /**
     * 样品信息
     */
    public static String GET_SAMPLES;

    @Value("${spring.http.api.get_samples}")
    public void setGetSamples(String getSamples) {
        GET_SAMPLES = sampleUrl() + getSamples;
    }

    /************************** 用户服务**********************************/

    @Value("${spring.http.api.user_serivce_host}")
    private String USER_SERIVCE_HOST;

    private String userUrl() {
        return HTTP + EnvUtils.getVal(USER_SERIVCE_HOST, USER_SERIVCE_HOST);
    }

    /**
     * 用户个人信息
     */
    public static String GET_USERINFO;

    @Value("${spring.http.api.get_userinfo}")
    public void setGetUserinfo(String getUserinfo) {
        GET_USERINFO = userUrl() + getUserinfo;
    }

    /************************** token服务**********************************/

    @Value("${spring.http.api.token_serivce_host}")
    private String TOKEN_SERIVCE_HOST;

    private String tokenUrl() {
        return HTTP + EnvUtils.getVal(TOKEN_SERIVCE_HOST, TOKEN_SERIVCE_HOST);
    }

    /**
     * 用户个人信息
     */
    public static String GET_TOKEN;

    @Value("${spring.http.api.get_token}")
    public void setGetToken(String getToken) {
        GET_TOKEN = tokenUrl() + getToken;
    }

    /************************** user-app服务**********************************/
    @Value("${spring.http.api.user_app_serivce_host}")
    private String USER_APP_SERVICE_HOST;

    private String userAppUrl() {
        return HTTP + EnvUtils.getVal(USER_APP_SERVICE_HOST, USER_APP_SERVICE_HOST);
    }

    /**
     * 获取用户扩展信息url
     */
    public static String GET_USER_APP_INFO;

    @Value("${spring.http.api.get_user_app_info}")
    private void setGetUserAppInfo(String getUserAppInfo) {
        GET_USER_APP_INFO = userAppUrl() + getUserAppInfo;
    }

    /************************ device *****************************/
    @Value("${spring.http.api.device_service_host}")
    private String DEVICE_SERVICE_HOST;
    
    private String deviceUrl(){
    	return HTTP + EnvUtils.getVal(DEVICE_SERVICE_HOST, DEVICE_SERVICE_HOST);
    }
    /**
     * 获取设备的信息
     */
    public static String GET_DEV_INFO;
    
    @Value("${spring.http.api.get_dev_info}")
    private void setGetDevInfo(String getDevInfo) {
    	GET_DEV_INFO = deviceUrl() + getDevInfo;
    }
}
