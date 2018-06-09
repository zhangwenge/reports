package cn.com.dplus.report.utils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.com.dplus.report.entity.mysql.DevBind;
import cn.com.dplus.report.entity.others.Content;
import cn.com.dplus.report.entity.others.Head;
/**
 * 
 *  @类功能:	TODO	文件导出工具类
 *	@文件名:	PoiExcelExportUtil.java
 * 	@所在包:	net.sondon.dplus.quickdetect.util
 *	@开发者:	黄先国
 * 	@邮_件:   hsamgle@qq.com
 *  @时_间:	2015年12月22日上午10:19:09
 *	@公_司:	广州讯动网络科技有限公司
 */
@SuppressWarnings("deprecation")
public class PoiExcelExportUtil {


	public static Workbook getWorkBook(HttpServletResponse response,String fileName)throws Exception {
		response.reset();// 清空输出流
		response.setHeader("Content-disposition", "attachment; filename="+ new String(fileName.getBytes("GBK"), "ISO8859-1"));
		//新建excel表
		Workbook workbook = null;
		if(fileName.contains(".xlsx")){
			//需要导出的为2007版本以上的excel表
			// 设定输出文件头
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");// 定义输出类型
			workbook = new XSSFWorkbook();
		}else if(fileName.contains(".xls")){
			//需要导出的是97~2003 版本的excel表
			response.setContentType("application/vnd.ms-excel");// 定义输出类型
			workbook = new HSSFWorkbook();
		}else{
			//需要外抛类型错误信息
			throw new Exception("文件类型错误");
		}
		return  workbook;
	}

