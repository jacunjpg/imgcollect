package monitor.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

public class ExportExcel {

	private HSSFWorkbook wb = null;

	private HSSFSheet sheet = null;
	private static int CELLHIGHT = 5000;

	/**
	 * @param wb
	 * @param sheet
	 */
	public ExportExcel(HSSFWorkbook wb, HSSFSheet sheet) {
		super();
		this.wb = wb;
		this.sheet = sheet;
	}

	/**
	 * @return the sheet
	 */
	public HSSFSheet getSheet() {
		return sheet;
	}

	/**
	 * @param sheet
	 *            the sheet to set
	 */
	public void setSheet(HSSFSheet sheet) {
		this.sheet = sheet;
	}

	/**
	 * @return the wb
	 */
	public HSSFWorkbook getWb() {
		return wb;
	}

	/**
	 * @param wb
	 *            the wb to set
	 */
	public void setWb(HSSFWorkbook wb) {
		this.wb = wb;
	}

	/**
	 * 创建通用EXCEL头部
	 * @param headString 头部显示的字符
	 * @param colSum 该报表的列数
	 */
	@SuppressWarnings("deprecation")
	public void createNormalHead(String headString, int colSum) {

		HSSFRow row = sheet.createRow(0);

		// 设置第一行
		HSSFCell cell = row.createCell(0);
		row.setHeight((short) 400);

		// 定义单元格为字符串类型
		cell.setCellType(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(new HSSFRichTextString(headString));

		// 指定合并区域
		sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) colSum));

		HSSFCellStyle cellStyle = wb.createCellStyle();

		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		cellStyle.setWrapText(true);// 指定单元格自动换行

		// 设置单元格字体
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 300);
		cellStyle.setFont(font);

		cell.setCellStyle(cellStyle);
	}

	/**
	 * 创建通用报表第二行
	 * @param params 统计条件数组
	 * @param colSum 需要合并到的列索引
	 */
	@SuppressWarnings("deprecation")
	public void createNormalTwoRow(Map<String, Object> params, int colSum) {
		//System.out.println(params.size());
		String timeStr = "时间：";
		HSSFRow row1 = sheet.createRow(1);
		row1.setHeight((short) 300);

		HSSFCell cell2 = row1.createCell(0);

		cell2.setCellType(HSSFCell.ENCODING_UTF_16);
		switch (params.size()) {
			case 0:
				Date date = new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				timeStr = "文件导出时间：" + format.format(date);
				break;
			case 1:
				if (params.get("startTime") != null && !params.get("startTime").equals("")) {
					timeStr += params.get("startTime") + "之后";
				}
				if (params.get("endTime") != null && !params.get("endTime").equals("")) {
					timeStr += params.get("endTime") + "之前";
				}
				break;
			case 2:
				timeStr += params.get("startTime") + " 至 " + params.get("endTime");
				break;
			default:
				break;
		}
		
		cell2.setCellValue(new HSSFRichTextString(timeStr));

		// 指定合并区域
		sheet.addMergedRegion(new Region(1, (short) 0, 1, (short) colSum));

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		cellStyle.setWrapText(true);// 指定单元格自动换行

		// 设置单元格字体
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 250);
		cellStyle.setFont(font);

		cell2.setCellStyle(cellStyle);

	}
	/**
	 * 创建通用EXCEL头部
	 * @param headString 头部显示的字符
	 * @param colSum 该报表的列数
	 */
	@SuppressWarnings("deprecation")
	public void createNormalRowN(String title,int rn, int colSum) {

		HSSFRow row = sheet.createRow(rn);

		// 设置第一行
		HSSFCell cell = row.createCell(0);
		row.setHeight((short) 400);

		// 定义单元格为字符串类型
		cell.setCellType(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(new HSSFRichTextString(title));
		System.out.println(colSum+"====");
		// 指定合并区域
		sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) colSum));

		HSSFCellStyle cellStyle = wb.createCellStyle();

		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		cellStyle.setWrapText(true);// 指定单元格自动换行

		// 设置单元格字体
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 300);
		cellStyle.setFont(font);

		cell.setCellStyle(cellStyle);
	}
	/**
	 * 设置报表标题
	 * 
	 * @param columHeader
	 *            标题字符串数组
	 */
	public void createColumHeader(String[] columHeader) {

		// 设置列头
		HSSFRow row2 = sheet.createRow(2);

		// 指定行高
		row2.setHeight((short) 600);

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		cellStyle.setWrapText(true);// 指定单元格自动换行

		// 单元格字体
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 250);
		cellStyle.setFont(font);

		/*
		 * cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单无格的边框为粗体
		 * cellStyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．
		 * cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		 * cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		 * cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		 * cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
		 * cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		 * cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
		 */

		// 设置单元格背景色
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		HSSFCell cell3 = null;

		for (int i = 0; i < columHeader.length; i++) {
			cell3 = row2.createCell(i);
			cell3.setCellType(HSSFCell.ENCODING_UTF_16);
			cell3.setCellStyle(cellStyle);
			cell3.setCellValue(new HSSFRichTextString(columHeader[i]));
		}

	}

	/**
	 * 创建内容单元格
	 * 
	 * @param wb HSSFWorkbook
	 * @param row HSSFRow
	 * @param col short型的列索引
	 * @param align 对齐方式
	 * @param val 列值
	 */
	public void cteateCell(HSSFWorkbook wb, HSSFRow row, int col, short align,
			String val) {
		HSSFCell cell = row.createCell(col);
		cell.setCellType(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle = wb.createCellStyle();
		cellstyle.setAlignment(align);
		cellstyle.setWrapText(true);
		cell.setCellStyle(cellstyle);
	}

	/**
	 * 创建合计行
	 * @param colSum 需要合并到的列索引
	 * @param cellValue
	 */
	@SuppressWarnings("deprecation")
	public void createLastSumRow(int colSum, String[] cellValue) {

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		cellStyle.setWrapText(true);// 指定单元格自动换行

		// 单元格字体
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 250);
		cellStyle.setFont(font);

		HSSFRow lastRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));
		HSSFCell sumCell = lastRow.createCell(0);

		sumCell.setCellValue(new HSSFRichTextString("合计"));
		sumCell.setCellStyle(cellStyle);
		sheet.addMergedRegion(new Region(sheet.getLastRowNum(), (short) 0,
				sheet.getLastRowNum(), (short) colSum));// 指定合并区域

		for (int i = 2; i < (cellValue.length + 2); i++) {
			sumCell = lastRow.createCell(i);
			sumCell.setCellStyle(cellStyle);
			sumCell.setCellValue(new HSSFRichTextString(cellValue[i - 2]));

		}

	}

	/**
	 * 输入EXCEL文件
	 * @param fileName 文件名
	 */
	public void outputExcel(String fileName) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(fileName));
			wb.write(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成Excel文件
	 * @param fialList   List<String>        表头
	 * @param path       String              文件生成路径
	 * @param excelName  String              Excel表格名称
	 * @param cellHight  int                 Excel表格列宽
	 * @param params     String[]            所统计任务的发布时间  
	 * @param list       List<List<String>>  Excel表格内容
	 * */
	public void createExcel(List<String> fialList, String path,
			String excelName, Map<String, Object> params, List<List<Object>> list) {
		// ExportExcel exportExcel = new ExportExcel(wb, sheet);
		// 计算该报表的列数
		int number = fialList.size();

		// 给工作表列定义列宽
		for (int i = 0; i < number; i++) {
			sheet.setColumnWidth(i, CELLHIGHT);
		}

		// 创建单元格样式
		HSSFCellStyle cellStyle = wb.createCellStyle();

		// 指定单元格居中对齐
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		// 指定单元格垂直居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		// 指定当单元格内容显示不下时自动换行
		cellStyle.setWrapText(false);

		// 设置单元格字体
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 200);
		cellStyle.setFont(font);
		// 创建报表头部
		this.createNormalHead(excelName, number - 1);

		// 设置第二行
		
		this.createNormalTwoRow(params, number - 1);
		String[] cellValue = new String[number];
		for (int i = 0; i < number; i++) {
			cellValue[i] = fialList.get(i);
		}
		this.createColumHeader(cellValue);

		// 循环创建中间的单元格的各项的值
		for (int i = 3; i < 3 + list.size(); i++) {
			HSSFRow row = sheet.createRow((short) i);
			for (int j = 0; j < list.get(i - 3).size(); j++) {
				this.cteateCell(wb, row, (short) j,
								HSSFCellStyle.ALIGN_CENTER_SELECTION, String
										.valueOf(list.get(i - 3).get(j)));
			}

		}

		this.outputExcel(path);
	}
	
	/**
	 * 导出表格
	 * @param fialList Excel表格头信息列表
	 * @param list 内容列表
	 * @param temp 生成的表格第二行时间信息(文件的导出时间或者统计的时间段)
	 * @param fileName Excel文件名
	 * @param tableName Excel表格的名称
	 * */
	public static void exportExcel(List<String> fialList, List<List<Object>> list, Map<String, Object> temp, String fileName, String tableName) {
		HSSFWorkbook wb = new HSSFWorkbook();

		HSSFSheet sheet = wb.createSheet();

		ExportExcel exportExcel = new ExportExcel(wb, sheet);
		//tableName = fileName.substring(fileName.lastIndexOf("\\"), fileName.lastIndexOf('.'));
		exportExcel.createExcel(fialList, fileName, tableName,
				temp, list);
	}
	
	
	//
	
	/**
	 * 复杂导出表格
	 * @param head Excel表格头信息列表
	 * @param names 表格头
	 * @param values 表格值
	 * @param fileName Excel文件名
	 * @param tableName Excel表格的名称
	 * */
	public static void exportExcelFZ(List<String> head,List<String> body, Map<String, Object> names, Map<String, Object> values, String fileName) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		ExportExcel exportExcel = new ExportExcel(wb, sheet);
		exportExcel.createExcelFZ(head,body,names,values,fileName);
	}
	/**
	 * 生成Excel文件
	 * @param fialList   List<String>        表头
	 * @param path       String              文件生成路径
	 * @param excelName  String              Excel表格名称
	 * @param cellHight  int                 Excel表格列宽
	 * @param params     String[]            所统计任务的发布时间  
	 * @param list       List<List<String>>  Excel表格内容
	 * */
	@SuppressWarnings("unchecked")
	public void createExcelFZ(List<String> head,List<String> body, Map<String, Object> names, Map<String, Object> values, String fileName) {
		// ExportExcel exportExcel = new ExportExcel(wb, sheet);
		// 总结果报表的列数
		int number = body.size();
		// 给工作表列定义列宽
		for (int i = 0; i < number; i++) {
			sheet.setColumnWidth(i, CELLHIGHT);
		}
		// 创建单元格样式
		HSSFCellStyle cellStyle = wb.createCellStyle();
		// 指定单元格居中对齐
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 指定单元格垂直居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 指定当单元格内容显示不下时自动换行
		cellStyle.setWrapText(true);
		// 设置单元格字体
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 200);
		cellStyle.setFont(font);
		// 创建报表头部
		this.createNormalHead((String)names.get("head"), head.size() - 1);
		names.remove("head");
		// 循环创建中间的单元格的各项的值
		List<List<Object>> totalList = (List<List<Object>>)values.get("head");
		int rowNum = totalList.size()+4;
		for (int i = 3; i < 3 + totalList.size(); i++) {
			HSSFRow row = sheet.createRow((short) i);
			for (int j = 0; j < totalList.get(i - 3).size(); j++) {
				this.cteateCell(wb, row, (short) j,HSSFCellStyle.ALIGN_CENTER_SELECTION, String.valueOf(totalList.get(i - 3).get(j)));
			}
		}//end for
		values.remove("head");
		//循环下面的子表格
		Object ns[] = names.keySet().toArray();
		System.out.println(rowNum+"===");
		for(int i = 0; i < names.size(); i++) {
			// 设置第N行
			this.createNormalRowN((String)names.get(ns[i]),rowNum,number-1);
			String[] cellValue = new String[number];
			for (int k = 0; k < number; k++) {
				cellValue[k] = body.get(k);
			}
			this.createColumHeader(cellValue);
			List<List<Object>> list = (List<List<Object>>)values.get(ns[i]);
			// 循环创建中间的单元格的各项的值
			int design = 0;
			System.out.println(rowNum+"==="+list.size());
			for (int m = rowNum+2; m < (rowNum+2+list.size()); m++) {
				System.out.println(rowNum+"==="+m);
				HSSFRow row = sheet.createRow((short) m);
				for (int j = 0; j < list.get(design).size(); j++) {
					System.out.println(rowNum+"==="+m+"==="+list.get(design).get(j));
					this.cteateCell(wb, row, (short) j,HSSFCellStyle.ALIGN_CENTER_SELECTION, String.valueOf(list.get(design).get(j)));
				}
				design++;
			}
		}

		this.outputExcel(fileName);
	}
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		List<String> body = new ArrayList<String>();
		body.add("编号");
		body.add("网评员姓名");
		body.add("网络昵称");
		body.add("任务类别");
		body.add("内容");
		body.add("点赞数量");
		body.add("转发数量");
		body.add("评论数量");
		body.add("执行时间");
		List<String> head = new ArrayList<String>();
		head.add("编号");
		head.add("网评员姓名");
		head.add("网络昵称");
		head.add("任务类别");
		head.add("内容");
		head.add("点赞数量");
		head.add("转发数量");
		head.add("评论数量");
		head.add("执行时间");
		
		//exportExcelFZ(head,List<String> body, Map<String, Object> names, Map<String, Object> values, String fileName)
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();

		ExportExcel exportExcel = new ExportExcel(wb, sheet);
		

		// 创建列标头LIST
		List<Object> fialList = new ArrayList<Object>();

		fialList.add("编号");
		fialList.add("网评员姓名");
		fialList.add("部门");
		fialList.add("任务名称");
		fialList.add("任务发布时间");
		fialList.add("执行条数");
		fialList.add("任务得分");
		fialList.add("任务描述");
//		Map<String, Object> params = new HashMap<String, Object>();
		List<List<Object>> list = new ArrayList<List<Object>>();
		list.add(fialList);
		list.add(fialList);
//		exportExcel.createExcel(head, "d:\\网评员任务考核结果.xls", "网评员任务考核结果", params, list);
		
		Map<String, Object> names = new HashMap<String,Object>();
		names.put("head", "测试庄园");
		names.put("11", "测试庄园11");
		Map<String, Object> values = new HashMap<String,Object>();
		values.put("head", list);
		values.put("11", list);
		exportExcel.createExcelFZ(head,body,names,values,"d:\\网评员任务考核结果0000.xls");
	}
}