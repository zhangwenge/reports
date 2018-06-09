package cn.com.dplus.report.utils;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.util.IOUtils;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import cn.com.dplus.project.annotation.ConsumeTime;
import cn.com.dplus.project.utils.DateTimeUtils;
import cn.com.dplus.report.entity.mongodb.Orchard;
import cn.com.dplus.report.entity.mongodb.PlantDetectRecord;
import cn.com.dplus.report.entity.mongodb.PlantInfo;

/**
 * 
 * @文件名： ItextPDFExportUtil.java
 * @所在包： cn.com.dplus.report.utils
 * @开发者： 张文歌
 * @邮_件：710890559@qq.com
 * @时_间：2017年10月12日上午11:13:33
 * @公_司：广州讯动网络科技有限公司
 */
public class ItextPDFExportUtil {

    private static PdfFont getPdfFont() {
    	InputStream inputStream = null;
        //设置字体为思源字体
    	PdfFont font = null;
        try {
        	inputStream = ItextPDFExportUtil.class.getResourceAsStream("/simfang.ttf");
            font = PdfFontFactory.createFont(IOUtils.toByteArray(inputStream), PdfEncodings.IDENTITY_H, false);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        	try {
        		if(inputStream!=null) {
        			inputStream.close(); 
        		}
			} catch (Exception e2) {
			}
		}
        return font;
    }

    @ConsumeTime
    public static byte[] exportPDF(PlantInfo plantInfo, Orchard orchard, List<PlantDetectRecord> plantDetectRecordList) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            //创建一个编写PDF文件的对象
            PdfWriter writer = new PdfWriter(out);
            //PdfDocument是管理添加的内容
            PdfDocument pdf = new PdfDocument(writer);
            //创建一个文档对象,并设置大小是A4
            Document document = new Document(pdf, PageSize.A4);

            //灰色背景
            DeviceRgb Gray = new DeviceRgb(242, 242, 242);
            
            /**设置第一个的表格*/
            Table table1 = new Table(new float[]{47, 88, 47, 88}).setWidthPercent(80);
            //设置高度18毫米，设置边框固定，距离顶部20毫米  字体兼容中文  表格居中
            table1.setMinHeight(18).setFixedLayout().setMarginTop(20).setFont(getPdfFont()).setHorizontalAlignment(HorizontalAlignment.CENTER);
            //植株编号
            Cell cell = new Cell().add(new Paragraph("植株编号").setBold()).setBackgroundColor(Gray);
            table1.addCell(cell);
            cell = new Cell().add(new Paragraph(plantInfo.getPlantNo()));
            table1.addCell(cell);
            //品种
            cell = new Cell().add(new Paragraph("品种").setBold()).setBackgroundColor(Gray);
            table1.addCell(cell);
            cell = new Cell().add(new Paragraph(plantInfo.getBreedName() != null ? plantInfo.getBreedName() : "--"));
            table1.addCell(cell);
            //检测结果
            cell = new Cell().add(new Paragraph("检测结果").setBold()).setBackgroundColor(Gray);
            table1.addCell(cell);
            cell = new Cell().add(new Paragraph(plantInfo.getLabelState())).setFont(getPdfFont());
            table1.addCell(cell);
            //植株状态
            cell = new Cell().add(new Paragraph("植株状态").setBold()).setBackgroundColor(Gray);
            table1.addCell(cell);
            switch (plantInfo.getState()) {
                case 1:
                    cell = new Cell().add(new Paragraph("--"));
                    table1.addCell(cell);
                    break;
                case 2:
                    cell = new Cell().add(new Paragraph("正常"));
                    table1.addCell(cell);
                    break;
                case 3:
                    cell = new Cell().add(new Paragraph("待处理"));
                    table1.addCell(cell);
                    break;
                case 4:
                    cell = new Cell().add(new Paragraph("已处理"));
                    table1.addCell(cell);
                    break;
                default:
                	cell = new Cell().add(new Paragraph(""));
                    break;
            }
            //果园名称
            cell = new Cell().add(new Paragraph("果园").setBold()).setBackgroundColor(Gray);
            table1.addCell(cell);
            cell = new Cell().add(new Paragraph( (orchard != null&& orchard.getOrcName() != null) ? orchard.getOrcName() : "--"));
            table1.addCell(cell);
            //负责人
            cell = new Cell().add(new Paragraph("负责人").setBold()).setBackgroundColor(Gray);
            table1.addCell(cell);
            cell = new Cell().add(new Paragraph( (orchard != null&&orchard.getLinkMan() != null) ? orchard.getLinkMan() : "--"));
            table1.addCell(cell);
            //联系电话
            cell = new Cell().add(new Paragraph("联系电话").setBold()).setBackgroundColor(Gray);
            table1.addCell(cell);
            cell = new Cell().add(new Paragraph( (orchard != null&&orchard.getPhoneNum()!=null) ?orchard.getPhoneNum() : "--"));
            table1.addCell(cell);
            //果园地址
            cell = new Cell().add(new Paragraph("果园地址").setBold()).setBackgroundColor(Gray);
            table1.addCell(cell);
            cell = new Cell().add(new Paragraph( (orchard != null&&orchard.getAddress() != null) ? orchard.getAddress() : "--"));
            table1.addCell(cell);

