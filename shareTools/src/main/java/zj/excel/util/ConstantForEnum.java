package zj.excel.util;

/**
 * 常量枚举
 * 
 * @version 1.00 （2011.12.02）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class ConstantForEnum {
	/**
	 * 单元格式状态
	 * 
	 * @version 1.00 （2014.09.15）
	 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
	 */
	public static enum MergedRegionStatus {
		/**
		 * 合并单元格行列的单元格
		 */
		CONSTANTS_ROW_COL,
		/**
		 * 合并单元格列的单元格
		 */
		EQUALS_ROW,
		/**
		 * 合并单元格行的单元格
		 */
		EQUALS_COL;
	}

	/**
	 * 单元格值key 如果ROW_INDEX, COLUMN_INDEX值不为空，则以这两个枚举值优先
	 * 
	 * @version 1.00 （2014.09.15）
	 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
	 */
	public static enum CellValueKey {
		VALUE, VALUE_COUNT, VALUE_ROW, VALUE_COLUMN, VALUE_COUNT_MATCH, ROW_VALUE, COLUMN_VALUE, ROW_COUNT, COLUMN_COUNT, ROW_COUNT_MATCH, COLUMN_COUNT_MATCH, ROW_INDEX, COLUMN_INDEX;
	}

	// /**
	// * 单元格值key
	// *
	// * @author zhangjun
	// *
	// */
	// public static enum CellValueKey {
	// ROW_VALUE("e6e003d2e9a3f8579ea76010b24b024f", "行值"), COLUMN_VALUE("93f8547230119db1d208423c09ee230c", "列值"), ROW_COUNT("e66c1556e78eda867f14ec8b539d639d", "第n行"), COLUMN_COUNT("4ae06dcd062049a0601a3706b5b84158", "第n列"), ROW_COUNT_MATCH("be35d0cd35542d5d2c7f8a9907a16f0d", "第n行必须匹配(true)，不匹配(false)，默认不匹配(false)取得最后一个"),COLUMN_COUNT_MATCH("252f9e1edbcd5133dd23b56ba03cfda1", "第n列必须匹配(true)，不匹配(false)，默认不匹配(false)取得最后一个");
	// private final String value;
	// private final String name;
	//
	// public String getValue() {
	// return value;
	// }
	//
	// public String getName() {
	// return name;
	// }
	//
	// CellValueKey(String value, String name) {
	// this.value = value;
	// this.name = name;
	// }
	//
	// // 普通方法
	// public static String getName(String value) {
	// for (CellValueKey entity : CellValueKey.values()) {
	// if (entity.getValue().equals(value)) {
	// return entity.name;
	// }
	// }
	// return "未知";
	// }
	// }
}
