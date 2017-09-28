package report;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.junit.Test;

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

public class Demo {
	//
	@Test
	public void test1() {
		try {
			String dest = "D:/hello_world.pdf";
			PdfWriter writer = new PdfWriter(dest);
			PdfDocument pdf = new PdfDocument(writer);
			Document document = new Document(pdf,PageSize.A4);
			PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", false);
		
			Table table = new Table(new float[] { 47,88,47,88 }) .setWidthPercent(80).setTextAlignment(TextAlignment.CENTER);
			table.setFixedLayout().setMarginTop(100).setMinHeight(18); 
			for(int i=0;i<4;i++){  
	            table.addCell(new Cell().add(new Paragraph(""+(i+1))));  
	        } 
			//表格行合并"2"代表合并2行单元格  
	        Cell cell=new Cell(2,1).add(new Paragraph("one"));
	       
	        table.addCell(cell);  
	        //表格列合并"3"代表合并3列  
	        cell=new Cell(1,3).add(new Paragraph("two"));  
	        table.addCell(cell);  
	        //将剩余格补齐  
	        cell=new Cell().add(new Paragraph("three"));  
	        table.addCell(cell);  
	        cell=new Cell().add(new Paragraph(""));  
	        table.addCell(cell);  
	        cell=new Cell().add(new Paragraph("three"));  
	        table.addCell(cell);  
			
	        Table table1 = new Table(new float[] { 47,88,47,88 }) .setWidthPercent(80);
	        for(int i=0;i<4;i++){  
	            table1.addCell(new Cell().add(new Paragraph(""+(i+1))).setBorderTop(Border.NO_BORDER).setHeight(26.40f));  
	        }
	        //table1.setMarginTop(10);
	        table1.setFixedLayout();
	        document.add(new Paragraph("企业信用报告").setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(20));
			document.add(new Paragraph(" "));
	        document.add(table.setHorizontalAlignment(HorizontalAlignment.CENTER));
			document.add(table1.setHorizontalAlignment(HorizontalAlignment.CENTER));
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test2() {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			PdfWriter writer = new PdfWriter(out);
			PdfDocument pdf = new PdfDocument(writer);
			Document document = new Document(pdf,PageSize.A4);
			PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", false);
		
			Table table = new Table(new float[] { 47,88,47,88 }) .setWidthPercent(80).setFont(font).setTextAlignment(TextAlignment.CENTER);
			Cell cell = new Cell().add(new Paragraph("植株编号")).setBackgroundColor(new DeviceRgb(242,242,242));
			table.addCell(cell);
			
			cell = new Cell().add(new Paragraph("QC20170817"));
			table.addCell(cell);
			
			cell = new Cell().add(new Paragraph("品种")).setBackgroundColor(new DeviceRgb(242,242,242));
			table.addCell(cell);
			
			cell = new Cell().add(new Paragraph("脐橙"));
			table.addCell(cell);
			
			document.add(new Paragraph("企业信用报告").setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(20).setBold());
			document.add(new Paragraph(" "));
			document.add(table.setHorizontalAlignment(HorizontalAlignment.CENTER));
			document.close();
			
			FileOutputStream outputStream = new FileOutputStream(new File("D:/","企业信用调查"));
            outputStream.write(out.toByteArray());
            outputStream.flush();
            outputStream.close();
		} catch (Exception e) {
			
		}
	}
}
