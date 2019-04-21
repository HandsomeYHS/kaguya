package com.akanemurakawa.kaguya.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.akanemurakawa.kaguya.model.entity.data.Excel;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel导入导出工具
 */
@Slf4j
public class ExcelUtils {

    /**
     * 2003- 版本的excel
     */
	private final static String excel2003L = ".xls";

    /**
     * 2007+ 版本的excel
     */
	private final static String excel2007U = ".xlsx";

    /**
     * 表头样式
     */
	private static XSSFCellStyle headerStyle;

    /**
     * 表体样式
     */
	private static XSSFCellStyle bodyStyle;

	/********************************************* Excel导入*******************************************************************************************/

	/**
	 * Excel导入
	 * Tip:注意excel导入的时候多余的空sheet要去除，否则报空指针错误。
	 * @param inputStream 文件输入流，要解析的Excel文件输入流
	 * @param fileName 文件名称
	 * @param startRow 从第几行开始读取
	 */
	public static List<List<Object>> importExcel(InputStream inputStream, String fileName, int startRow){
		List<List<Object>> result = null;
		try {
			// 1. 创建Excel工作薄，定义excel对象变量
			Workbook workbook = null;
			FormulaEvaluator formulaEvaluator = null;
			// 2. 描述：根据文件后缀，自适应上传文件的版本
			String suffix = fileName.substring(fileName.lastIndexOf("."));
			if (excel2003L.equals(suffix)) {
				workbook = new HSSFWorkbook(inputStream); // 2003-
				formulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
			} else if (excel2007U.equals(suffix)) {
				workbook = new XSSFWorkbook(inputStream); // 2007+
				formulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
			} else {
				throw new Exception("解析的文件格式有误！");
			}
	
			// 3. 获取工作表，excel分为若干个表，sheet
			Sheet sheet = null;
			result = new ArrayList<List<Object>>();
			for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
				sheet = workbook.getSheetAt(sheetNum);
				if (null == sheet) {
					continue;
				}
				// 4.获取表格中最后一行的行号
				int lastRowNum = sheet.getLastRowNum();
				if (lastRowNum <= startRow) {
					return null;
				}
				// 定义行遍历和单元格变量
				Row row = null;
				Cell cell = null;
				// 5. 循环读取
				for (int rowNum = startRow; rowNum <= lastRowNum; rowNum++) {
					row = sheet.getRow(rowNum);
					// 获取当前行的第一列和最后一列的标记
					short firstCellNum = row.getFirstCellNum();
					short lastCellNum = row.getLastCellNum();
					if (firstCellNum <= lastCellNum && lastCellNum != 0) {
						// 遍历所有的列
						List<Object> list = new ArrayList<Object>();
						for (int cellNum = firstCellNum; cellNum <= lastCellNum; cellNum++) {
							cell = row.getCell(cellNum);
							// 判断单元格是否有数据
							if (null == cell) {
								list.add("");
							} else {
								list.add(parseCell(cell, formulaEvaluator));
							}
						}
						result.add(list);
					}
				}
			}
		}catch (Exception e) {
			log.error("Excel导入失败，error:{}", e);
		}
		return result;
	}

	/**
	 * 解析单元格数据类型
	 */
	public static Object parseCell(Cell cell, FormulaEvaluator formulaEvaluator) {
		Object value = null;
		// 格式化字符类型的数字
		DecimalFormat df2 = new DecimalFormat("0.00"); // 格式化数字
		SimpleDateFormat sdf1 = new SimpleDateFormat("h:mm"); // 日期格式化
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyy-MM-dd"); // 日期格式化

		// 判断单元格的类型
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			// 字符串数据类型
			value = cell.getRichStringCellValue().getString();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			// 数学类型，包含日期，时间，数字
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				if (cell.getCellType() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
					// 时间 h:mm
					value = sdf1.format(cell.getDateCellValue());
				} else {
					// 日期 yy-MM-dd
					value = sdf2.format(cell.getDateCellValue());
				}
			} else {
				// 数字0
				double temp = cell.getNumericCellValue();
				/*
				 * 数学格式化工具 DecimalFormat 类主要靠 # 和 0 两种占位符号来指定数字长度。 0 表示如果位数不足则以 0 填充，#
				 * 表示只要有可能就把数字拉上这个位置
				 */
				DecimalFormat format = new DecimalFormat("#");
				// 查看单元格中的具体样式类型
				String formatStr = cell.getCellStyle().getDataFormatString();
				if ("General".equals(formatStr)) {
					// 定义格式化正则，使用具体有效数据，且只保留有效数据
					format.applyPattern("#0.0");
				}
				value = format.format(temp);
			}
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			double temp = formulaEvaluator.evaluate(cell).getNumberValue();
			DecimalFormat format = new DecimalFormat("#0.00");
			value = format.format(temp);
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			value = cell.getBooleanCellValue();
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			// 空数据， 标准数据
			value = "";
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			value = "非法字符";
			break;
		default:
			value = "未知类型";
			break;
		}
		return value;
	}

	/********************************************* Excel导出(2007+) *******************************************************************************************/

	/**
	 * exportExcel(2007+)
	 * 
	 * @param clazz     数据源model类型
	 * @param dataList  导出的数据
	 * @param map       标题列行数以及标题内容和样式
	 * @param sheetName 工作簿名称
	 */
	public static void exportExcel(OutputStream outputStream, Class clazz, List dataList, Map<Integer, List<Excel>> map, String sheetName) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException, IOException {
		// 1.创建一个内存Excel对象
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 2.创建一个表格
		XSSFSheet sheet = workbook.createSheet(sheetName);
		// 3.设置字体样式
		createFont(workbook);
		// 4.创建表头和表头内容
		createHeader(sheet, map);
		// 5.创建表体内容
		createRows(sheet, clazz, dataList, map);
		// 6.写入
		workbook.write(outputStream);
	}

	/**
	 * fontStyle(2007+) 创建两种样式
	 * 
	 * @param workbook
	 */
	public static void createFont(XSSFWorkbook workbook) {
		// 1.表头样式：水平居中 - 黑色  - 黑体 - 12号
		headerStyle = workbook.createCellStyle();
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
		// 设置字体
		XSSFFont headerFont = workbook.createFont();
		// headerFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD); // 加粗
		headerFont.setColor(HSSFColor.BLACK.index);
		headerFont.setFontName("黑体");
		headerFont.setFontHeightInPoints((short) 12); // 设置字体大小
		headerStyle.setFont(headerFont);

		// 2.表体样式：水平居中 - 黑色 - 宋体 - 10号
		bodyStyle = workbook.createCellStyle();
		bodyStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
		// 设置字体
		XSSFFont bodyFont = workbook.createFont();
		bodyFont.setColor(HSSFColor.BLACK.index);
		bodyFont.setFontName("宋体");
		bodyFont.setFontHeightInPoints((short) 10); // 设置字体大小
		bodyStyle.setFont(bodyFont);
	}

	/**
	 * createHeader (2007+)
	 * @param sheet
	 * @param map   标题列行数以及标题内容和样式
	 */
	public static final void createHeader(XSSFSheet sheet, Map<Integer, List<Excel>> map) {
		int startIndex = 0; // cell起始位置
		int endIndex = 0; // cell终止位置
		for (Map.Entry<Integer, List<Excel>> entry : map.entrySet()) {
			XSSFRow headerRow = sheet.createRow(entry.getKey()); // 创建行
			List<Excel> excels = entry.getValue();
			for (int x = 0; x < excels.size(); x++) {
				// 合并表格 cols:合并单元格数
				if (excels.get(x).getCols() > 1) {
					if (0 == x) {
						endIndex += excels.get(x).getCols() - 1;
						// firstRow, lastRow, firstCol, lastCol
						CellRangeAddress range = new CellRangeAddress(0, 0, startIndex, endIndex);
						sheet.addMergedRegion(range);
						startIndex += excels.get(x).getCols();
					} else {
						endIndex += excels.get(x).getCols();
						CellRangeAddress range = new CellRangeAddress(0, 0, startIndex, endIndex);
						sheet.addMergedRegion(range);
						startIndex += excels.get(x).getCols();
					}
					// 创建表头
					XSSFCell headerCell = headerRow.createCell(startIndex - excels.get(x).getCols());
					// 创建表头内容
					headerCell.setCellValue(excels.get(x).getHeadTextName());
					// 设置样式
					if (excels.get(x).getCellStyle() != null) {
						headerCell.setCellStyle(excels.get(x).getCellStyle());
					}
					headerCell.setCellStyle(headerStyle);
				} else {
					// 创建表头
					XSSFCell headerCell = headerRow.createCell(x);
					// 创建表头内容
					headerCell.setCellValue(excels.get(x).getHeadTextName());
					// 设置样式
					if (excels.get(x).getCellStyle() != null) {
						headerCell.setCellStyle(excels.get(x).getCellStyle());
					}
					headerCell.setCellStyle(headerStyle);
				}
			}
		}
	}

	/**
	 * createRows(2007+)
	 * 
	 * @param sheet
	 * @param clazz    数据源model类型
	 * @param dataList 导出的数据
	 * @param map      标题列行数以及标题内容和样式
	 */
	public static void createRows(XSSFSheet sheet, Class clazz, List dataList, Map<Integer, List<Excel>> map)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		int rowIndex = map.size();
		int maxKey = 0;
		List<Excel> emsList = new ArrayList<Excel>();
		for (Map.Entry<Integer, List<Excel>> entry : map.entrySet()) {
			if (entry.getKey() > maxKey) {
				maxKey = entry.getKey();
			}
		}
		emsList = map.get(maxKey);
		List<Integer> widthsList = new ArrayList<Integer>(emsList.size()); // 列宽

		// 创建表体以及给表体内容赋值
		for (Object object : dataList) {
			XSSFRow bodyRow = sheet.createRow(rowIndex);
			for (int cell = 0; cell < emsList.size(); cell++) {
				Excel em = (Excel) emsList.get(cell);
				// Java反射机制，PropertyName为需要执行的方法名称
				PropertyDescriptor propertyDescriptor = new PropertyDescriptor(em.getPropertyName(), clazz);
				Method getMethod = propertyDescriptor.getReadMethod();
				// object为执行方法参数列表
				Object data = getMethod.invoke(object);

				String value = "";
				// 类型处理
				if (data != null) {
					if (data instanceof Date) {
						value = DateUtils.formatDate((Date) data, "yyyy-MM-dd");
					} else if (data instanceof BigDecimal) {
						DecimalFormat format = new DecimalFormat("#0,00");
						value = format.format(data);
					} else if (data instanceof Integer) {
						// TODO
					} else {
						value = data.toString();
					}
				}

				XSSFCell bodyCell = bodyRow.createCell(cell);
				// 给表格赋值
				bodyCell.setCellValue(value);
				bodyCell.setCellType(XSSFCell.CELL_TYPE_STRING);
				bodyCell.setCellStyle(bodyStyle); // 设置表体样式

				// 最大列宽
				int width = value.getBytes().length * 300;
				// 未设置
				if (widthsList.size() <= cell) {
					widthsList.add(width);
					continue;
				}
				// 比原来大，更新数据
				if (width > widthsList.get(cell)) {
					widthsList.set(cell, width);
				}
			}
			rowIndex++;
		}
		// 设置列宽
		for (int index = 0; index < widthsList.size(); index++) {
			Integer width = widthsList.get(index);
			width = width < 2500 ? 2500 : width + 300;
			width = width > 10000 ? 10000 + 300 : width + 300;
			sheet.setColumnWidth(index, width);
		}
	}
}
