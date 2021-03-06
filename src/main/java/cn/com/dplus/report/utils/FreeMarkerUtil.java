package cn.com.dplus.report.utils;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

/**
 * @作用:
 * @所在包: cn.com.dplus.d.utils
 * @开发者: 余浪
 * @邮箱: 365617581@qq.com
 * @时间: 2017/4/5
 * @公司: 广州讯动网络科技有限公司
 */
public class FreeMarkerUtil {


    // FreeMarker 配置
    private static Configuration cfg;

    /**
     * 静态内部类，创建freeMarker 配置，实例化工具类
     */
    private static class LazyHolder {

        // 创建 freeMarker 配置 ，2.3.24  引入的freemarker 版本号
        private static final Configuration config = new Configuration(new Version("2.3.24"));

        // 实例化工具类
        private static final FreeMarkerUtil fk = new FreeMarkerUtil();
    }

    /**
     * 私有构造函数
     */
    private FreeMarkerUtil() {

    }

    /**
     * 初始化配置文件，获取实例
     *
     * @param templatePath 模板路径
     * @return FreeMarkerUtil 工具类
     */
    public static FreeMarkerUtil getInstance(String templatePath) {

        if (null == cfg) {

            // 创建 freeMarker 配置
            cfg = LazyHolder.config;

            // 设置编码格式
            cfg.setDefaultEncoding("UTF-8");

            // templatePath 指的是模板所在路径
            cfg.setClassForTemplateLoading(FreeMarkerUtil.class, templatePath);
        }

        return LazyHolder.fk;
    }

    /**
     * 根据模版名称加载对应模版
     *
     * @param templateName 模版名称
     * @return
     */
    private Template getTemplate(String templateName) {

        try {

            return cfg.getTemplate(templateName);
        }
        catch (IOException e) {

            e.printStackTrace();
        }

        return null;
    }

    /**
     * 控制台打印通过模板生成的文件
     *
     * @param dataModel 数据模型
     * @param templateName 输出模版
     */
    public String getContent(Map<String, Object> dataModel, String templateName) {

        try {

            StringWriter stringWriter = new StringWriter();

            getTemplate(templateName).process(dataModel, stringWriter);

            stringWriter.flush();

            String result = stringWriter.toString();

            stringWriter.close();

            return result;
        }
        catch (TemplateException e) {

            e.printStackTrace();
        }
        catch (IOException e) {

            e.printStackTrace();
        }

        return null;
    }

    /**
     * 创建通过模板生成的文件
     *
     * @param dataModel 数据模型
     * @param templateName 输出模版
     * @param filePath 输出文件路径
     */
    public File create(Map<String, Object> dataModel, String templateName, String filePath) {

        try {

            File file = new File(filePath);

            if (!file.getParentFile().exists()) {

                file.getParentFile().mkdirs();
            }

            BufferedWriter bufWrite = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath),"UTF-8"));
            getTemplate(templateName).process(dataModel, bufWrite);
            bufWrite.flush();
            bufWrite.close();

            return file;
        }
        catch (TemplateException e) {

            e.printStackTrace();
        }
        catch (IOException e) {

            e.printStackTrace();
        }

        return null;
    }

    public  byte[] createByte(Object dataModel, String templateName) {
        try {

            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
            BufferedWriter bufWrite = new BufferedWriter(new OutputStreamWriter(ostream,"UTF-8"));
            getTemplate(templateName).process(dataModel, bufWrite);
            bufWrite.flush();
            bufWrite.close();
            ostream.close();
            byte[] temp =  ostream.toByteArray();
            return temp;
        }
        catch (TemplateException e) {

            e.printStackTrace();
        }
        catch (IOException e) {

            e.printStackTrace();
        }

        return null;
    }
    /**
     * 根据地址获得数据的字节流
     *
     * @param strUrl 网络连接地址
     * @return 图片Base64码
     */
	public String getImgBase64ByUrl(String strUrl) {

        try {

            // 建立 http 链接
            HttpURLConnection conn = (HttpURLConnection) new URL(strUrl).openConnection();

            // 5秒响应超时
            conn.setConnectTimeout(5 * 1000);
            conn.setDoInput(true);

            // 判断http请求是否正常响应请求数据，如果正常获取图片 Base64 码
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {

                // 获取图片输入流
                InputStream inStream = conn.getInputStream();

                // 用于存储图片输出流
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();

                // 定义缓存流，用于存储图片输出流
                byte[] buffer = new byte[1024];

                int len = 0;

                // 图片输出流循环写入
                while ((len = inStream.read(buffer)) != -1) {

                    outStream.write(buffer, 0, len);
                }
                // 图片输出流转字节流
                byte[] btImg = outStream.toByteArray();

                inStream.close();
                outStream.flush();
                outStream.close();

                return new String(Base64.encode(btImg));
            }
        }
        catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }



}
