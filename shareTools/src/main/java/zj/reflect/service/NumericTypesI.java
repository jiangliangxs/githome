package zj.reflect.service;

/**
 * 
 * @version 1.00 （2014.09.15）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public interface NumericTypesI {
	// Order does matter here... see the getNumericType methods in ognl.g.

	/** Type tag meaning boolean. */
	int BOOL = 0;
	/** Type tag meaning byte. */
	int BYTE = 1;
	/** Type tag meaning char. */
	int CHAR = 2;
	/** Type tag meaning short. */
	int SHORT = 3;
	/** Type tag meaning int. */
	int INT = 4;
	/** Type tag meaning long. */
	int LONG = 5;
	/** Type tag meaning java.math.BigInteger. */
	int BIGINT = 6;
	/** Type tag meaning float. */
	int FLOAT = 7;
	/** Type tag meaning double. */
	int DOUBLE = 8;
	/** Type tag meaning java.math.BigDecimal. */
	int BIGDEC = 9;
	/** Type tag meaning something other than a number. */
	int NONNUMERIC = 10;

	/**
	 * The smallest type tag that represents reals as opposed to integers. You can see whether a type tag represents reals or integers by comparing the tag to this constant: all tags less than this constant represent integers, and all tags greater than or equal to this constant represent reals. Of course, you must also check for NONNUMERIC, which means it is not a number at all.
	 */
	int MIN_REAL_TYPE = FLOAT;
}
