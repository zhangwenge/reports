package cn.com.dplus.report.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @作用: 文件工具类
 * @所在包: cn.com.dplus.report.utils
 * @开发者: 余浪
 * @邮箱: 365617581@qq.com
 * @时间: 2017/4/11
 * @公司: 广州讯动网络科技有限公司
 */
public class FileUtil {

    /**
     * 保存文件
     * @param bytes
     * @param path
     * @param fileName
     * @return
     */
    public static String saveFile(byte[] bytes,String path,String fileName){
        try {
            createDir(path);
            FileOutputStream outputStream = new FileOutputStream(new File(path,fileName));
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
            return path+fileName;

        }catch (Exception e){
             e.printStackTrace();
        }
        return null;

    }

    /**
     * 读取文件
     * @param path
     * @return
     */
    public static byte[] readFile(String path){
        try {
            FileInputStream inputStream = new FileInputStream(new File(path));
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            inputStream.close();
            return bytes;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 删除文件
     * @param sPath
     * @return
     */
    public static boolean deleteFile(String sPath) {
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
          return true;
        }
        return false;
    }

    /**
     * 创建目录
     * @param destDirName
     * @return
     */
    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {// 判断目录是否存在
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {// 结尾是否以"/"结束
            destDirName = destDirName + File.separator;
        }
        if (dir.mkdirs()) {// 创建目标目录
            System.out.println("创建目录成功！" + destDirName);
            return true;
        } else {
            System.out.println("创建目录失败！");
            return false;
        }
    }
}