	private static  Workbook getWorkBook(String fileName)throws Exception{
		//新建excel表
		Workbook workbook = null;
		if(fileName.contains(".xlsx")){
			workbook = new XSSFWorkbook();
		}else if(fileName.contains(".xls")){
			workbook = new HSSFWorkbook();
		}else{
			//需要外抛类型错误信息
			throw new Exception("文件类型错误");
		}
		return  workbook;
	}
	/**
	 * 
	 * @方法功能：	TODO	执行POI的文件导出
	 * @方法名称：	exportExcel
	 * @编写时间：	2015年11月27日上午9:21:42
	 * @开发者：	  黄先国
	 * @方法参数：	@param title
	 * @方法参数：	@param headers
	 * @方法参数：	@param listContent
	 * @方法参数：	@param response
	 * @方法参数：	@param fileName
	 * @方法参数：	@throws Exception
	 * @返回值:	void
	 */
	@SuppressWarnings("unchecked")
	public static void exportExcel(Object[] headers,Map<String,List<?>> listContent, HttpServletResponse response, String fileName,TreeMap<String, TreeSet<String>> moreHead)
			throws Exception {
		OutputStream out = response.getOutputStream();// 取得输出流
		//新建excel表
		Workbook workbook = getWorkBook(response,fileName);

		/**
		 * 设备表头的样式
		 * 列宽20                  行高40       浅绿色背景               浅边框        
		 * 
		 * 字体粗体        颜色 白色          14号字体
		 * 
		 */
		CellStyle style_Header = workbook.createCellStyle();
		style_Header.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style_Header.setAlignment(CellStyle.ALIGN_CENTER); //设置表头文本居中
		style_Header.setAlignment(CellStyle.VERTICAL_CENTER); //设置表头文本居中
		style_Header.setFillBackgroundColor(IndexedColors.GREEN.index);  //设置前景填充为浅绿色
		style_Header.setFillForegroundColor(IndexedColors.GREEN.index);  //设置前景填充为浅绿色
		style_Header.setBorderBottom(CellStyle.BORDER_THIN); 		//设置底边框
		style_Header.setBorderLeft(CellStyle.BORDER_THIN); 			//设置左边框
		style_Header.setBorderRight(CellStyle.BORDER_THIN); 		//设置右边框
		style_Header.setBorderTop(CellStyle.BORDER_THIN); 			//设置上边框
		
		Font font_Header = workbook.createFont();		//设置表头的字体
		font_Header.setBoldweight(Font.BOLDWEIGHT_BOLD);  //设置表头字体为粗体
		font_Header.setColor(IndexedColors.WHITE.index); 		 //设置字体的颜色为白色
		font_Header.setFontHeightInPoints((short)12);				//设置字体的大小
		style_Header.setFont(font_Header);					//为表头应用样式
		
		
		
		/**
		 * 设置单数行的样式
		 * 
		 * 普通样式      行高20   列宽 20   浅边框     无填充颜色
		 * 
		 * 字体  颜色黑色          11 号字体
		 * 
		 */
		CellStyle style_Odd = workbook.createCellStyle();
		style_Odd.setBorderBottom(CellStyle.BORDER_THIN);   //设置底边框
		style_Odd.setBorderTop(CellStyle.BORDER_THIN);   	//设置上边框
		style_Odd.setBorderLeft(CellStyle.BORDER_THIN);     //设置左边框
		style_Odd.setBorderRight(CellStyle.BORDER_THIN);    //设置右边框
		style_Odd.setAlignment(CellStyle.VERTICAL_CENTER);  //内存垂直居中
		
		
		Font font_content = workbook.createFont();		//设置内容的字体
		font_content.setBoldweight(Font.BOLDWEIGHT_NORMAL);  //设置表头字体为体
		font_content.setColor(IndexedColors.BLACK.index); 		 //设置字体的颜色为黑色
		font_content.setFontHeightInPoints((short)11);		 //设置字体的大小
		
		style_Odd.setFont(font_content);
		
		/**
		 * 设置双数行的样式
		 * 
		 * 普通样式      行高20   列宽 20   浅边框     浅橘黄色颜色
		 * 
		 * 字体  颜色黑色          11 号字体
		 * 
		 */
		CellStyle style_Even = workbook.createCellStyle();
		style_Even.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style_Even.setBorderBottom(CellStyle.BORDER_THIN);   //设置底边框
		style_Even.setBorderTop(CellStyle.BORDER_THIN);   	//设置上边框
		style_Even.setBorderLeft(CellStyle.BORDER_THIN);     //设置左边框
		style_Even.setBorderRight(CellStyle.BORDER_THIN);    //设置右边框
		style_Even.setAlignment(CellStyle.VERTICAL_CENTER);  //内存垂直居中
		style_Even.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index); //填充颜色为橘黄色
		style_Even.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.index); //填充颜色为橘黄色
		style_Even.setFont(font_content);
		
		//遍历需要导出的内容
		for (Entry<String, List<?>> content : listContent.entrySet()) {
			
			String sheetTile = content.getKey();
			List<?> list = content.getValue();
			
			TreeSet<String> moreHeads = null;
			
			Object[] allHeads = null;
			
			if(moreHead!=null){
				moreHeads = moreHead.get(sheetTile);
				Object[] ms = moreHeads.toArray();
				Object[] all = new String[headers.length+ms.length];  
				System.arraycopy(headers, 0, all, 0, headers.length);  
				System.arraycopy(ms, 0, all, headers.length, ms.length);
				allHeads = new Object[all.length];
				allHeads = all;
			}else{
				allHeads = new Object[headers.length];
				allHeads = headers;
			}
			
			// 声明一个工作簿
			// 生成一个表格
			Sheet sheet = workbook.createSheet(sheetTile);
			sheet.setDefaultColumnWidth(8);
			
			// 产生表格标题行
			Row row = sheet.createRow(0);
			for (int i = 0; i < allHeads.length; i++) {// 遍历标题
				Cell cell = row.createCell(i);
				cell.setCellStyle(style_Header);
				cell.setCellValue(allHeads[i].toString());
				cell.getRow().setHeight((short) 350);	//设置行高
			}
			
			
			Field[] fields = null;
			//遍历填充数据
			Object obj = null;
			Cell cell = null;
			for (int i = 0; i < list.size(); i++) {
				obj = list.get(i);
				row = sheet.createRow(i + 1);
				fields = obj.getClass().getDeclaredFields(); // 反射获取
				//通过反射的方式来获取数据
				for (int j = 0; j < fields.length; j++) {
					
					Field v = fields[j];
					v.setAccessible(true);
					
					if(v.getType() == TreeMap.class){
						//是map类型
						TreeMap<Object,Object> treeMap  = (TreeMap<Object, Object>) v.get(obj);
						int a = 0;
						for (String more : moreHeads) {
							Object con = treeMap.get(more);
							if (con == null) {
								//如果该字段没值，则置为“”
								con = "";
							}
							cell = row.createCell(j+a);
							cell.getRow().setHeight((short) 400);
							//往里面添加数据
							String data = con.toString();
							cell.setCellValue(data);
							if((i+1)%2==0){
								//双数行
								cell.setCellStyle(style_Even);
							}else{
								//单双行
								cell.setCellStyle(style_Odd);
							}
							if(data.length()>5){
								int len = data.length();
								if(len<=15){
									sheet.setColumnWidth(j+a,con.toString().length() * 512);
								}else if(len > 15 && len<=20){
									sheet.setColumnWidth(j+a,con.toString().length() * 380);
								}else{
									sheet.setColumnWidth(j+a,con.toString().length() * 256);
								}
							}
							a++;
						}
					}else{
						Object va = v.get(obj);
						if (va == null) {
							//如果该字段没值，则置为“”
							va = " ";
						}
						cell = row.createCell(j);
						cell.getRow().setHeight((short) 400);
						//往里面添加数据
						String data = va.toString();
						cell.setCellValue(data);
						if((i+1)%2==0){
							//双数行
							cell.setCellStyle(style_Even);
						}else{
							//单双行
							cell.setCellStyle(style_Odd);
						}
						if(data.length()>5){
							int len = data.length();
							if(len<=15){
								sheet.setColumnWidth(j,va.toString().length() * 512);
							}else if(len > 15 && len<=20){
								sheet.setColumnWidth(j,va.toString().length() * 380);
							}else{
								sheet.setColumnWidth(j,va.toString().length() * 256);
							}
						}
					}
				}
			}
		}
		
		workbook.write(out);
		workbook.close();
	}

	/**
	 *
	 * 设备表头的样式
	 * 浅绿色背景               浅边框
	 * 字体粗体        颜色 黑色          12号字体
	 * @param workbook
	 * @return
     */
	private static  CellStyle getHeaderCellStyle(Workbook workbook){
		CellStyle headStyle = workbook.createCellStyle();
		headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headStyle.setBorderBottom(BorderStyle.THIN);	//设置底边框
		headStyle.setBorderTop(BorderStyle.THIN);   	//设置上边框
		headStyle.setBorderLeft(BorderStyle.THIN);     //设置左边框
		headStyle.setBorderRight(BorderStyle.THIN);    //设置右边框
		headStyle.setAlignment(HorizontalAlignment.LEFT);  //垂直靠左居中
		headStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
		headStyle.setFillBackgroundColor( IndexedColors.LIGHT_GREEN.index); //设置前景填充为浅绿色
		headStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);  //设置前景填充为浅绿色
		
		Font headFont = workbook.createFont();//设置头部的字体
		headFont.setBold(true);	//设置为粗体
		headFont.setColor(IndexedColors.BLACK.index); //设置字体颜色为黑色
		headFont.setFontHeightInPoints((short)12);	//设置字体大小为12
		headFont.setFontName("微软雅黑");
		
		headStyle.setFont(headFont);
		return  headStyle;
	}
	
	/**
	  字体垂直居中靠左 白色背景  黑色字体 10号
	 */
	private static  CellStyle getBodyStyle1(Workbook workbook) {
		CellStyle bodyStyle1 = workbook.createCellStyle();
		bodyStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		bodyStyle1.setBorderBottom(BorderStyle.THIN);	//设置底边框
		bodyStyle1.setBorderTop(BorderStyle.THIN);   	//设置上边框
		bodyStyle1.setBorderLeft(BorderStyle.THIN);     //设置左边框
		bodyStyle1.setBorderRight(BorderStyle.THIN);    //设置右边框
		bodyStyle1.setAlignment(HorizontalAlignment.LEFT);  //垂直靠左居中
		bodyStyle1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
		bodyStyle1.setFillBackgroundColor( IndexedColors.WHITE.index);
		bodyStyle1.setFillForegroundColor(IndexedColors.WHITE.index);
		
		Font bodyFont1 = workbook.createFont();
		bodyFont1.setFontHeightInPoints((short)10);
		bodyFont1.setColor(IndexedColors.BLACK.index); //设置字体颜色为黑色
		bodyFont1.setFontName("微软雅黑");
		
		bodyStyle1.setFont(bodyFont1);
		return bodyStyle1;
	}
	/**
	  字体靠右 白色背景  黑色字体 10号 
	 */
	private static  CellStyle getBodyStyle2(Workbook workbook) {
		CellStyle bodyStyle2 = workbook.createCellStyle();
		bodyStyle2.setBorderBottom(BorderStyle.THIN);
		bodyStyle2.setBorderTop(BorderStyle.THIN);
		bodyStyle2.setBorderLeft(BorderStyle.THIN);
		bodyStyle2.setBorderRight(BorderStyle.THIN);
		bodyStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		bodyStyle2.setAlignment(HorizontalAlignment.RIGHT);  //靠右居中
		bodyStyle2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
		bodyStyle2.setFillBackgroundColor( IndexedColors.WHITE.index);
		bodyStyle2.setFillForegroundColor(IndexedColors.WHITE.index);
		
		Font bodyFont2 = workbook.createFont();
		bodyFont2.setFontHeightInPoints((short)10);
		bodyFont2.setColor(IndexedColors.BLACK.index); //设置字体颜色为黑色
		bodyFont2.setFontName("微软雅黑");
		
		bodyStyle2.setFont(bodyFont2);
		return bodyStyle2;
	}
	/**
	 字体靠右 白色背景  红色字体 10号 
	 */
	private static  CellStyle getBodyStyle3(Workbook workbook) {
		CellStyle bodyStyle3 = workbook.createCellStyle();
		bodyStyle3.setBorderBottom(BorderStyle.THIN);
		bodyStyle3.setBorderTop(BorderStyle.THIN);
		bodyStyle3.setBorderLeft(BorderStyle.THIN);
		bodyStyle3.setBorderRight(BorderStyle.THIN);
		bodyStyle3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		bodyStyle3.setAlignment(HorizontalAlignment.RIGHT);  //靠右居中
		bodyStyle3.setFillBackgroundColor( IndexedColors.WHITE.index);
		bodyStyle3.setFillForegroundColor(IndexedColors.WHITE.index);
		
		Font bodyFont3 = workbook.createFont();
		bodyFont3.setFontHeightInPoints((short)10);
		bodyFont3.setColor(IndexedColors.RED.index); //设置字体颜色为红色
		bodyFont3.setFontName("微软雅黑");
		
		bodyStyle3.setFont(bodyFont3);
		
		return bodyStyle3;
	}
	
	/** 蓝色背景   靠左  10号字体   黑色*/
	private static  CellStyle getBodyStyle4(Workbook workbook) {
		CellStyle bodyStyle4 = workbook.createCellStyle();
		bodyStyle4.setBorderBottom(BorderStyle.THIN);
		bodyStyle4.setBorderTop(BorderStyle.THIN);
		bodyStyle4.setBorderLeft(BorderStyle.THIN);
		bodyStyle4.setBorderRight(BorderStyle.THIN);
		bodyStyle4.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		bodyStyle4.setAlignment(HorizontalAlignment.LEFT);  
		bodyStyle4.setFillBackgroundColor( IndexedColors.ROYAL_BLUE.index);
		bodyStyle4.setFillForegroundColor(IndexedColors.ROYAL_BLUE.index);
		
		Font bodyFont4 = workbook.createFont();
		bodyFont4.setFontHeightInPoints((short)10);
		bodyFont4.setColor(IndexedColors.BLACK.index); //设置字体颜色为黑色
		bodyFont4.setFontName("微软雅黑");
		
		bodyStyle4.setFont(bodyFont4);
		
		return bodyStyle4;
	}
	//蓝色背景，黑色字体 ，靠右垂直居中
	private static  CellStyle getBodyStyle5(Workbook workbook) {
		CellStyle bodyStyle5 = workbook.createCellStyle();
		bodyStyle5.setBorderBottom(BorderStyle.THIN);
		bodyStyle5.setBorderTop(BorderStyle.THIN);
		bodyStyle5.setBorderLeft(BorderStyle.THIN);
		bodyStyle5.setBorderRight(BorderStyle.THIN);
		bodyStyle5.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		bodyStyle5.setAlignment(HorizontalAlignment.RIGHT);  
		bodyStyle5.setFillBackgroundColor( IndexedColors.ROYAL_BLUE.index);
		bodyStyle5.setFillForegroundColor(IndexedColors.ROYAL_BLUE.index);
		
		Font bodyFont5 = workbook.createFont();
		bodyFont5.setFontHeightInPoints((short)10);
		bodyFont5.setColor(IndexedColors.BLACK.index); //设置字体颜色为黑色
		bodyFont5.setFontName("微软雅黑");
		
		bodyStyle5.setFont(bodyFont5);
		return bodyStyle5;
	}
	//蓝色背景 红色字体 靠右垂直居中
	private static  CellStyle getBodyStyle6(Workbook workbook) {
		CellStyle bodyStyle6 = workbook.createCellStyle();
		bodyStyle6.setBorderBottom(BorderStyle.THIN);
		bodyStyle6.setBorderTop(BorderStyle.THIN);
		bodyStyle6.setBorderLeft(BorderStyle.THIN);
		bodyStyle6.setBorderRight(BorderStyle.THIN);
		bodyStyle6.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		bodyStyle6.setAlignment(HorizontalAlignment.RIGHT);  //靠右居中
		bodyStyle6.setFillBackgroundColor( IndexedColors.ROYAL_BLUE.index);
		bodyStyle6.setFillForegroundColor(IndexedColors.ROYAL_BLUE.index);
		
		Font bodyFont6 = workbook.createFont();
		bodyFont6.setFontHeightInPoints((short)10);
		bodyFont6.setColor(IndexedColors.RED.index); //设置字体颜色为红色
		bodyFont6.setFontName("微软雅黑");
		
		bodyStyle6.setFont(bodyFont6);
		return bodyStyle6;
	}
	
	//解决单元格和并后出现的 边框缺少的问题
	private static void RegionCell(CellRangeAddress cellRangeAddress,Sheet sheet) {
		RegionUtil.setBorderLeft(1, cellRangeAddress, sheet);
		RegionUtil.setBorderRight(1, cellRangeAddress, sheet);
		RegionUtil.setBorderTop(1, cellRangeAddress, sheet);
		RegionUtil.setBorderBottom(1, cellRangeAddress, sheet);
	}
	/**
	 * 
	 * @描__述: 理化值列表的导出的
	 * @方法功能：TODO
	 * @方法名称：exportExcel
	 * @编写时间：2017年9月24日下午2:37:07
	 * @开发者  ：张文歌
	 * @方法参数：
	 * @返回值：
	 * @接口文档：
	 */
	public static byte[] exportExcel(List<Content> contents,String fileName) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		//创建一个excel表
		Workbook workbook = getWorkBook(fileName);

		//使用调色板来  对颜色进行定义    
		HSSFPalette palette = ((HSSFWorkbook) workbook).getCustomPalette();
		palette.setColorAtIndex(IndexedColors.LIGHT_GREEN.index, (byte) 226, (byte) 239, (byte) 217);
		
		//创建一个头部的style
		CellStyle headStyle = getHeaderCellStyle(workbook);
		
		//为身体的前4列使用
		CellStyle bodyStyle1 = getBodyStyle1(workbook);
		
		//为身体体的第五列以后使用
		CellStyle bodyStyle2 = getBodyStyle2(workbook);
		
		//为标红色的cell使用
		CellStyle bodyStyle3 = getBodyStyle3(workbook);
		//创建多个sheet
		for (Content content : contents) {
			Sheet sheet = workbook.createSheet(content.getBreedName());
			//样品编号  设备   检测时间   检测方案
			CellRangeAddress cellRangeAddress1 = new CellRangeAddress(0,1,0,0);
			CellRangeAddress cellRangeAddress2 = new CellRangeAddress(0,1,1,1);
			CellRangeAddress cellRangeAddress3 = new CellRangeAddress(0,1,2,2);
			CellRangeAddress cellRangeAddress4 = new CellRangeAddress(0,1,3,3);
			sheet.addMergedRegion(cellRangeAddress1);
			sheet.addMergedRegion(cellRangeAddress2);
			sheet.addMergedRegion(cellRangeAddress3);
			sheet.addMergedRegion(cellRangeAddress4);
			
			//设置列 宽  把前四列的列宽全部固定下来
			sheet.setColumnWidth(0,256*14+184);
			sheet.setColumnWidth(1,256*18+184);
			sheet.setColumnWidth(2,256*19+184);
			sheet.setColumnWidth(3,256*13+184);
			
			Row row0 = sheet.createRow(0);
			Row row1 = sheet.createRow(1);
			row0.setHeight((short)(288*2));
			row1.setHeight((short)(288*2));
			
			//创建一个单元格，设置值，设单元格信息
			Cell cell1 = row0.createCell(0);
			cell1.setCellStyle(headStyle);
			cell1.setCellValue("样品编号");
			Cell cell2 = row0.createCell(1);
			cell2.setCellStyle(headStyle);
			cell2.setCellValue("设备");
			Cell cell3 = row0.createCell(2);
			cell3.setCellStyle(headStyle);
			cell3.setCellValue("检测时间");
			Cell cell4 = row0.createCell(3);
			cell4.setCellStyle(headStyle);
			cell4.setCellValue("检测方案");
			
			//获得头部的数据
			List<Head> headList = content.getHeadList();
			//控制指标  --标记参数
			int a = 4;
			//控制模型名称理化值  --标记参数
			int b = 4;
			for (Head head : headList) {
				String indicatorName = head.getIndicatorName();
				Integer indicatorType = head.getIndicatorType();
				Float innerCtrValueMax = head.getInnerCtrValueMax();
				Float innerCtrValueMin = head.getInnerCtrValueMin();
				String unit = head.getUnit();
				Map<String, String> models = head.getModels();
				int size = models.size();
				
				Cell cell = row0.createCell(a);
				CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, a, a+size);
				sheet.addMergedRegion(cellRangeAddress);
				
				//解决边框缺少的问题
				RegionCell(cellRangeAddress,sheet);
				
				a=a+size+1;
				
				if(indicatorType==1) {
					cell.setCellValue(indicatorName);
					cell.setCellStyle(headStyle);
				}else {
					if(innerCtrValueMax!=null&&innerCtrValueMin!=null) {
						cell.setCellValue(indicatorName+" "+"（内控标准："+innerCtrValueMin+"-"+innerCtrValueMax+"）");
						cell.setCellStyle(headStyle);
					}else {
						cell.setCellValue(indicatorName+" "+"（内控标准：--）");
						cell.setCellStyle(headStyle);
					}
				}
				for(String key:models.keySet()) {
					String modelName = models.get(key);
					if(indicatorType==1) {
						Cell createCell = row1.createCell(b);
						createCell.setCellValue(modelName);
						createCell.setCellStyle(headStyle);
						int len = modelName.length();
						sheet.setColumnWidth(b, len*512);
					}else {
						Cell createCell = row1.createCell(b);
						createCell.setCellValue(modelName+"（"+unit+"）");
						createCell.setCellStyle(headStyle);
						int len = (modelName+"（"+unit+"）").length();
						sheet.setColumnWidth(b, len*512);
					}
					b++;
				}
				Cell createCell1 = row1.createCell(b);
				createCell1.setCellValue("理化值");
				createCell1.setCellStyle(headStyle);
				sheet.setColumnWidth(b,"理化值".length()*512*2);
				b++;
			}
			//解决单元格合并的缺少的问题
			RegionCell(cellRangeAddress1,sheet);
			RegionCell(cellRangeAddress2,sheet);
			RegionCell(cellRangeAddress3,sheet);
			RegionCell(cellRangeAddress4,sheet);
			
			//参数设置   冻结的列数，冻结的行数，表示右边区域可见的首列序号从1开始计算，下边区域可见的首行序号从1开始计算
			sheet.createFreezePane(0, 2, 0, 2);
			
			//开始遍历身体
			int c = 2;
			List<List<Object>> bodyList = content.getBodyList();
			for (List<Object> list : bodyList) {
				Row rowBody = sheet.createRow(c);
				rowBody.setHeight((short)(20*20));
				c++;
				int d = 0;
				for (Object cellValue : list) {
					Cell createCell = rowBody.createCell(d);
					String cellVal = (String) cellValue;
					if(d<4) {
						createCell.setCellStyle(bodyStyle1);
						createCell.setCellValue(cellVal.substring(0,cellVal.length()-1));
					}else {
						createCell.setCellValue(cellVal.substring(0,cellVal.length()-1));
						if(cellVal.substring(cellVal.length()-1).equals("0")) {
							createCell.setCellStyle(bodyStyle2);
						}else {
							createCell.setCellStyle(bodyStyle3);
						}
					}
					d++;
				}
			}
			
		}
		workbook.write(out);
		workbook.close();
		byte[] bytes = out.toByteArray();
		out.close();
		return bytes;
	}
	
	
	/**
	 * 
	 * @描__述: 检测记录列表导出
	 * @方法功能：TODO
	 * @方法名称：exportExcel1
	 * @编写时间：2017年9月24日下午2:37:40
	 * @开发者  ：张文歌
	 * @方法参数：
	 * @返回值：
	 * @接口文档：
	 */
	public static byte[] exportExcel1(List<Content> contents,String fileName) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		//创建一个excel表
		Workbook workbook = getWorkBook(fileName);
		
		//使用调色板来  对颜色进行定义
		HSSFPalette palette = ((HSSFWorkbook) workbook).getCustomPalette();
		palette.setColorAtIndex(IndexedColors.LIGHT_GREEN.index, (byte) 226, (byte) 239, (byte) 217);
		palette.setColorAtIndex(IndexedColors.ROYAL_BLUE.index, (byte)222, (byte)235, (byte)246);
		
		//创建一个头部的style
		CellStyle headStyle = getHeaderCellStyle(workbook);
		
		//为身体的前4列使用     靠左  白色背景   黑色字体
		CellStyle bodyStyle1 = getBodyStyle1(workbook);
		
		//为身体体的第五列以后使用  靠右  白色背景  黑色字体
		CellStyle bodyStyle2 = getBodyStyle2(workbook);
		
		//靠右  白色背景  红色字体
		CellStyle bodyStyle3 = getBodyStyle3(workbook);
		
		//平均值前四列      蓝色背景   黑色字体   靠左
		CellStyle bodyStyle4 = getBodyStyle4(workbook);
		
		//平均值      蓝色背景   黑色字体   靠右
		CellStyle bodyStyle5 = getBodyStyle5(workbook);
		
		//靠右  蓝色背景  红色字体
		CellStyle bodyStyle6 = getBodyStyle6(workbook);
		
		for (Content content : contents) {
			Sheet sheet = workbook.createSheet(content.getBreedName());
			//样品编号  设备   检测时间   检测方案
			CellRangeAddress cellRangeAddress1 = new CellRangeAddress(0,1,0,0);
			CellRangeAddress cellRangeAddress2 = new CellRangeAddress(0,1,1,1);
			CellRangeAddress cellRangeAddress3 = new CellRangeAddress(0,1,2,2);
			CellRangeAddress cellRangeAddress4 = new CellRangeAddress(0,1,3,3);
			sheet.addMergedRegion(cellRangeAddress1);
			sheet.addMergedRegion(cellRangeAddress2);
			sheet.addMergedRegion(cellRangeAddress3);
			sheet.addMergedRegion(cellRangeAddress4);
			
			//设置列 宽
			sheet.setColumnWidth(0,256*14+184);
			sheet.setColumnWidth(1,256*18+184);
			sheet.setColumnWidth(2,256*19+184);
			sheet.setColumnWidth(3,256*13+184);
			
			Row row0 = sheet.createRow(0);
			Row row1 = sheet.createRow(1);
			row0.setHeight((short)(288*2));
			row1.setHeight((short)(288*2));
			
			//创建一个单元格，设置值，设单元格信息
			Cell cell1 = row0.createCell(0);
			cell1.setCellStyle(headStyle);
			cell1.setCellValue("样品编号");
			Cell cell2 = row0.createCell(1);
			cell2.setCellStyle(headStyle);
			cell2.setCellValue("设备");
			Cell cell3 = row0.createCell(2);
			cell3.setCellStyle(headStyle);
			cell3.setCellValue("检测时间");
			Cell cell4 = row0.createCell(3);
			cell4.setCellStyle(headStyle);
			cell4.setCellValue("检测方案");
			
			//获得头部的数据
			List<Head> headList = content.getHeadList();
			//控制指标  --标记参数
			int a = 4;
			//控制模型名称理化值  --标记参数
			int b = 4;
			for (Head head : headList) {
				String indicatorName = head.getIndicatorName();
				Integer indicatorType = head.getIndicatorType();
				Float innerCtrValueMax = head.getInnerCtrValueMax();
				Float innerCtrValueMin = head.getInnerCtrValueMin();
				String unit = head.getUnit();
				Map<String, String> models = head.getModels();
				int size = models.size();
				
				Cell cell = row0.createCell(a);
				if(size!=1) {
					CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, a, a+size-1);
					sheet.addMergedRegion(cellRangeAddress);
					RegionCell(cellRangeAddress,sheet);
				}
				
				a=a+size;
				
				if(indicatorType==1) {
					cell.setCellValue(indicatorName);
					cell.setCellStyle(headStyle);
				}else {
					if(innerCtrValueMax!=null&&innerCtrValueMin!=null) {
						cell.setCellValue(indicatorName+" "+"（内控标准："+innerCtrValueMin+"-"+innerCtrValueMax+"）");
						cell.setCellStyle(headStyle);
					}else {
						cell.setCellValue(indicatorName+" "+"（内控标准：--）");
						cell.setCellStyle(headStyle);
					}
				}
				for(String key:models.keySet()) {
					String modelName = models.get(key);
					if(indicatorType==1) {
						Cell createCell = row1.createCell(b);
						createCell.setCellValue(modelName);
						createCell.setCellStyle(headStyle);
						int len = modelName.length();
						sheet.setColumnWidth(b, len*512);
					}else {
						Cell createCell = row1.createCell(b);
						createCell.setCellValue(modelName+"（"+unit+"）");
						createCell.setCellStyle(headStyle);
						int len = (modelName+"（"+unit+"）").length();
						sheet.setColumnWidth(b, len*512);
					}
					b++;
				}
			}
			//合并单元格的后出现的边框缺少的问题的解决
			RegionCell(cellRangeAddress1,sheet);
			RegionCell(cellRangeAddress2,sheet);
			RegionCell(cellRangeAddress3,sheet);
			RegionCell(cellRangeAddress4,sheet);
			
			//参数设置   冻结的列数，冻结的行数，表示右边区域可见的首列序号从1开始计算，下边区域可见的首行序号从1开始计算
			sheet.createFreezePane(0, 2, 0, 2);
			
			//开始遍历身体
			int c = 2;
			List<List<Object>> bodyList = content.getBodyList();
			for (List<Object> list : bodyList) {
				Row rowBody = sheet.createRow(c);
				rowBody.setHeight((short)(20*20));
				c++;
				int d = 0;
				for (Object cellValue : list) {
					Cell createCell = rowBody.createCell(d);
					String cellVal = (String) cellValue;
					if(d<4) {
						if(cellVal.substring(cellVal.length()-1).equals("0")) {
							createCell.setCellStyle(bodyStyle1);
						}else {
							createCell.setCellStyle(bodyStyle4);
						}
						createCell.setCellValue(cellVal.substring(0,cellVal.length()-2));
					}else {
						createCell.setCellValue(cellVal.substring(0,cellVal.length()-2));
						//判断背景   0白色  1蓝色
						if(cellVal.substring(cellVal.length()-1).equals("0")) {
							if(cellVal.substring(cellVal.length()-2,cellVal.length()-1).equals("0")) {
								createCell.setCellStyle(bodyStyle2);
							}else {
								createCell.setCellStyle(bodyStyle3);
							}
						}else {
							if(cellVal.substring(cellVal.length()-2,cellVal.length()-1).equals("0")) {
								createCell.setCellStyle(bodyStyle5);
							}else {
								createCell.setCellStyle(bodyStyle6);
							}
						}
					}
					d++;
				}
			}
		}
		workbook.write(out);
		workbook.close();
		byte[] bytes = out.toByteArray();
		out.close();
		return bytes;
	}
	
	/**
	 * 
	 * @描__述: 统计用户信息数据  （简略版的会做出修改）
	 * @方法功能：TODO
	 * @方法名称：exportExcel2
	 * @编写时间：2017年9月24日下午2:35:43
	 * @开发者  ：张文歌
	 * @方法参数：
	 * @返回值：
	 * @接口文档：
	 */
	public static byte[] exportExcel2(List<List<String>> content,String fileName) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		//创建一个excel表
		Workbook workbook = getWorkBook(fileName);
		
		//使用调色板来  对颜色进行定义
		HSSFPalette palette = ((HSSFWorkbook) workbook).getCustomPalette();
		palette.setColorAtIndex(IndexedColors.LIGHT_GREEN.index, (byte) 226, (byte) 239, (byte) 217);
		
		CellStyle headStyle = workbook.createCellStyle();
		headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headStyle.setBorderBottom(BorderStyle.THIN);	//设置底边框
		headStyle.setBorderTop(BorderStyle.THIN);   	//设置上边框
		headStyle.setBorderLeft(BorderStyle.THIN);     //设置左边框
		headStyle.setBorderRight(BorderStyle.THIN);    //设置右边框
		headStyle.setAlignment(HorizontalAlignment.LEFT);  //垂直靠左居中
		headStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
		headStyle.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.index); //设置前景填充为浅绿色
		headStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);  //设置前景填充为浅绿色
		
		Font headFont = workbook.createFont();//设置头部的字体
		headFont.setBold(true);	//设置为粗体
		headFont.setColor(IndexedColors.BLACK.index); //设置字体颜色为黑色
		headFont.setFontHeightInPoints((short)12);	//设置字体大小为12
		headFont.setFontName("微软雅黑");
		
		headStyle.setFont(headFont);
		
		//为身体的前4列使用     靠左  白色背景   黑色字体
		CellStyle bodyStyle1 = workbook.createCellStyle();
		bodyStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		bodyStyle1.setBorderBottom(BorderStyle.THIN);	//设置底边框
		bodyStyle1.setBorderTop(BorderStyle.THIN);   	//设置上边框
		bodyStyle1.setBorderLeft(BorderStyle.THIN);     //设置左边框
		bodyStyle1.setBorderRight(BorderStyle.THIN);    //设置右边框
		bodyStyle1.setAlignment(HorizontalAlignment.LEFT);  //垂直靠左居中
		bodyStyle1.setFillBackgroundColor( IndexedColors.WHITE.index);
		bodyStyle1.setFillForegroundColor(IndexedColors.WHITE.index);
		
		Font bodyFont1 = workbook.createFont();
		bodyFont1.setFontHeightInPoints((short)11);
		bodyFont1.setColor(IndexedColors.BLACK.index); //设置字体颜色为黑色
		bodyFont1.setFontName("微软雅黑");
		
		bodyStyle1.setFont(bodyFont1);
		
		Sheet sheet = workbook.createSheet("用户检测信息统计");
		int a = 0;
		for (List<String> row : content) {
			Row createRow = sheet.createRow(a);
			createRow.setHeight((short)(165*2));
			Cell cellf = createRow.createCell(0);
			switch (a) {
			case 0:
				cellf.setCellValue("设备的dSn");
				cellf.setCellStyle(headStyle);
				break;
			case 1:
				cellf.setCellValue("统计时间段");
				cellf.setCellStyle(bodyStyle1);
				break;
			case 2:
				cellf.setCellValue("参比次数");
				cellf.setCellStyle(bodyStyle1);
				break;
			case 3:
				cellf.setCellValue("参比失败次数");
				cellf.setCellStyle(bodyStyle1);
				break;
			case 4:
				cellf.setCellValue("参比失败时间");
				cellf.setCellStyle(bodyStyle1);
				break;
			case 5:
				cellf.setCellValue("检测总次数");
				cellf.setCellStyle(bodyStyle1);
				break;
			case 6:
				cellf.setCellValue("样品集扫描光谱总数");
				cellf.setCellStyle(bodyStyle1);
				break;
			default:
				break;
			}
			a++;
			int b = 1;
			for (String cell : row) {
				Cell createCell = createRow.createCell(b);
				createCell.setCellValue(cell);
				if(a==1) {
					createCell.setCellStyle(headStyle);
				}else {
					createCell.setCellStyle(bodyStyle1);
				}
				b++;
			}
		}
		workbook.write(out);
		workbook.close();
		byte[] bytes = out.toByteArray();
		out.close();
		return bytes;
	}
	
	public static byte[] exportReference(Content content,String fileName) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		//创建一个excel表
		Workbook workbook = getWorkBook(fileName);
		CellStyle headStyle = deviceStatistics(workbook);
			Sheet sheet = workbook.createSheet(content.getBreedName());
			//第一，二列   列宽固定
			sheet.setColumnWidth(0,256*15+184);
			sheet.setColumnWidth(1,256*15+184);
			List<Object> headListNO = content.getHeadListNO();
			Row row0 = sheet.createRow(0);
			int a = 0;
			for (Object object : headListNO) {
				Cell createCell = row0.createCell(a);
				if (object instanceof Integer) {
					createCell.setCellValue((Integer) object);
				} else if (object instanceof Double) {
					createCell.setCellValue((Double) object);
				} else if (object instanceof Float) {
					createCell.setCellValue((Float) object);
				} else if (object instanceof Long) {
					createCell.setCellValue((Long) object);
				} else if (object instanceof Boolean) {
					createCell.setCellValue((Boolean) object);
				} else {
					createCell.setCellValue(object.toString());
				}
				createCell.setCellStyle(headStyle);
				a++;
			}
			List<List<Object>> bodyList = content.getBodyList();
			
			int b = 1;
			for (List<Object> list : bodyList) {
				Row createRow = sheet.createRow(b);
				int c = 0;
				for (Object object : list) {
					Cell createCell = createRow.createCell(c);
					if (object instanceof Integer) {
						createCell.setCellValue((Integer) object);
					} else if (object instanceof Double) {
						createCell.setCellValue((Double) object);
					} else if (object instanceof Float) {
						createCell.setCellValue((Float) object);
					} else if (object instanceof Long) {
						createCell.setCellValue((Long) object);
					} else if (object instanceof Boolean) {
						createCell.setCellValue((Boolean) object);
					} else {
						createCell.setCellValue(object.toString());
					}
					c++;
				}
				b++;
			}
		workbook.write(out);
		workbook.close();
		byte[] bytes = out.toByteArray();
		out.close();
		return bytes;
	}
	
	public static byte[] exportSelfTest(Content content,String fileName) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		//创建一个excel表
		Workbook workbook = getWorkBook(fileName);
		CellStyle headStyle = deviceStatistics(workbook);
		
			Sheet sheet = workbook.createSheet(content.getBreedName());
			//第一，二列   列宽固定
			sheet.setColumnWidth(0,256*5+184);
			sheet.setColumnWidth(1,256*15+184);
			sheet.setColumnWidth(2,256*15+184);
			List<Object> headListNO = content.getHeadListNO();
			Row row0 = sheet.createRow(0);
			int a = 0;
			for (Object object : headListNO) {
				Cell createCell = row0.createCell(a);
				if (object instanceof Integer) {
					createCell.setCellValue((Integer) object);
				} else if (object instanceof Double) {
					createCell.setCellValue((Double) object);
				} else if (object instanceof Float) {
					createCell.setCellValue((Float) object);
				} else if (object instanceof Long) {
					createCell.setCellValue((Long) object);
				} else if (object instanceof Boolean) {
					createCell.setCellValue((Boolean) object);
				} else {
					createCell.setCellValue(object.toString());
				}
				createCell.setCellStyle(headStyle);
				a++;
			}
			
            List<List<Object>> bodyList = content.getBodyList();
			
			int b = 1;
			for (List<Object> list : bodyList) {
				Row createRow = sheet.createRow(b);
				int c = 0;
				for (Object object : list) {
					Cell createCell = createRow.createCell(c);
					if (object instanceof Integer) {
						createCell.setCellValue((Integer) object);
					} else if (object instanceof Double) {
						createCell.setCellValue((Double) object);
					} else if (object instanceof Float) {
						createCell.setCellValue((Float) object);
					} else if (object instanceof Long) {
						createCell.setCellValue((Long) object);
					} else if (object instanceof Boolean) {
						createCell.setCellValue((Boolean) object);
					} else {
						createCell.setCellValue(object.toString());
					}
					c++;
				}
				b++;
			}
		workbook.write(out);
		workbook.close();
		byte[] bytes = out.toByteArray();
		out.close();
		return bytes;
	}


	public static byte[] exportSelfSampleSet(Content content,String fileName) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		//创建一个excel表
		Workbook workbook = getWorkBook(fileName);

		Sheet sheet = workbook.createSheet(content.getBreedName());
		List<List<Object>> bodyList = content.getBodyList();

		int b = 0;
		for (List<Object> list : bodyList) {
			Row createRow = sheet.createRow(b);
			int c = 0;
			for (Object object : list) {
				Cell createCell = createRow.createCell(c);
				if (object instanceof Integer) {
					createCell.setCellValue((Integer) object);
				} else if (object instanceof Double) {
					createCell.setCellValue((Double) object);
				} else if (object instanceof Float) {
					createCell.setCellValue((Float) object);
				} else if (object instanceof Long) {
					createCell.setCellValue((Long) object);
				} else if (object instanceof Boolean) {
					createCell.setCellValue((Boolean) object);
				} else {
					createCell.setCellValue(object.toString());
				}
				c++;
			}
			b++;
		}
		workbook.write(out);
		workbook.close();
		byte[] bytes = out.toByteArray();
		out.close();
		return bytes;
	}

	private static CellStyle deviceStatistics(Workbook workbook) {
		//使用调色板来  对颜色进行定义
		HSSFPalette palette = ((HSSFWorkbook) workbook).getCustomPalette();
		palette.setColorAtIndex(IndexedColors.LIGHT_GREEN.index, (byte) 192, (byte) 192, (byte) 192);
		
		CellStyle headStyle = workbook.createCellStyle();
		headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headStyle.setBorderBottom(BorderStyle.THIN);	//设置底边框
		headStyle.setBorderTop(BorderStyle.THIN);   	//设置上边框
		headStyle.setBorderLeft(BorderStyle.THIN);     //设置左边框
		headStyle.setBorderRight(BorderStyle.THIN);    //设置右边框
		headStyle.setAlignment(HorizontalAlignment.CENTER);  //垂直居中
		headStyle.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.index); //设置前景填充为浅绿色
		headStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);  //设置前景填充为浅绿色
		
		Font headFont = workbook.createFont();//设置头部的字体
		headFont.setBold(true);	//设置为粗体
		headFont.setColor(IndexedColors.BLACK.index); //设置字体颜色为黑色
		headFont.setFontHeightInPoints((short)9);	//设置字体大小为9
		headFont.setFontName("微软雅黑");
		
		headStyle.setFont(headFont);
		return headStyle;
	}
	
	public static byte[] exportEquipmentStatistics(List<DevBind> DevBind,String fileName) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		//创建一个excel表
		Workbook workbook = getWorkBook(fileName);
		CellStyle headStyle = deviceStatistics(workbook);
		Sheet sheet = workbook.createSheet("设备统计");
		Row row0 = sheet.createRow(0);
		for(int a=0;a<9;a++) {
			sheet.setColumnWidth(a,256*15+184);
		}
		
		Cell cell0 = row0.createCell(0);
		cell0.setCellValue("设备SN");
		cell0.setCellStyle(headStyle);
			
		cell0 = row0.createCell(1);
		cell0.setCellValue("用户名");
		cell0.setCellStyle(headStyle);
		
		cell0 = row0.createCell(2);
		cell0.setCellValue("参比总数");
		cell0.setCellStyle(headStyle);
		
		cell0 = row0.createCell(3);
		cell0.setCellValue("自检总数");
		cell0.setCellStyle(headStyle);
		
		cell0= row0.createCell(4);
		cell0.setCellValue("参比失败次数");
		cell0.setCellStyle(headStyle);
		
		cell0= row0.createCell(5);
		cell0.setCellValue("自检失败次数");
		cell0.setCellStyle(headStyle);
		
		cell0 = row0.createCell(6);
		cell0.setCellValue("MGK扫描次数");
		cell0.setCellStyle(headStyle);
		
		cell0 = row0.createCell(7);
		cell0.setCellValue("快检光谱扫描数");
		cell0.setCellStyle(headStyle);
		
		cell0 = row0.createCell(8);
		cell0.setCellValue("样品异常数");
		cell0.setCellStyle(headStyle);
		
		int b = 1;
		for (DevBind devBind : DevBind) {
			Row createRow = sheet.createRow(b);
			
			Cell createCell = createRow.createCell(0);
			createCell.setCellValue(devBind.getDSn());
			
			createCell = createRow.createCell(1);
			createCell.setCellValue(devBind.getOwnerName());
			
			createCell = createRow.createCell(2);
			createCell.setCellValue(devBind.getReferenceSum());
			
			createCell = createRow.createCell(3);
			createCell.setCellValue(devBind.getSelfTestSum());
			
			createCell = createRow.createCell(4);
			createCell.setCellValue(devBind.getReferenceFail());
			
			createCell = createRow.createCell(5);
			createCell.setCellValue(devBind.getSelfTestFail());
			
			createCell = createRow.createCell(6);
			createCell.setCellValue(devBind.getMgkScanningSum());
			
			createCell = createRow.createCell(7);
			createCell.setCellValue(devBind.getFastTestScanningSum());
			
			createCell = createRow.createCell(8);
			createCell.setCellValue(devBind.getAbnormalSamples());
			b++;
		}
		workbook.write(out);
		workbook.close();
		byte[] bytes = out.toByteArray();
		out.close();
		return bytes;
	}
}
