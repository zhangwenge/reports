package cn.com.dplus.report.utils;

import com.csvreader.CsvWriter;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @Package :cn.com.dplus.report.utils
 * @Administrator : 张伟杰
 * @E-mail : zhangwj@sondon.net
 * @Description :TODO
 * @Date : 2017/12/19 13:38
 */
public class CsvUtil {
    public static File createCSVFile(List<Object> head, List<List<Object>> dataList,
                                     String outPutPath, String filename) {
        File csvFile = null;
        BufferedWriter csvWtriter = null;
        try {
            csvFile = new File(outPutPath + File.separator + filename + ".csv");
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();

            // GB2312使正确读取分隔符","
            csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"), 1024);
            // 写入文件头部
            writeRow(head, csvWtriter);
            // 写入文件内容
            for (List<Object> row : dataList) {
                writeRow(row, csvWtriter);
            }
            csvWtriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            try {
                csvWtriter.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return csvFile;
    }

    public static File createXlsxFile(List<Object> head, List<List<Object>> dataList,
                                     String outPutPath, String filename) {
        File csvFile = null;
        BufferedWriter csvWtriter = null;
        try {
            csvFile = new File(outPutPath + File.separator + filename + ".xls");
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();

            // GB2312使正确读取分隔符","
            csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"), 1024);
            // 写入文件头部
            writeRowXlsx(head, csvWtriter);
            // 写入文件内容
            for (List<Object> row : dataList) {
                writeRowXlsx(row, csvWtriter);
            }
            
            csvWtriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            try {
                csvWtriter.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return csvFile;
    }
    /**
     * 写一行数据方法
     * @param row
     * @param csvWriter
     */
    private static void writeRow(List<Object> row, BufferedWriter csvWriter) {
        try {
            // 写入文件头部
            for (Object data : row) {
                StringBuffer sb = new StringBuffer();
                String rowStr = sb.append("\"").append(data).append("\",").toString();
                csvWriter.write(rowStr);
            }
            csvWriter.newLine();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 写一行数据方法
     * @param row
     * @param csvWriter
     */
    private static void writeRowXlsx(List<Object> row, BufferedWriter csvWriter) {
        try {
            // 写入文件头部
            for (Object data : row) {
                StringBuffer sb = new StringBuffer();
                String rowStr = sb.append(data).append("\t").toString();
                csvWriter.write(rowStr);
            }
            csvWriter.newLine();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static byte[] exportValueTemplate(String[] firstRow,String[] twoRow) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //创建写入对象
        CsvWriter csvWriter = new CsvWriter(out, ',', Charset.forName("GBK"));
        csvWriter.writeRecord(firstRow);
        csvWriter.writeRecord(twoRow);

        csvWriter.close();
        byte[] bytes = out.toByteArray();
        return bytes;
    }
}