            /**设置第二个的表格*/
            Table table2 = new Table(new float[]{105, 78, 92}).setWidthPercent(80);
            //最小高度18mm 边框固定  设置兼容中文  文本字体居中 表格居中
            table2.setMinHeight(18).setFixedLayout().setFont(getPdfFont()).setTextAlignment(TextAlignment.CENTER).setHorizontalAlignment(HorizontalAlignment.CENTER);
            //上面无边框，合并三列 ，字体居中
            cell = new Cell(1, 3).setBorderTop(Border.NO_BORDER).add(new Paragraph("各样本检测结果"));
            table2.addCell(cell);

            cell = new Cell().setBackgroundColor(Gray).add(new Paragraph("检测时间").setBold());
            table2.addCell(cell);
            cell = new Cell().setBackgroundColor(Gray).add(new Paragraph("样本编号").setBold());
            table2.addCell(cell);
            cell = new Cell().setBackgroundColor(Gray).add(new Paragraph("检测结果").setBold());
            table2.addCell(cell);

            int a = plantDetectRecordList.size();
            for (PlantDetectRecord plantDetectRecord : plantDetectRecordList) {
                cell = new Cell().add(new Paragraph(DateTimeUtils.long2FormatString(plantDetectRecord.getCreateTime(), "yyyy/MM/dd HH:mm:ss")));
                table2.addCell(cell);
                cell = new Cell().add(new Paragraph("#" + a));
                table2.addCell(cell);
                cell = new Cell().add(new Paragraph(plantDetectRecord.getLabelState()));
                table2.addCell(cell);
                a--;
            }

            /**设置第三个表格 */
            Table table3 = new Table(new float[]{47, 88, 47, 88}).setWidthPercent(80);
            //字体兼容中文 --边框固定--
            table3.setFont(getPdfFont()).setMinHeight(18).setFixedLayout().setHorizontalAlignment(HorizontalAlignment.CENTER);

            //取消上边框使得的
            cell = new Cell().setBackgroundColor(Gray).setHeight(26.40f).setBorderTop(Border.NO_BORDER).add(new Paragraph("备注").setBold().setVerticalAlignment(VerticalAlignment.MIDDLE));
            table3.addCell(cell);
            cell = new Cell(1, 3).setHeight(26.40f).setBorderTop(Border.NO_BORDER).add(new Paragraph(""));
            table3.addCell(cell);

            cell = new Cell().setBackgroundColor(Gray).add(new Paragraph("检测人").setBold());
            table3.addCell(cell);
            cell = new Cell().setHeight(18).add(new Paragraph(""));
            table3.addCell(cell);

            cell = new Cell().setBackgroundColor(Gray).add(new Paragraph("审批人").setBold());
            table3.addCell(cell);
            cell = new Cell().setHeight(18).add(new Paragraph(""));
            table3.addCell(cell);

            //标题
            document.add(new Paragraph("ICitrus柑橘产业服务平台").setFont(getPdfFont()).setTextAlignment(TextAlignment.CENTER).setFontSize(23).setBold());
            document.add(new Paragraph("检测报告单").setFont(getPdfFont()).setTextAlignment(TextAlignment.CENTER).setFontSize(20).setBold());
            //加载第一个表格
            document.add(table1);
            //加载第二个表格
            document.add(table2);
            //加载第三个表格
            document.add(table3);

            //关闭的文档
            document.close();

            byte[] bytes = out.toByteArray();
            out.close();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
