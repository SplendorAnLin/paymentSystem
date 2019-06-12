package com.yl.recon.core.handler.impl;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.recon.core.handler.AbstractFileHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Excle文件处理
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年9月11日
 */
public class ExcleFileHandlerImpl extends AbstractFileHandler {

	private static Logger logger = LoggerFactory.getLogger(ExcleFileHandlerImpl.class);

	private final static String xls = "xls";
	private final static String xlsx = "xlsx";

	@Override
	public List <?> fileHandle() {
		try {
			String[] totalTitles = null;
			String[] content = null;
			boolean headFlag = true;
			boolean Flag = true;
			boolean tow = true;
			final String totalTitlesKey = "counts";
			Map <String, String> params = new HashMap <>();
			//检查文件
			checkFile(file);
			//获得Workbook工作薄对象
			Workbook workbook = getWorkBook(file);
			//创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
			List <String> lists = new ArrayList <>();
			if (workbook != null) {
				for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
					//获得当前sheet工作表
					Sheet sheet = workbook.getSheetAt(sheetNum);
					if (sheet == null) {
						continue;
					}
					//获得当前sheet的开始行
					int firstRowNum = sheet.getFirstRowNum();
					//获得当前sheet的结束行
					int lastRowNum = sheet.getLastRowNum();
					//循环所有行
					for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++) {
						//获得当前行
						Row row = sheet.getRow(rowNum);
						if (row == null) {
							continue;
						}
						//获得当前行的开始列
						int firstCellNum = row.getFirstCellNum();
						//获得当前行的列数
						int lastCellNum = row.getPhysicalNumberOfCells();
						String[] cells = new String[row.getPhysicalNumberOfCells()];
						//循环当前行
						for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
							Cell cell = row.getCell(cellNum);
							cells[cellNum] = getCellValue(cell);
							if (totalTitlesKey.equals(getCellValue(cell))) {
								headFlag = true;
							}
							content = cells;
							if (headFlag) {
								totalTitles = cells;
								headFlag = false;
								tow = false;
								continue;
							}
						}
						if (totalTitles != null && tow) {
							params = new HashMap <>();
							for (int i = 0; i < totalTitles.length; i++) {
								params.put(totalTitles[i], content[i]);
							}
						}
						if (!Flag && tow) {
							lists.add(JsonUtils.toJsonString(params));
						}
						tow = true;
						Flag = false;
					}
				}
			}
			return lists;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return null;
	}

	public void checkFile(File file) throws IOException {
		//判断文件是否存在
		if (null == file) {
			throw new FileNotFoundException("文件不存在！");
		}
		//获得文件名
		String fileName = file.getName();
		//判断文件是否是excel文件
		if (!fileName.endsWith(xls) && !fileName.endsWith(xlsx)) {
			throw new IOException(fileName + "不是excel文件");
		}
	}

	public Workbook getWorkBook(File file) {
		//获得文件名
		String fileName = file.getName();
		//创建Workbook工作薄对象，表示整个excel
		Workbook workbook = null;
		try {
			//获取excel文件的io流
			InputStream is = new FileInputStream(file);
			//根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
			if (fileName.endsWith(xls)) {
				//2003
				workbook = new HSSFWorkbook(is);
			} else if (fileName.endsWith(xlsx)) {
				//2007
				workbook = new XSSFWorkbook(is);
			}
		} catch (IOException e) {

		}
		return workbook;
	}

	public String getCellValue(Cell cell) {
		String cellValue = "";
		if (cell == null) {
			return cellValue;
		}

		//把数字当成String来读，避免出现1读成1.0的情况
		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			cell.setCellType(Cell.CELL_TYPE_STRING);
		}

		//判断数据的类型
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC: //数字
				cellValue = String.valueOf(cell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_STRING: //字符串
				cellValue = String.valueOf(cell.getStringCellValue());
				break;
			case Cell.CELL_TYPE_BOOLEAN: //Boolean
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA: //公式
				cellValue = String.valueOf(cell.getCellFormula());
				break;
			case Cell.CELL_TYPE_BLANK: //空值
				cellValue = "";
				break;
			case Cell.CELL_TYPE_ERROR: //故障
				cellValue = "非法字符";
				break;
			default:
				cellValue = "未知类型";
				break;
		}
		return cellValue;
	}

	@Override
	public List <?> fileHandle(String separator) {
		this.separator = separator;
		return fileHandle();
	}

}