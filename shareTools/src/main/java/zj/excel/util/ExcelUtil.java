package zj.excel.util;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import zj.check.util.CheckUtil;
import zj.date.util.DateUtil;
import zj.excel.bean.DatasKeySheets;
import zj.excel.bean.Excel;
import zj.excel.bean.ExcelI;
import zj.excel.bean.FormulaTemplate;
import zj.excel.bean.SheetDatas;
import zj.java.util.JavaUtil;

/**
 * 概况 ：Excel/xls/xlsx<br>
 * 
 * @version 1.00 （2011.12.02）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>} <br>
 */
public final class ExcelUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ExcelUtil.class.getName());

	private ExcelUtil() {
	}

	/**
	 * Excel写入
	 * 
	 * @see zj.excel.util.ExcelRWUtil#writeExcel(List, Excel)
	 * @param sheetDatas
	 *            所有行列数据对象
	 * @param excel
	 *            excel对象
	 * @throws Exception
	 */
	@Deprecated
	public static final void writeExcel(final List<SheetDatas> sheetDatas, final Excel excel) throws Exception {
		writeExcel(sheetDatas, excel, null);
	}

	/**
	 * Excel写入
	 * 
	 * @see zj.excel.util.ExcelRWUtil#writeExcel(List, Excel, ExcelI)
	 * @param sheetDatas
	 *            数据集合
	 * @param excel
	 *            对象
	 * @param excelI
	 *            接口
	 * @throws Exception
	 */
	@Deprecated
	public static final void writeExcel(final List<SheetDatas> sheetDatas, Excel excel, final ExcelI excelI) throws Exception {
		ExcelRWUtil.writeExcel(sheetDatas, excel, excelI);
	}

	// /**
	// * 合并单元格
	// *
	// * @param sheet
	// * @param datasLst
	// */
	// private static void mergedRegion(Sheet sheet, List<List<SheetData>> datasLst) {
	// // 合并单元格
	// // org.apache.poi.ss.util.CellRangeAddress
	// // // 合并第一行到第二行的第一列到第二列
	// // sheet.addMergedRegion(new CellRangeAddress(0, (short) 1, 0, (short) 1));
	// // // 合并第三行到第五行的第一列到第二列
	// // sheet.addMergedRegion(new CellRangeAddress(2, (short) 4, 0, (short) 1));
	// // HSSFRow row4 = sheet.createRow(4);
	// //
	// // // 合并第三行到第五行的第一列
	// // sheet.addMergedRegion(new Region(2, (short) 0, 4, (short) 0));
	// //
	// // // 合并第三行到第五行的第二列
	// // sheet.addMergedRegion(new Region(2, (short) 1, 4, (short) 1));
	// //
	// // // 合并第三行的第三列到第AA指定的列
	// // int aa = 2 * fialList.size() + 1;
	// // sheet.addMergedRegion(new Region(2, (short) 2, 2, (short) aa));
	// //
	// // int start = aa + 1;
	// //
	// // sheet.addMergedRegion(new Region(2, (short) start, 2,
	// // (short) (number - 1)));
	// //
	// // // 循环合并第四行的行，并且是每2列合并成一列
	// // for (int i = 2; i < number; i = i + 2) {
	// // sheet.addMergedRegion(new Region(3, (short) i, 3, (short) (i + 1)));
	// //
	// // }
	// }

	/**
	 * 默认行样式
	 * 
	 * @param cellStyle
	 */
	public static final void rowExcel(final Row row, final int rowIndex) {
		// row.setHeight((short) 1000);
	}

	/**
	 * 默认单元格样式
	 * 
	 * @param cellStyle
	 */
	public static final void cellStyle(final CellStyle cellStyle) {
		// 设置边框样式
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		// 设置边框颜色
		cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
		// // 设置背景色
		// cellStyle.setFillForegroundColor(HSSFColor.BLUE.index);
		// cellStyle.setFillBackgroundColor(HSSFColor.RED.index);
		// // 上面程序只指定了「ForegroundColor」，填充模式是「SOLID_FOREGROUND」，因此顔色应该是全部充满整个单元格的
		// cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		// // 单元格内容
		// cellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyle.setAlignment(CellStyle.ALIGN_LEFT); // 指定单元格左对齐
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		// cellStyle.setWrapText(true);// 指定单元格自动换行
	}

	/**
	 * 默认字体样式
	 * 
	 * @param cellStyle
	 */
	public static final void font(final Font font) {
		// 单元格字体
		// font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		// 设置字体高度
		// font.setFontHeight((short) 550);
	}

	// #####################################################################
	/**
	 * 读取excel
	 * 
	 * @param excel
	 *            excel对象
	 * @return 所有行列数据对象
	 * @throws Exception
	 */
	@Deprecated
	public static final DatasKeySheets readExcel(final Excel excel) throws Exception {
		return readExcel(excel, null);
	}

	/**
	 * 读取excel
	 * 
	 * @param excel
	 *            excel对象
	 * @param excelI
	 *            excelI接口
	 * @return 所有行列数据对象
	 * @throws Exception
	 */
	@Deprecated
	public static final DatasKeySheets readExcel(final Excel excel, final ExcelI excelI) throws Exception {
		return ExcelRWUtil.readExcel(excel, excelI);
	}

	/**
	 * 合并单元格
	 * 
	 * @param sheet
	 * @param firstR
	 *            开始行
	 * @param lastR
	 *            结束行
	 * @param firstC
	 *            开始列
	 * @param lastC
	 *            结束列
	 */
	public static final void mergerRegion(final Sheet sheet, final int firstR, final int lastR, final int firstC, final int lastC) {
		sheet.addMergedRegion(new CellRangeAddress(firstR, lastR, firstC, lastC));
	}

	/**
	 * 复制原有sheet的合并单元格到新创建的sheet
	 * 
	 * @param sheetCreat
	 *            新创建sheet
	 * @param sheet
	 *            原有的sheet
	 */
	public static final void mergerRegion(final Sheet fromSheet, final Sheet toSheet) {
		int sheetMergerCount = fromSheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergerCount; i++) {
			CellRangeAddress mergedRegionAt = fromSheet.getMergedRegion(i);
			toSheet.addMergedRegion(mergedRegionAt);
		}
	}

	/**
	 * 复制一个单元格样式到目的单元格样式
	 * 
	 * @param srcStyle
	 *            源样式
	 * @param destStyle
	 *            目标样式
	 */
	public static final void copyCellStyle(final CellStyle srcStyle, final CellStyle destStyle) {
		destStyle.setAlignment(srcStyle.getAlignment());
		// 边框和边框颜色
		destStyle.setBorderBottom(srcStyle.getBorderBottom());
		destStyle.setBorderLeft(srcStyle.getBorderLeft());
		destStyle.setBorderRight(srcStyle.getBorderRight());
		destStyle.setBorderTop(srcStyle.getBorderTop());
		destStyle.setTopBorderColor(srcStyle.getTopBorderColor());
		destStyle.setBottomBorderColor(srcStyle.getBottomBorderColor());
		destStyle.setRightBorderColor(srcStyle.getRightBorderColor());
		destStyle.setLeftBorderColor(srcStyle.getLeftBorderColor());

		// 背景和前景
		destStyle.setFillBackgroundColor(srcStyle.getFillBackgroundColor());
		destStyle.setFillForegroundColor(srcStyle.getFillForegroundColor());

		destStyle.setDataFormat(srcStyle.getDataFormat());
		destStyle.setFillPattern(srcStyle.getFillPattern());
		// destStyle.setFont(srcStyle.getFont(null));
		destStyle.setHidden(srcStyle.getHidden());
		destStyle.setIndention(srcStyle.getIndention());// 首行缩进
		destStyle.setLocked(srcStyle.getLocked());
		destStyle.setRotation(srcStyle.getRotation());// 旋转
		destStyle.setVerticalAlignment(srcStyle.getVerticalAlignment());
		destStyle.setWrapText(srcStyle.getWrapText());
	}

	/**
	 * 拷贝单元格
	 * 
	 * @param srcCell
	 *            源单元格
	 * @param destCell
	 *            目标单元格
	 */
	public static final void copyCell(final Cell srcCell, final Cell destCell) {
		copyCell(srcCell, destCell, true, true, true);
	}

	/**
	 * 
	 * @param srcCell
	 *            源单元格
	 * @param destCell
	 *            目标单元格
	 * @param copyStyle
	 *            是否拷贝样式
	 * @param copyValue
	 *            是否拷贝内容
	 * @param copyCellComment
	 *            是否拷贝评论
	 */
	public static final void copyCell(final Cell srcCell, final Cell destCell, final boolean copyValue, final boolean copyStyle, final boolean copyCellComment) {
		// 样式
		if (copyStyle) {
			destCell.setCellStyle(srcCell.getCellStyle());
		}
		// 评论
		if (copyCellComment) {
			if (srcCell.getCellComment() != null) {
				destCell.setCellComment(srcCell.getCellComment());
			}
		}
		try {
			destCell.setCellType(srcCell.getCellType());
		} catch (Exception e) {
			// 复制格式
			logger.error("设置excel类型出错:[" + srcCell.getRowIndex() + "][" + srcCell.getColumnIndex() + "]", e);
		}
		// 不同数据类型处理
		if (copyValue) {
			switch (srcCell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				destCell.setCellValue(srcCell.getRichStringCellValue());
				break;
			case Cell.CELL_TYPE_NUMERIC:
				destCell.setCellValue(srcCell.getNumericCellValue());
				if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(srcCell)) {
					destCell.setCellValue(srcCell.getDateCellValue());
				} else {
					destCell.setCellValue(srcCell.getNumericCellValue());
				}
				break;
			case Cell.CELL_TYPE_BLANK:
				destCell.setCellType(Cell.CELL_TYPE_BLANK);
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				destCell.setCellValue(srcCell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_ERROR:
				destCell.setCellErrorValue(srcCell.getErrorCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				destCell.setCellFormula(srcCell.getCellFormula());
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 格式化值
	 * 
	 * @param sheet
	 *            对象
	 * @param row
	 *            行对象
	 * @param cell
	 *            列对象
	 * @param rowIndex
	 *            行索引
	 * @param colIndex
	 *            列索引
	 * @param value
	 *            值
	 */
	public static final void setCellValue(final Sheet sheet, final Row row, final Cell cell, final int rowIndex, final int colIndex, final Object value) {
		setCellValue(sheet, row, cell, rowIndex, colIndex, value, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 格式化值
	 * 
	 * @param sheet
	 *            对象
	 * @param row
	 *            行对象
	 * @param cell
	 *            列对象
	 * @param rowIndex
	 *            行索引
	 * @param colIndex
	 *            列索引
	 * @param value
	 *            值
	 * @param partten
	 *            格式化日期:yyyy-MM-dd HH:mm:ss
	 */
	public static final void setCellValue(Sheet sheet, Row row, Cell cell, int rowIndex, int colIndex, Object value, String partten) {
		// String strValue = "";
		// if (value instanceof String) {
		// strValue = value == null ? "" : "" + value;
		// } else if (value instanceof Date) {
		// strValue = sdf.format((Date) value);
		// } else if (value instanceof Boolean) {
		// strValue = value == null ? "" : "" + value;
		// } else if (value instanceof Double) {
		// strValue = value == null ? "" : "" + value;
		// } else if (value instanceof Calendar) {
		// strValue = sdf.format(((Calendar) value).getTime());
		// } else if (value instanceof Float) {
		// strValue = value == null ? "" : "" + value;
		// } else {
		// strValue = value == null ? "" : "" + value;
		// }
		try {
			if (value == null || "".equals(value)) {
				// 设置空白字符
				cell.setCellType(Cell.CELL_TYPE_BLANK);
				return;
			}
			if (value instanceof String) {
				cell.setCellValue(JavaUtil.objToStr(value));
			} else if (value instanceof Calendar) {
				if (CheckUtil.isNull(partten)) {
					cell.setCellValue((Calendar) value);
				} else {
					// 日期格式化成字符串
					cell.setCellValue(DateUtil.dateParse(value, partten));
				}
			} else if (value instanceof Date || value instanceof Timestamp) {
				if (CheckUtil.isNull(partten)) {
					cell.setCellValue((Date) value);
				} else {
					// 日期格式化成字符串
					cell.setCellValue(DateUtil.dateParse(value, partten));
				}
			} else if (value instanceof Boolean) {
				cell.setCellValue((Boolean) value);
			} else if (value instanceof Number) {
				// 数字
				cell.setCellValue(Double.parseDouble(String.valueOf(value)));
			} else if (value instanceof FormulaTemplate) {
				FormulaTemplate ft = (FormulaTemplate) value;
				// 数字
				cell.setCellFormula(ft.value);
			} else {
				cell.setCellValue(JavaUtil.objToStr(value));
			}
		} catch (Exception e) {
			cell.setCellValue(JavaUtil.objToStr(value));
			e.printStackTrace();
		}
	}

	/**
	 * 插入单元格
	 * 
	 * @param sheet
	 *            sheet对象
	 * @param startRow
	 *            开始行号,从0开始
	 * @param rows
	 *            插入行数
	 */
	public static final void insertRows(final Sheet sheet, final int startRow, final int rows) {
		insertRows(sheet, startRow, rows, false);
	}

	/**
	 * 插入单元格
	 * 
	 * @param sheet
	 *            sheet对象
	 * @param startRow
	 *            开始行号,从0开始
	 * @param rows
	 *            插入行数
	 */
	public static final void insertRows(final Sheet sheet, final int startRow, final int rows, final boolean copyValue) {
		try {
			if (sheet != null) {
				int lastRowNum = sheet.getLastRowNum();
				if (startRow==lastRowNum){
					lastRowNum ++;
				}
				if (startRow >= lastRowNum) {
					logger.warn("开始行[" + startRow + "]已超出excel最大行[" + lastRowNum + "]");
					return;
				}
				int tempStartRow = startRow;
				// startRow the row to start shifting
				// endRow the row to end shifting
				// n the number of rows to shift
				// copyRowHeight whether to copy the row height during the shift
				// resetOriginalRowHeight whether to set the original row's height to the default
				// 从开始行下一行开始操作
				// Set<CellRangeAddress> srcCas = new HashSet<CellRangeAddress>();
				// for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
				// CellRangeAddress ca = sheet.getMergedRegion(i);
				// logger.debug(ca + "[" + ca.getFirstRow() + "-" + ca.getLastRow() + "," + ca.getFirstColumn() + "-" + ca.getLastColumn() + "]");
				// }
				// logger.debug("==========================");
				sheet.shiftRows(tempStartRow + 1, lastRowNum, rows, true, false);
				// 取得第一行
				Row srcRow = sheet.getRow(tempStartRow);
				if (srcRow == null) {
					return;
				}
				for (int i = 0; i < rows; i++) {
					// 循环添加合并单元格
					// 创建新行
					Row newRow = sheet.createRow(++tempStartRow);
					// 创建单元格
					// 合并的单元格式集合
					if (srcRow.getFirstCellNum() >= 0 && srcRow.getLastCellNum() >= 0) {
						// 如果源行每一列和最后一个>=0
						for (int j = srcRow.getFirstCellNum(); j <= srcRow.getLastCellNum(); j++) {
							// 循环添加列
							// 获取源行的单元格
							Cell srcCell = srcRow.getCell(j);
							if (srcCell == null) {
								continue;
							}
							// 获取源行的单元格
							Cell newCell = newRow.getCell(j);
							if (newCell == null) {
								newCell = newRow.createCell(j);
							}
							// 如果有往下或往上合并的数据,则不赋值
							if (copyValue) {
								// 如果有往下或往上合并的数据,则不赋值
								CellRangeAddress ca = getMergedRegion(sheet, srcRow.getRowNum(), srcCell.getColumnIndex(), ConstantForEnum.MergedRegionStatus.CONSTANTS_ROW_COL);
								if (ca == null) {
									// 非合并单元格,赋值
									ExcelUtil.copyCell(srcCell, newCell, true, true, true);
								} else {
									ca = getMergedRegion(sheet, srcRow.getRowNum(), srcCell.getColumnIndex(), ConstantForEnum.MergedRegionStatus.EQUALS_ROW);
									if (ca == null) {
										// 多行合并单元格,不用赋值
										ExcelUtil.copyCell(srcCell, newCell, false, true, true);
									} else {
										// 一行多列合并单元格,赋值
										ExcelUtil.copyCell(srcCell, newCell, true, true, true);
									}
								}
							} else {
								ExcelUtil.copyCell(srcCell, newCell, false, true, true);
							}
						}
					}
				}
				Set<CellRangeAddress> srcCas = new HashSet<CellRangeAddress>();
				for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
					CellRangeAddress ca = sheet.getMergedRegion(i);
					// logger.debug(ca + "[" + ca.getFirstRow() + "-" + ca.getLastRow() + "," + ca.getFirstColumn() + "-" + ca.getLastColumn() + "]");
					srcCas.add(ca);
				}
				// 合并单元格
				Set<CellRangeAddress> rowsCas = new HashSet<CellRangeAddress>();
				// 如果单元格在合并单元格范围内
				for (CellRangeAddress ca : srcCas) {
					// logger.debug(ca + "[" + ca.getFirstRow() + "-" + ca.getLastRow() + "," + ca.getFirstColumn() + "-" + ca.getLastColumn() + "]");
					if (ca.getFirstRow() == ca.getLastRow() && ca.getFirstRow() <= startRow && ca.getLastRow() > startRow) {
						// 循环列合并
						rowsCas.add(ca);
					}
					if (ca.getFirstRow() != ca.getLastRow() && ca.getFirstRow() <= startRow + rows && ca.getLastRow() > startRow + rows) {
						// 由于插入行,则合并列改变为插入后的行列单元格了,因此加rows
						// 如果起始行, 结束行, 起始列, 结束列在单元格行列中
						int caStartRow = ca.getFirstRow() - rows;
						if (caStartRow < 0) {
							continue;
						}
						mergerRegion(sheet, caStartRow, ca.getLastRow(), ca.getFirstColumn(), ca.getLastColumn());
					}
				}
				// for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
				// CellRangeAddress ca = sheet.getMergedRegion(i);
				// logger.debug(ca + "[" + ca.getFirstRow() + "-" + ca.getLastRow() + "," + ca.getFirstColumn() + "-" + ca.getLastColumn() + "]");
				// if (ca.getFirstRow() <= startRow && ca.getLastRow() >= startRow) {
				// // 循环列合并
				// rowsCas.add(ca);
				// }
				// if (ca.getFirstRow() != ca.getLastRow() && ca.getFirstRow() <= startRow + rows && ca.getLastRow() >= startRow + rows) {
				// // 由于插入行,则合并列改变为插入后的行列单元格了,因此加rows
				// // 如果起始行, 结束行, 起始列, 结束列在单元格行列中
				// mergerRegion(sheet, ca.getFirstRow() - rows, ca.getLastRow(), ca.getFirstColumn(), ca.getLastColumn());
				// }
				// }
				for (CellRangeAddress ca : rowsCas) {
					for (int i = startRow; i < rows + startRow; i++) {
						// 如果起始行, 结束行, 起始列, 结束列在单元格行列中
						mergerRegion(sheet, ca.getFirstRow() + i, ca.getLastRow() + i, ca.getFirstColumn(), ca.getLastColumn());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取合并单元格对象
	 * 
	 * @param sheet
	 *            当前sheet对象
	 * @param row
	 *            行号
	 * @param col
	 *            列号
	 * @param mergedRegionStatus
	 *            参考{@link zj.excel.util.ConstantForEnum.MergedRegionStatus}
	 * @return 合并单元格对象
	 */
	public static final CellRangeAddress getMergedRegion(final Sheet sheet, final int row, final int col, final ConstantForEnum.MergedRegionStatus mergedRegionStatus) {
		for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			switch (mergedRegionStatus) {
			case CONSTANTS_ROW_COL:
				// 获得合并单元格的起始行, 结束行, 起始列, 结束列, 行数
				if (ca.getFirstRow() <= row && ca.getLastRow() >= row && ca.getFirstColumn() <= col && ca.getLastColumn() >= col) {
					// 如果起始行, 结束行, 起始列, 结束列在单元格行列中
					return ca;
				}
				break;
			case EQUALS_ROW:
				// 获得合并单元格的起始行, 结束行, 起始列, 结束列, 行数
				if (ca.getFirstRow() == row && ca.getLastRow() == row && ca.getFirstColumn() <= col && ca.getLastColumn() >= col) {
					// 如果起始行, 结束行, 起始列, 结束列在单元格行列中
					return ca;
				}
				break;
			case EQUALS_COL:
				// 获得合并单元格的起始行, 结束行, 起始列, 结束列, 行数
				if (ca.getFirstRow() <= row && ca.getLastRow() >= row && ca.getFirstColumn() == col && ca.getLastColumn() == col) {
					// 如果起始行, 结束行, 起始列, 结束列在单元格行列中
					return ca;
				}
				break;
			}
		}
		return null;
	}

	/**
	 * 获取合并单元格对象索引
	 * 
	 * @param sheet
	 *            当前sheet对象
	 * @param row
	 *            行号
	 * @param col
	 *            列号
	 * @return 合并单元格对象索引
	 */
	public static final int getMergedRegionIndex(final Sheet sheet, final int row, final int col) {
		for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			// 获得合并单元格的起始行, 结束行, 起始列, 结束列, 行数
			if (ca.getFirstRow() <= row && ca.getLastRow() >= row && ca.getFirstColumn() <= col && ca.getLastColumn() >= col) {
				// 如果起始行, 结束行, 起始列, 结束列在单元格行列中
				return i;
			}
		}
		return -1;
	}
}