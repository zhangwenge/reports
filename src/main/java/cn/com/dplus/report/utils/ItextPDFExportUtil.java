package cn.com.dplus.report.utils;

import java.io.ByteArrayOutputStream;
import java.util.List;

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

import cn.com.dplus.project.utils.DateTimeUtils;
import cn.com.dplus.report.entity.Orchard;
import cn.com.dplus.report.entity.PlantDetectRecord;
import cn.com.dplus.report.entity.PlantInfo;

public class ItextPDFExportUtil {
	
	public static byte[] exportPDF(PlantInfo plantInfo,Orchard orchard,List<PlantDetectRecord> plantDetectRecordList) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			//创建一个编写PDF文件的对象
			PdfWriter writer = new PdfWriter(out);
			//PdfDocument是管理添加的内容
			PdfDocument pdf = new PdfDocument(writer);
			//创建一个文档对象,并设置大小是A4
			Document document = new Document(pdf,PageSize.A4);
			
			//设置特定的编码  使得兼容 --中文
			PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", false);
			
			//灰色背景
			DeviceRgb Gray = new DeviceRgb(242,242,242);
			
			/**设置第一个的表格*/
			Table table1 = new Table(new float[] { 47,88,47,88 }) .setWidthPercent(80);
			//设置高度18毫米，设置边框固定，距离顶部20毫米  字体兼容中文  表格居中
			table1.setMinHeight(18).setFixedLayout().setMarginTop(20).setFont(font).setHorizontalAlignment(HorizontalAlignment.CENTER);
			//植株编号
			Cell cell = new Cell().add(new Paragraph("植株编号").setBold()).setBackgroundColor(Gray);
			table1.addCell(cell);
			cell = new Cell().add(new Paragraph(plantInfo.getPlantNo()));
			table1.addCell(cell);
			//品种
			cell = new Cell().add(new Paragraph("品种").setBold()).setBackgroundColor(Gray);
			table1.addCell(cell);
			cell = new Cell().add(new Paragraph(plantInfo.getBreedName()!=null?plantInfo.getBreedName():"--"));
			table1.addCell(cell);
			//检测结果
			cell = new Cell().add(new Paragraph("检测结果").setBold()).setBackgroundColor(Gray);
			table1.addCell(cell);
			cell = new Cell().add(new Paragraph(plantInfo.getLabelState())).setFont(font);
			table1.addCell(cell);
			//植株状态
			cell = new Cell().add(new Paragraph("植株状态").setBold()).setBackgroundColor(Gray);
			table1.addCell(cell);
			switch (plantInfo.getPlantState()) {
			case 1:
				cell = new Cell().add(new Paragraph("正常"));
				table1.addCell(cell);
				break;
			case 2:
				cell = new Cell().add(new Paragraph("待送检"));
				table1.addCell(cell);
				break;
			case 3:
				cell = new Cell().add(new Paragraph("待收样"));
				table1.addCell(cell);
				break;
			case 4:
				cell = new Cell().add(new Paragraph("已收样"));
				table1.addCell(cell);
				break;
			case 5:
				cell = new Cell().add(new Paragraph("诊断完成"));
				table1.addCell(cell);
				break;
			case 6:
				cell = new Cell().add(new Paragraph("已砍树"));
				table1.addCell(cell);
				break;
			default:
				break;
			}
			//果园名称
			cell = new Cell().add(new Paragraph("果园").setBold()).setBackgroundColor(Gray);
			table1.addCell(cell);
			cell = new Cell().add(new Paragraph(orchard.getOrcName()!=null?orchard.getOrcName():"--"));
			table1.addCell(cell);
			//负责人
			cell = new Cell().add(new Paragraph("负责人").setBold()).setBackgroundColor(Gray);
			table1.addCell(cell);
			cell = new Cell().add(new Paragraph(orchard.getLinkMan()!=null?orchard.getLinkMan():"--"));
			table1.addCell(cell);
			//联系电话
			cell = new Cell().add(new Paragraph("联系电话").setBold()).setBackgroundColor(Gray);
			table1.addCell(cell);
			cell = new Cell().add(new Paragraph(orchard.getPhoneNum()!=null?orchard.getPhoneNum():"--"));
			table1.addCell(cell);
			//果园地址
			cell = new Cell().add(new Paragraph("果园地址").setBold()).setBackgroundColor(Gray);
			table1.addCell(cell);
			cell = new Cell().add(new Paragraph(orchard.getAddress()!=null?orchard.getAddress():"--"));
			table1.addCell(cell);
			
			/**设置第二个的表格*/
			Table table2 = new Table(new float[] {105,78,92}) .setWidthPercent(80);
			//最小高度18mm 边框固定  设置兼容中文  文本字体居中 表格居中
			table2.setMinHeight(18).setFixedLayout().setFont(font).setTextAlignment(TextAlignment.CENTER).setHorizontalAlignment(HorizontalAlignment.CENTER);
			//上面无边框，合并三列 ，字体居中
			cell = new Cell(1,3).setBorderTop(Border.NO_BORDER).add(new Paragraph("各样本检测结果"));
			table2.addCell(cell);
			
			cell = new Cell().setBackgroundColor(Gray).add(new Paragraph("检测时间").setBold());
			table2.addCell(cell);
			cell = new Cell().setBackgroundColor(Gray).add(new Paragraph("样本编号").setBold());
			table2.addCell(cell);
			cell = new Cell().setBackgroundColor(Gray).add(new Paragraph("检测结果").setBold());
			table2.addCell(cell);
			
			int a = plantDetectRecordList.size();
			for(PlantDetectRecord plantDetectRecord:plantDetectRecordList) {
				cell = new Cell().add(new Paragraph(DateTimeUtils.long2FormatString(plantDetectRecord.getCreateTime() , "yyyy/MM/dd HH:mm:ss")));
				table2.addCell(cell);
				cell = new Cell().add(new Paragraph("#"+a));
				table2.addCell(cell);
				cell = new Cell().add(new Paragraph(plantDetectRecord.getLabelState()));
				table2.addCell(cell);
			}
			
			/**设置第三个表格 */
			Table table3 = new Table(new float[] { 47,88,47,88 }) .setWidthPercent(80);
			//字体兼容中文 --边框固定--  
			table3.setFont(font).setFixedLayout().setHorizontalAlignment(HorizontalAlignment.CENTER);
			
			cell = new Cell().setBackgroundColor(Gray).setHeight(26.40f).add(new Paragraph("备注").setBold().setVerticalAlignment(VerticalAlignment.MIDDLE));
			table3.addCell(cell);
			cell = new Cell(1,3).setHeight(26.40f).add(new Paragraph(""));
			table3.addCell(cell);
			
			cell = new Cell().setBackgroundColor(Gray).setHeight(18).add(new Paragraph("检测人").setBold());
			table3.addCell(cell);
			cell = new Cell().setHeight(18).add(new Paragraph(""));
			table3.addCell(cell);
			
			cell = new Cell().setBackgroundColor(Gray).setHeight(18).add(new Paragraph("审批人").setBold());
			table3.addCell(cell);
			cell = new Cell().setHeight(18).add(new Paragraph(""));
			table3.addCell(cell);
			
			//标题
			document.add(new Paragraph("检测报告单").setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(20).setBold());
			//加载第一个表格
			document.add(table1);
			//加载第二个表格
			document.add(table2);
			//加载第三个表格
			document.add(table3);
			
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
