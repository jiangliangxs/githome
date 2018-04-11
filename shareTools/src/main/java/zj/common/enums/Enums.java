package zj.common.enums;

/**
 * 通用枚举类
 * 
 * @version 1.00 （2011.12.02）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class Enums {
	/**
	 * 是否标识
	 */
	public static enum _YN {
		N("0", "否"), Y("1", "是");
		private final String value;

		public String getValue() {
			return value;
		}

		private final String name;

		public String getName() {
			return name;
		}

		_YN(String value, String name) {
			this.value = value;
			this.name = name;
		}

		// 普通方法
		public static String getName(String value) {
			for (_YN entity : _YN.values()) {
				if (entity.getValue().equals(value)) {
					return entity.name;
				}
			}
			return "未知";
		}
	}

	/**
	 * 删除标识
	 * 
	 * @see _YN
	 */
	@Deprecated
	public static enum _Delete {
		NOT_DELETE("0", "未删除"), DELETE("1", "已删除");
		private final String value;

		public String getValue() {
			return value;
		}

		private final String name;

		public String getName() {
			return name;
		}

		_Delete(String value, String name) {
			this.value = value;
			this.name = name;
		}

		// 普通方法
		public static String getName(String value) {
			for (_Delete entity : _Delete.values()) {
				if (entity.getValue().equals(value)) {
					return entity.name;
				}
			}
			return "未知";
		}
	}

	/**
	 * 升序降序
	 */
	public static enum _Order {
		DESC(" desc", "降序"), ASC(" asc", "升序");
		private final String value;

		public String getValue() {
			return value;
		}

		private final String name;

		public String getName() {
			return name;
		}

		_Order(String value, String name) {
			this.value = value;
			this.name = name;
		}

		// 普通方法
		public static String getName(String value) {
			for (_Order entity : _Order.values()) {
				if (entity.getValue().equals(value)) {
					return entity.name;
				}
			}
			return "未知";
		}
	}
}
