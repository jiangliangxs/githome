package zj.excel.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import zj.io.util.FileUtil;

/**
 * 概况 ：Excel/xls/xlsx<br>
 * 
 * @version 1.00 （2011.12.02）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class Excel implements Serializable {
	private static final long serialVersionUID = 1L;
	private Workbook wb;
	private boolean readMemoryWb;
	private String filePath;
	private Object sheetValue;
	private boolean delete = false;
	private String reFilePath;
	private boolean addRowNull;
	private boolean addTitleKeyRow = true;
	private boolean containTitleKeys = true;
	private int titleRow = -1;
	private boolean titleRowNullContinue = true;
	private List<String> titleKeys;
	private List<String> tempTitleKeys;
	private boolean readTitleKeys;
	private String jsonParams;
	private boolean autoWriteExcel = true;

	/**
	 * 此项可无需使用
	 * 
	 * @return 是否立即写入excel数据
	 */
	public boolean isAutoWriteExcel() {
		return autoWriteExcel;
	}

	/**
	 * 此项可无需使用
	 * 
	 * @return 是否立即写入excel数据
	 */
	public void setAutoWriteExcel(boolean autoWriteExcel) {
		this.autoWriteExcel = autoWriteExcel;
	}

	/**
	 * 通用json参数
	 * 
	 * @return 通用json参数
	 */
	public String getJsonParams() {
		return jsonParams;
	}

	/**
	 * 通用json参数
	 * 
	 * @param jsonParams
	 *            通用json参数
	 */
	public void setJsonParams(String jsonParams) {
		this.jsonParams = jsonParams;
	}

	public boolean isTitleRowNullContinue() {
		return titleRowNullContinue;
	}

	public void setTitleRowNullContinue(boolean titleRowNullContinue) {
		this.titleRowNullContinue = titleRowNullContinue;
	}

	public List<String> getTempTitleKeys() {
		return tempTitleKeys;
	}

	public void setTempTitleKeys(List<String> tempTitleKeys) {
		this.tempTitleKeys = tempTitleKeys;
	}

	/**
	 * 获取是否读取title(如果没有设置标题key,则设置此项，否则无需设置此项)
	 * 
	 * @return 是否读取title
	 */
	public boolean isReadTitleKeys() {
		return readTitleKeys;
	}

	/**
	 * 设置是否读取title(如果没有设置标题key,则设置此项，否则无需设置此项)
	 * 
	 * @param readTitleKeys
	 *            是否读取title
	 */
	public void setReadTitleKeys(boolean readTitleKeys) {
		this.readTitleKeys = readTitleKeys;
	}

	/**
	 * 获取是否重新创建wb对象，在输出多个模板sheet写入时此项必须设置
	 * 
	 * @return 是否重新创建wb对象
	 */
	public boolean isReadMemoryWb() {
		return readMemoryWb;
	}

	/**
	 * 设置是否重新创建wb对象，在输出多个模板sheet写入时此项必须设置
	 * 
	 * @param readMemoryWb
	 *            是否重新创建wb对象
	 */
	public void setReadMemoryWb(boolean readMemoryWb) {
		this.readMemoryWb = readMemoryWb;
	}

	/**
	 * 获取是否添加标题行(true:添加[默认]，false:不添加)
	 * 
	 * @return 是否添加标题行
	 */
	public boolean isAddTitleKeyRow() {
		return addTitleKeyRow;
	}

	/**
	 * 设置是否添加标题行(true:添加[默认]，false:不添加)
	 * 
	 * @param addTitleKeyRow
	 *            是否添加标题行
	 */
	public void setAddTitleKeyRow(boolean addTitleKeyRow) {
		this.addTitleKeyRow = addTitleKeyRow;
	}

	/**
	 * 是否添加行为null的数据
	 * 
	 * @return 是否添加行为null的数据
	 */
	public boolean isAddRowNull() {
		return addRowNull;
	}

	/**
	 * 是否添加行为null的数据
	 * 
	 * @param addRowNull
	 *            是否添加行为null的数据
	 */
	public void setAddRowNull(boolean addRowNull) {
		this.addRowNull = addRowNull;
	}

	/**
	 * 是否包含标题行键(true:包含[默认]，false:不包含)
	 * 
	 * @return 是否包含标题行键
	 */
	public boolean isContainTitleKeys() {
		return containTitleKeys;
	}

	/**
	 * 设置是否包含标题行键(true:包含[默认]，false:不包含)
	 * 
	 * @param containTitleKeys
	 *            是否包含标题行键
	 */
	public void setContainTitleKeys(boolean containTitleKeys) {
		this.containTitleKeys = containTitleKeys;
	}

	/**
	 * 获取标题行号从0开始
	 * 
	 * @return 标题行号从0开始
	 */
	public int getTitleRow() {
		return titleRow;
	}

	/**
	 * 设置标题行号从0开始
	 * 
	 * @param titleRow
	 *            标题行号从0开始
	 */
	public void setTitleRow(int titleRow) {
		this.titleRow = titleRow;
	}

	/**
	 * 获取标题行主键
	 * 
	 * @return 标题行主键
	 */
	public List<String> getTitleKeys() {
		return titleKeys;
	}

	/**
	 * 设置标题行主键
	 * 
	 * @param titleKeys
	 *            标题行主键
	 */
	public void setTitleKeys(List<String> titleKeys) {
		this.titleKeys = titleKeys;
	}

	/**
	 * 获取重命名文件路径
	 * 
	 * @return 重命名文件路径
	 */
	public String getReFilePath() {
		return reFilePath;
	}

	/**
	 * 设置重命名文件路径
	 * 
	 * @param reFilePath
	 *            重命名文件路径
	 */
	public void setReFilePath(String reFilePath) {
		this.reFilePath = reFilePath;
	}

	/**
	 * 是否删除存在的文件
	 * 
	 * @return true:删除,false:不删除
	 */
	public boolean isDelete() {
		return delete;
	}

	/**
	 * 设置是否删除存在的文件
	 * 
	 * @param delete
	 *            删除状态
	 */
	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	/**
	 * 获取workbook对象
	 * 
	 * @return workbook对象
	 */
	public Workbook getWb() {
		return wb;
	}

	/**
	 * 设置workbook对象
	 * 
	 * @param wb
	 *            workbook对象
	 */
	public void setWb(Workbook wb) {
		this.wb = wb;
	}

	/**
	 * 获取文件路径
	 * 
	 * @return 文件路径
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * 设置文件路径
	 * 
	 * @param filePath
	 *            文件路径
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * 根据索引或名称(可以为集合)获取某个sheet,默认获取所有sheet
	 * 
	 * @see 新版本V1.1:模板写入以*号隔开,源sheet名->目标sheet名
	 * 
	 * @return 索引或名称(可以为集合)
	 */
	public Object getSheetValue() {
		return sheetValue;
	}

	/**
	 * 设置索引或名称(可以为集合)
	 * 
	 * @see 新版本V1.1:模板写入以*号隔开,源sheet名->目标sheet名
	 * @param sheetValue
	 */
	public void setSheetValue(Object sheetValue) {
		this.sheetValue = sheetValue;
	}

	/**
	 * 初使化Workbook
	 * 
	 * @throws Exception
	 */
	public void initWorkbook() throws Exception {
		if (this.readMemoryWb) {
			if (this.wb != null) {
				logger.debug("取得已创建好的Workbook对象");
				return;
			}
		}
		Workbook wb = null;
		InputStream is = null;
		File file = null;
		try {
			String[] paths = FileUtil.getFileNameExtension(this.filePath);
			if ("false".equals(paths[3])) {
				// 文件夹
				file = new File(paths[0]);
				if (!file.exists()) {
					file.mkdirs();
				}
			}
			// 文件
			file = new File(paths[0] + paths[1] + paths[2]);
			if (this.delete) {
				if (file.exists()) {
					file.delete();
				}
			}
			try {
				if (".xls".equalsIgnoreCase(paths[2])) {
					if (file.exists()) {
						// 设置要读取的文件路径
						// HSSFWorkbook相当于一个excel文件，HSSFWorkbook是解析excel2007之前的版本（xls）
						// 之后版本使用XSSFWorkbook（xlsx）
						is = new FileInputStream(this.filePath);
						wb = new HSSFWorkbook(is);
					} else {
						wb = new HSSFWorkbook();
					}
				} else if (".xlsx".equalsIgnoreCase(paths[2])) {
					if (file.exists()) {
						// 设置要读取的文件路径
						// HSSFWorkbook相当于一个excel文件，HSSFWorkbook是解析excel2007之前的版本（xls）
						// 之后版本使用XSSFWorkbook（xlsx）
						is = new FileInputStream(this.filePath);
						wb = new XSSFWorkbook(is);
					} else {
						wb = new XSSFWorkbook();
					}
				}
			} catch (Exception e) {
				logger.warn("数据文件格式不正确，改用自动判断版本");
				try {
					if (file.exists()) {
						// 设置要读取的文件路径
						// HSSFWorkbook相当于一个excel文件，HSSFWorkbook是解析excel2007之前的版本（xls）
						// 之后版本使用XSSFWorkbook（xlsx）
						is = new FileInputStream(this.filePath);
						wb = new HSSFWorkbook(is);
					} else {
						wb = new HSSFWorkbook();
					}
				} catch (Exception e1) {
					if (file.exists()) {
						// 设置要读取的文件路径
						// HSSFWorkbook相当于一个excel文件，HSSFWorkbook是解析excel2007之前的版本（xls）
						// 之后版本使用XSSFWorkbook（xlsx）
						is = new FileInputStream(this.filePath);
						wb = new XSSFWorkbook(is);
					} else {
						wb = new XSSFWorkbook();
					}
				}
			}
		} catch (Exception e) {
			logger.warn("数据文件格式不正确，改用自动判断版本,通用读法");
			try {
				if (file.exists()) {
					// 设置要读取的文件路径
					// HSSFWorkbook相当于一个excel文件，HSSFWorkbook是解析excel2007之前的版本（xls）
					// 之后版本使用XSSFWorkbook（xlsx）
					is = new FileInputStream(this.filePath);
					wb = WorkbookFactory.create(is);
				} else {
					wb = new XSSFWorkbook();
				}
			} catch (Exception e1) {
				e.printStackTrace();
				throw e;
			}
		} finally {
			if (is != null) {
				is.close();
			}
		}
		this.wb = wb;
		logger.debug("创建workbook对象成功");
	}

	private final Logger logger = Logger.getLogger(this.getClass());
}