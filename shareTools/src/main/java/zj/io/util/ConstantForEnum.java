package zj.io.util;

/**
 * 常量枚举
 * 
 * @version 1.00 （2011.12.02）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class ConstantForEnum {
	/**
	 * 最后分割符状态
	 * 
	 * @author zhangjun
	 * 
	 */
	public static enum ChangePathLastSeparator {
		/**
		 * 不改变
		 */
		NONE,
//		/**
//		 * 添加分割符
//		 * 
//		 * @see #ADD_ALL
//		 * @see #ADD_BEFORE
//		 * @see #ADD_AFTER
//		 */
//		@Deprecated
//		ADD,
//		/**
//		 * 删除分割符
//		 * 
//		 * @see #DEL_ALL
//		 * @see #DEL_BEFORE
//		 * @see #DEL_AFTER
//		 */
//		@Deprecated
//		DEL,
		/**
		 * 添加前后割符
		 */
		ADD_ALL,
		/**
		 * 添加前分割符
		 */
		ADD_BEFORE,
		/**
		 * 添加后分割符
		 */
		ADD_AFTER,
		/**
		 * 删除前后分割符
		 */
		DEL_ALL,
		/**
		 * 删除前分割符
		 */
		DEL_BEFORE,
		/**
		 * 删除后分割符
		 */
		DEL_AFTER
	}
	// /**
	// * 最后分割符状态
	// *
	// * @author zhangjun
	// *
	// */
	// public static enum ChangePathLastSeparator {
	// NONE("0", "不改变"), ADD("0", "添加分割符"), DEL("0", "删除分割符");
	// private final String name;
	// private final String value;
	//
	// /**
	// * 获取枚举值
	// *
	// * @return 枚举值
	// */
	// public String getValue() {
	// return value;
	// }
	//
	// /**
	// * 获取枚举描述
	// *
	// * @return 枚举描述
	// */
	// public String getName() {
	// return name;
	// }
	//
	// /**
	// * 实例对象
	// *
	// * @param value
	// * 枚举值
	// * @param name
	// * 枚举描述
	// */
	// private ChangePathLastSeparator(String value, String name) {
	// this.value = value;
	// this.name = name;
	// }
	// }
}
