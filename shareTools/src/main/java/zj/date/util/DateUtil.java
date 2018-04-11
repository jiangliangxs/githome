package zj.date.util;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * 
 * @author zhangjun
 * 
 */
public class DateUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	private transient static final Logger log = Logger.getLogger(DateUtil.class);
//	DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
//	String value = "'" + formatter.format(new Date()) + "'";
//	System.out.println(value);
	/**
	 * 日期类型
	 * 
	 * @author zhangjun
	 * 
	 */
	public static class DateType implements Serializable {
		private static final long serialVersionUID = 1L;
		public static int DATE_TYPE_ALL = 0;
		public static int DATE_TYPE_y4 = 1;
		public static int DATE_TYPE_y4_MM = 2;
		public static int DATE_TYPE_y4_MM_dd = 3;
		public static int DATE_TYPE_y4_MM_dd_HH = 4;
		public static int DATE_TYPE_y4_MM_dd_HH_mm = 5;
		public static int DATE_TYPE_y4_MM_dd_HH_mm_ss = 6;

		public static int RANGE_TYPE_y = 0;
		public static int RANGE_TYPE_M = 1;
		public static int RANGE_TYPE_d = 2;
		public static int RANGE_TYPE_H = 3;
		public static int RANGE_TYPE_m = 4;
		public static int RANGE_TYPE_s = 5;
	}

	/**
	 * 字符串转日期
	 * 
	 * @param dt
	 *            日期字符串
	 * @return 日期对象
	 */
	public static Date parseDate(String dt) {
		return parseDate(dt, DateType.DATE_TYPE_ALL);
	}

	/**
	 * 字符串转日期
	 * 
	 * @param dt
	 *            日期字符串
	 * @param dateType
	 *            日期类型
	 * @see DateType
	 * @return 日期对象
	 */
	public static Date parseDate(String dt, int dateType) {
		Date rd = null;
		try {
			if (dt == null || dt == "") {
				log.warn("日期不能为:" + dt);
			} else {
				boolean isFail = false;
				String _val_format = "【" + dt + "】不是一个有效的日期格式\n支持日期格式如下：\n";
				if (dateType == DateType.DATE_TYPE_y4) {
					_val_format += "【yyyy】";
				} else if (dateType == DateType.DATE_TYPE_y4_MM) {
					_val_format += "【yyyyMM或yyyy-MM或yyyy/MM或yyyy MM】";
				} else if (dateType == DateType.DATE_TYPE_y4_MM_dd) {
					_val_format += "【yyyyMMdd或yyyy-MM-dd或yyyy/MM/dd或yyyy MM dd】";
				} else if (dateType == DateType.DATE_TYPE_y4_MM_dd_HH) {
					_val_format += "【yyyyMMddhh或yyyy-MM-dd hh或yyyy/MM/dd hh或yyyy MM dd hh】";
				} else if (dateType == DateType.DATE_TYPE_y4_MM_dd_HH_mm) {
					_val_format += "【yyyyMMddhhmm或yyyy-MM-dd hh:mm或yyyy/MM/dd hh:mm或yyyy MM dd hh:mm】";
				} else if (dateType == DateType.DATE_TYPE_y4_MM_dd_HH_mm_ss) {
					_val_format += "【yyyyMMddhhmmss或yyyy-MM-dd hh:mm:ss或yyyy/MM/dd hh:mm:ss或yyyy MM dd hh:mm:ss】";
				} else {
					dateType = DateType.DATE_TYPE_ALL;
					_val_format += "【yyyy】";
					_val_format += "【yyyyMM或yyyy-MM或yyyy/MM或yyyy MM】";
					_val_format += "【yyyyMMdd或yyyy-MM-dd或yyyy/MM/dd或yyyy MM dd】";
					_val_format += "【yyyyMMddhh或yyyy-MM-dd hh或yyyy/MM/dd hh或yyyy MM dd hh】";
					_val_format += "【yyyyMMddhhmm或yyyy-MM-dd hh:mm或yyyy/MM/dd hh:mm或yyyy MM dd hh:mm】";
					_val_format += "【yyyyMMddhhmmss或yyyy-MM-dd hh:mm:ss或yyyy/MM/dd hh:mm:ss或yyyy MM dd hh:mm:ss】";
				}
				String datePat = "";
				datePat = "^(\\d{4})(/|-| )(\\d{1,2})(\\2)([0-9]{1,2}) (\\d{1,2}):(\\d{1,2}):(\\d{1,2})$";
				Matcher mt = Pattern.compile(datePat).matcher(dt);
				String year = "";
				String month = "";
				String day = "";
				String hour = "";
				String minute = "";
				String second = "";
				if (mt.find()) {
					if (dateType == DateType.DATE_TYPE_y4_MM_dd_HH_mm_ss || dateType == DateType.DATE_TYPE_ALL) {
						year = mt.group(1);
						month = mt.group(3);
						day = mt.group(5);
						hour = mt.group(6);
						minute = mt.group(7);
						second = mt.group(8);
					} else {
						isFail = true;
					}
				} else {
					datePat = "^(\\d{14})$";
					mt = Pattern.compile(datePat).matcher(dt);
					if (mt.find()) {
						if (dateType == DateType.DATE_TYPE_y4_MM_dd_HH_mm_ss || dateType == DateType.DATE_TYPE_ALL) {
							year = dt.substring(0, 4);
							month = dt.substring(4, 6);
							day = dt.substring(6, 8);
							hour = dt.substring(8, 10);
							minute = dt.substring(10, 12);
							second = dt.substring(12, 14);
						} else {
							isFail = true;
						}

					} else {
						datePat = "^(\\d{4})(/|-| )(\\d{1,2})(\\2)(\\d{1,2}) (\\d{1,2}):(\\d{1,2})$";
						mt = Pattern.compile(datePat).matcher(dt);
						if (mt.find()) {
							if (dateType == DateType.DATE_TYPE_y4_MM_dd_HH_mm || dateType == DateType.DATE_TYPE_ALL) {
								year = mt.group(1);
								month = mt.group(3);
								day = mt.group(5);
								hour = mt.group(6);
								minute = mt.group(7);
								second = "0";
							} else {
								isFail = true;
							}
						} else {
							datePat = "^(\\d{12})$";
							mt = Pattern.compile(datePat).matcher(dt);
							if (mt.find()) {
								if (dateType == DateType.DATE_TYPE_y4_MM_dd_HH_mm || dateType == DateType.DATE_TYPE_ALL) {
									year = dt.substring(0, 4);
									month = dt.substring(4, 6);
									day = dt.substring(6, 8);
									hour = dt.substring(8, 10);
									minute = dt.substring(10, 12);
									second = "0";
								} else {
									isFail = true;
								}
							} else {
								datePat = "^(\\d{4})(/|-| )(\\d{1,2})(\\2)(\\d{1,2}) (\\d{1,2})$";
								mt = Pattern.compile(datePat).matcher(dt);
								if (mt.find()) {
									if (dateType == DateType.DATE_TYPE_y4_MM_dd_HH || dateType == DateType.DATE_TYPE_ALL) {
										year = mt.group(1);
										month = mt.group(3);
										day = mt.group(5);
										hour = mt.group(6);
										minute = "0";
										second = "0";
									} else {
										isFail = true;
									}
								} else {
									datePat = "^(\\d{10})$";
									mt = Pattern.compile(datePat).matcher(dt);
									if (mt.find()) {
										if (dateType == DateType.DATE_TYPE_y4_MM_dd_HH || dateType == DateType.DATE_TYPE_ALL) {
											year = dt.substring(0, 4);
											month = dt.substring(4, 6);
											day = dt.substring(6, 8);
											hour = dt.substring(8, 10);
											minute = "0";
											second = "0";
										} else {
											isFail = true;
										}
									} else {
										datePat = "^(\\d{4})(/|-| )(\\d{1,2})(\\2)(\\d{1,2})$";
										mt = Pattern.compile(datePat).matcher(dt);
										if (mt.find()) {
											if (dateType == DateType.DATE_TYPE_y4_MM_dd || dateType == DateType.DATE_TYPE_ALL) {
												year = mt.group(1);
												month = mt.group(3);
												day = mt.group(5);
												hour = "0";
												minute = "0";
												second = "0";
											} else {
												isFail = true;
											}
										} else {
											datePat = "^(\\d{8})$";
											mt = Pattern.compile(datePat).matcher(dt);
											if (mt.find()) {
												if (dateType == DateType.DATE_TYPE_y4_MM_dd || dateType == DateType.DATE_TYPE_ALL) {
													year = dt.substring(0, 4);
													month = dt.substring(4, 6);
													day = dt.substring(6, 8);
													hour = "0";
													minute = "0";
													second = "0";
												} else {
													isFail = true;
												}
											} else {
												datePat = "^(\\d{4})(/|-| )(\\d{1,2})$";
												mt = Pattern.compile(datePat).matcher(dt);
												if (mt.find()) {
													if (dateType == DateType.DATE_TYPE_y4_MM || dateType == DateType.DATE_TYPE_ALL) {
														year = mt.group(1);
														month = mt.group(3);
														day = "1";
														hour = "0";
														minute = "0";
														second = "0";
													} else {
														isFail = true;
													}
												} else {
													datePat = "^(\\d{6})$";
													mt = Pattern.compile(datePat).matcher(dt);
													if (mt.find()) {
														if (dateType == DateType.DATE_TYPE_y4_MM || dateType == DateType.DATE_TYPE_ALL) {
															year = dt.substring(0, 4);
															month = dt.substring(4, 6);
															day = "1";
															hour = "0";
															minute = "0";
															second = "0";
														} else {
															isFail = true;
														}
													} else {
														datePat = "^(\\d{4})$";
														mt = Pattern.compile(datePat).matcher(dt);
														if (mt.find()) {
															if (dateType == DateType.DATE_TYPE_y4 || dateType == DateType.DATE_TYPE_ALL) {
																year = mt.group(1);
																month = "1";
																day = "1";
																hour = "0";
																minute = "0";
																second = "0";
															} else {
																isFail = true;
															}
														} else {
															_val_format = "【" + dt + "】不是一个有效的日期格式";
															isFail = true;
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
				if (isFail) {
					log.warn(_val_format);
				} else {
					int iyear = Integer.parseInt(year);
					int imonth = Integer.parseInt(month);
					int iday = Integer.parseInt(day);
					int ihour = Integer.parseInt(hour);
					int iminute = Integer.parseInt(minute);
					int isecond = Integer.parseInt(second);
					if (iyear < 1 || iyear > 9999) {
						_val_format = "年份必须在0001-9999之间";
						isFail = true;
					}
					if (imonth < 1 || imonth > 12) {
						_val_format = "月份必须在01-12之间";
						isFail = true;
					}
					if (iday < 1 || iday > 31) {
						_val_format = "天数必须在01-31之间";
						isFail = true;
					}
					if ((imonth == 4 || imonth == 6 || imonth == 9 || imonth == 11) && iday == 31) {
						_val_format = month + "月不能有31天!";
						isFail = true;
					}
					if (imonth == 2) {
						boolean isleap = (iyear % 4 == 0 && iyear % 100 != 0) || iyear % 400 == 0;
						if (isleap) {
							if (iday > 29) {
								_val_format = "闰年[" + year + "]年的2月不能有[" + day + "]天!";
								isFail = true;
							}
						} else {
							if (iday > 28) {
								_val_format = "平年[" + year + "]年的2月不能有[" + day + "]天!";
								isFail = true;
							}
						}
					}
					if (ihour < 0 || ihour > 23) {
						_val_format = "小时必须在00-23之间";
						isFail = true;
					}
					if (iminute < 0 || iminute > 59) {
						_val_format = "分钟必须在00-59之间";
						isFail = true;
					}
					if (isecond < 0 || isecond > 59) {
						_val_format = "秒钟必须在00-59之间";
						isFail = true;
					}
					if (isFail) {
						log.warn(_val_format);
					} else {
						// //("调试字符串如下【开始】\n" + sb.toString() + "\n调试字符串如下【结束】");
						Calendar calendar = Calendar.getInstance();
						calendar.setTimeInMillis(0);
						calendar.set(iyear, imonth - 1, iday, ihour, iminute, isecond);
						rd = calendar.getTime();
					}
				}
			}
		} catch (Exception e) {
			rd = null;
			log.error(e.getMessage());
		}
		return rd;
	}

	/**
	 * 日期解析字符串
	 * 
	 * @param odt
	 *            日期对象
	 * @return 返回格式化后的日期字符串,如果出错返回当前日期对象
	 */
	public static String dateParse(Object odt) {
		return dateParse(odt, null);
	}

	/**
	 * 日期解析字符串
	 * 
	 * @param odt
	 *            日期对象
	 * @param partten
	 *            日期格式
	 *            <p>
	 *            yyyy-MM-dd HH:mm:ss:SSS w p
	 *            </p>
	 * @return 返回格式化后的日期字符串,如果出错返回当前日期对象
	 */
	public static String dateParse(Object odt, String partten) {
		try {
			if (odt == null)
				return "";
			Date dt = null;
			if (odt instanceof Date) {
				dt = (Date) odt;
			} else if (odt instanceof Timestamp) {
				Timestamp ts = (Timestamp) odt;
				dt = new Date(ts.getTime());
			} else if (odt instanceof Calendar) {
				Calendar cal = (Calendar) odt;
				dt = cal.getTime();
			} else if (odt instanceof String) {
				dt = parseDate(String.valueOf(odt));
				if (dt == null) {
					log.error("无效的日期:" + odt);
					return "";
				}
			} else {
				log.error("无效的日期:" + odt);
				return String.valueOf(odt);
			}
			return getParseDt(dt, partten);
		} catch (Exception e) {
			log.error(e.getMessage());
			return String.valueOf(odt);
		}
	}

	/**
	 * 日期解析字符串
	 * 
	 * @param dt
	 *            日期对象
	 * @param partten
	 *            日期格式
	 *            <p>
	 *            yyyy-MM-dd HH:mm:ss:SSS w p
	 *            </p>
	 * @return 返回格式化后的日期字符串,如果出错返回当前日期对象
	 */
	public static String getParseDt(Date dt, String partten) {
		if (dt == null)
			return "";
		if (partten == null || "".equals(partten)) {
			partten = "yyyy-MM-dd HH:mm:ss:SSS w p";
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		String year = "" + cal.get(Calendar.YEAR);
		String month = "" + (cal.get(Calendar.MONTH) + 1);
		String day = "" + cal.get(Calendar.DATE);
		String hour = "" + cal.get(Calendar.HOUR_OF_DAY);
		String minute = "" + cal.get(Calendar.MINUTE);
		String second = "" + cal.get(Calendar.SECOND);
		String millisecond = "" + cal.get(Calendar.MILLISECOND);
		int week_of_month = cal.get(Calendar.DAY_OF_WEEK) - 1;
		String ws = "星期";
		switch (week_of_month) {
		case 1:
			ws += "一";
			break;
		case 2:
			ws += "二";
			break;
		case 3:
			ws += "三";
			break;
		case 4:
			ws += "四";
			break;
		case 5:
			ws += "五";
			break;
		case 6:
			ws += "六";
			break;
		case 0:
			ws += "日";
			break;
		default:
			ws = "";
			break;
		}
		int r = cal.get(Calendar.AM_PM);
		String ap = "";
		if (r == Calendar.AM) {
			ap = "上午";
		}
		if (r == Calendar.PM) {
			ap = "下午";
		}
		String matStr = "";
		matStr = "(y|Y){4}";
		Matcher mt = Pattern.compile(matStr).matcher(partten);
		partten = mt.replaceAll(addForLR(year, "0", 4));
		matStr = "M{2}";
		mt = Pattern.compile(matStr).matcher(partten);
		partten = mt.replaceAll(addForLR(month, "0", 2));
		matStr = "d{2}";
		mt = Pattern.compile(matStr).matcher(partten);
		partten = mt.replaceAll(addForLR(day, "0", 2));
		matStr = "(h|H){2}(24)?";
		mt = Pattern.compile(matStr).matcher(partten);
		partten = mt.replaceAll(addForLR(hour, "0", 2));
		matStr = "m{2}";
		mt = Pattern.compile(matStr).matcher(partten);
		partten = mt.replaceAll(addForLR(minute, "0", 2));
		matStr = "s{2}";
		mt = Pattern.compile(matStr).matcher(partten);
		partten = mt.replaceAll(addForLR(second, "0", 2));
		matStr = "S{3}";
		mt = Pattern.compile(matStr).matcher(partten);
		partten = mt.replaceAll(addForLR(millisecond, "0", 3));
		matStr = "w|W";
		mt = Pattern.compile(matStr).matcher(partten);
		partten = mt.replaceAll(ws);
		matStr = "p|P";
		mt = Pattern.compile(matStr).matcher(partten);
		partten = mt.replaceAll(ap);

		matStr = "(y|Y){2}";
		mt = Pattern.compile(matStr).matcher(partten);
		partten = mt.replaceAll(year.length() > 2 ? year.substring(year.length() - 2, year.length()) : year);
		matStr = "M";
		mt = Pattern.compile(matStr).matcher(partten);
		partten = mt.replaceAll(month);
		matStr = "d";
		mt = Pattern.compile(matStr).matcher(partten);
		partten = mt.replaceAll(day);
		matStr = "(h|H){1}(12)?";
		mt = Pattern.compile(matStr).matcher(partten);
		partten = mt.replaceAll(addForLR((Integer.parseInt(hour) > 12 ? "" + Math.abs(Integer.parseInt(hour) - 12) : hour), "0", 2));
		// matStr = "(h|H)12";
		// mt = Pattern.compile(matStr).matcher(partten);
		// partten = mt.replaceAll(Integer.parseInt(hour) > 12 ? "" + Math.abs(Integer.parseInt(hour) - 12) : hour);
		// matStr = "(h|H)24";
		// mt = Pattern.compile(matStr).matcher(partten);
		// partten = mt.replaceAll(hour);
		matStr = "m";
		mt = Pattern.compile(matStr).matcher(partten);
		partten = mt.replaceAll(minute);
		matStr = "s";
		mt = Pattern.compile(matStr).matcher(partten);
		partten = mt.replaceAll(second);
		matStr = "S";
		mt = Pattern.compile(matStr).matcher(partten);
		partten = mt.replaceAll(millisecond);
		return partten;
	}

	/**
	 * 数字前补0可以补"0"也可以补""
	 * 
	 * @param oldStr
	 *            旧字符串
	 * @param addStr
	 *            添加字符串
	 * @param strLength
	 *            字符长度
	 * @param isLeft
	 *            <p>
	 *            true:左补字符
	 *            </p>
	 *            <p>
	 *            false:右补字符
	 *            </p>
	 * @return 重组后的数据
	 */
	public static String addForLR(String oldStr, String addStr, int strLength, boolean isLeft) {
		if (oldStr == null || addStr == null)
			return oldStr;
		int strLen = oldStr.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				if (isLeft) {
					sb.append(addStr).append(oldStr);
				} else {
					sb.append(oldStr).append(addStr);
				}
				oldStr = sb.toString();
				strLen = oldStr.length();
			}
		}

		return oldStr;
	}

	/**
	 * 数字前补0可以补"0"也可以补""
	 * 
	 * @param oldStr
	 *            旧字符串
	 * @param addStr
	 *            添加字符串
	 * @param strLength
	 *            字符长度
	 * @return 重组后的数据
	 */
	public static String addForLR(String oldStr, String addStr, int strLength) {
		return addForLR(oldStr, addStr, strLength, true);
	}

	/**
	 * 计算日期范围
	 * 
	 * @param startTime
	 *            开始日期对象
	 * @param endTime
	 *            结束日期对象
	 * @param rangeType
	 *            范围类型
	 * @param ymdhms
	 *            年月日时分秒
	 * @param dateType
	 *            日期类型
	 */
	public static String dateRangeFlg(Object startTime, Object endTime, int rangeType, int ymdhms, int dateType) {
		String sstartTime = startTime == null ? "" : String.valueOf(startTime);
		String sendTime = endTime == null ? "" : String.valueOf(endTime);
		String rtn_1 = "1";
		String rtn_2 = "2";
		String rtn_3 = "3";
		String rtn_4 = "4";
		String rtn_5 = "5";
		String rtn_6 = "6";
		try {
			if ("".equals(sstartTime)) {
				return rtn_4;
			}
			if ("".equals(sendTime)) {
				return rtn_5;
			}
			Date checktime1 = null;
			Date checktime2 = null;
			if (startTime instanceof Date) {
				checktime1 = (Date) startTime;
			} else {
				checktime1 = parseDate(sstartTime, dateType);
			}
			if (checktime1 == null) {
				return rtn_4;
			}
			if (endTime instanceof Date) {
				checktime2 = (Date) endTime;
			} else {
				checktime2 = parseDate(sendTime, dateType);
			}
			if (checktime2 == null) {
				return rtn_5;
			}
			if (startTime == "" || endTime == "")
				return "";
			Calendar cal1 = Calendar.getInstance();
			cal1.setTimeInMillis(0);
			cal1.setTime(checktime1);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTimeInMillis(0);
			cal2.setTime(checktime2);
			long flg = cal2.getTimeInMillis() - cal1.getTimeInMillis();
			if (flg < 0) {
				// //("开始时间不能大于结束时间！");
				return rtn_6;
			}
			if (rangeType == DateType.RANGE_TYPE_y) {
				cal1.set(Calendar.YEAR, cal1.get(Calendar.YEAR) + ymdhms);
				// v_typeMsg = "年";
			} else if (rangeType == DateType.RANGE_TYPE_M) {
				cal1.set(Calendar.MONTH, cal1.get(Calendar.MONTH) + ymdhms);
				// v_typeMsg = "个月";
			} else if (rangeType == DateType.RANGE_TYPE_d) {
				cal1.set(Calendar.DATE, cal1.get(Calendar.DATE) + ymdhms);
				// v_typeMsg = "天";
			} else if (rangeType == DateType.RANGE_TYPE_H) {
				cal1.set(Calendar.HOUR_OF_DAY, cal1.get(Calendar.HOUR_OF_DAY) + ymdhms);
				// v_typeMsg = "小时";
			} else if (rangeType == DateType.RANGE_TYPE_m) {
				cal1.set(Calendar.MINUTE, cal1.get(Calendar.MINUTE) + ymdhms);
				// v_typeMsg = "分钟";
			} else if (rangeType == DateType.RANGE_TYPE_s) {
				cal1.set(Calendar.SECOND, cal1.get(Calendar.SECOND) + ymdhms);
				// v_typeMsg = "秒钟";
			} else {
				// //("类型值不正确,请看下面提示\ny：年，m1：月，d：日，h：时，m2：分，s：秒");
				return rtn_3;
			}
			flg = cal2.getTimeInMillis() - cal1.getTimeInMillis();
			if (flg > 0) {
				// //("开始时间和结束时间相差不能超过" + ymdhms + v_typeMsg + "！");
				return rtn_1;
			} else {
				return rtn_2;
			}
		} catch (Exception e) {
			return rtn_3;
		}

	}

	/**
	 * Calendar 转换为 Date
	 * 
	 * @return date日期
	 */
	public static Date calToDate() {
		// (1) Calendar转化为Date
		Calendar cal = Calendar.getInstance();
		return calToDate(cal);
	}

	/**
	 * Calendar 转换为 Date
	 * 
	 * @param cal
	 *            Calendar日期
	 * @return date日期
	 */
	public static Date calToDate(Calendar cal) {
		// (1) Calendar转化为Date
		Date date = cal.getTime();
		return date;
	}

	/**
	 * Date 转换为 Calendar
	 * 
	 * @return Calendar日期
	 */
	public static Calendar dateToCal() {
		// (2) Date转化为Calendar
		Date date = new Date();
		return dateToCal(date);
	}

	/**
	 * Date 转换为 Calendar
	 * 
	 * @param date
	 *            date日期
	 * @return Calendar日期
	 */
	public static Calendar dateToCal(Date date) {
		// (2) Date转化为Calendar
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	/**
	 * 相差日-秒
	 * 
	 * @param sd
	 *            开始日期
	 * @param ed
	 *            结束日期
	 * @return 返回值为：{天, 时, 分, 秒}
	 */
	public static long[] diffDayToSecond(Date sd, Date ed) {
		Date newSd = new Date(sd.getTime());
		Date newEd = new Date(ed.getTime());
		long diff = newEd.getTime() - newSd.getTime();
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		day = diff / (24 * 60 * 60 * 1000);
		hour = (diff / (60 * 60 * 1000) - day * 24);
		min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
		sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		long[] times = { day, hour, min, sec };
		return times;
	}

	/**
	 * 相差日
	 * 
	 * @param sd
	 *            开始日期
	 * @param ed
	 *            结束日期
	 * @return 相差值
	 */
	public static long diffDay(Date sd, Date ed) {
		Date newSd = new Date(sd.getTime());
		Date newEd = new Date(ed.getTime());
		long diff = newEd.getTime() - newSd.getTime();
		return diff / (24 * 60 * 60 * 1000);
	}

	/**
	 * 加日
	 * 
	 * @param d
	 *            日期
	 * @param day
	 *            天数
	 * @return 计算后的日期
	 */
	public static Date addDay(Date d, int day) {
		Date newD = new Date(d.getTime());
		newD.setTime(newD.getTime() + (long) day * 86400000L);
		return newD;
	}

	/**
	 * 加月
	 * 
	 * @param d
	 *            日期
	 * @param month
	 *            月数
	 * @return 计算后的日期
	 */
	public static Date addMonth(Date d, int month) {
		Date newD = new Date(d.getTime());
		GregorianCalendar gval = new GregorianCalendar();
		gval.setTime(newD);
		gval.add(2, month);
		newD.setTime(gval.getTime().getTime());
		return newD;
	}

	/**
	 * 加年
	 * 
	 * @param d
	 *            日期
	 * @param year
	 *            年数
	 * @return 计算后的日期
	 */
	public static Date addYear(Date d, int year) {
		Date newD = new Date(d.getTime());
		GregorianCalendar gval = new GregorianCalendar();
		gval.setTime(newD);
		gval.add(1, year);
		newD.setTime(gval.getTime().getTime());
		return newD;
	}

	/**
	 * 相差时
	 * 
	 * @param sd
	 *            开始日期
	 * @param ed
	 *            结束日期
	 * @return 相差值
	 */
	public static long diffHour(Date sd, Date ed) {
		Date newSd = new Date(sd.getTime());
		Date newEd = new Date(ed.getTime());
		long diff = newEd.getTime() - newSd.getTime();
		return diff / (60 * 60 * 1000);
	}

	/**
	 * 加小时
	 * 
	 * @param d
	 *            日期
	 * @param hour
	 *            小时数
	 * @return 计算后的日期
	 */
	public static Date addHour(Date d, int hour) {
		Date newD = new Date(d.getTime());
		newD.setTime(newD.getTime() + (long) hour * 3600000L);
		return newD;
	}

	/**
	 * 相差分
	 * 
	 * @param sd
	 *            开始日期
	 * @param ed
	 *            结束日期
	 * @return 相差值
	 */
	public static long diffMinute(Date sd, Date ed) {
		Date newSd = new Date(sd.getTime());
		Date newEd = new Date(ed.getTime());
		long diff = newEd.getTime() - newSd.getTime();
		return diff / (60 * 1000);
	}

	/**
	 * 加分
	 * 
	 * @param d
	 *            日期
	 * @param minute
	 *            分数
	 * @return 计算后的日期
	 */
	public static Date addMinute(Date d, int minute) {
		Date newD = new Date(d.getTime());
		newD.setTime(newD.getTime() + (long) minute * 60000L);
		return newD;
	}

	/**
	 * 相差秒
	 * 
	 * @param sd
	 *            开始日期
	 * @param ed
	 *            结束日期
	 * @return 相差值
	 */
	public static long diffSecond(Date sd, Date ed) {
		Date newSd = new Date(sd.getTime());
		Date newEd = new Date(ed.getTime());
		long diff = newEd.getTime() - newSd.getTime();
		return diff / (1000);
	}

	/**
	 * 加秒
	 * 
	 * @param d
	 *            日期
	 * @param second
	 *            秒数
	 * @return 计算后的日期
	 */
	public static Date addSecond(Date d, int second) {
		Date newD = new Date(d.getTime());
		newD.setTime(newD.getTime() + (long) second * 1000L);
		return newD;
	}

	/**
	 * 加毫秒
	 * 
	 * @param d
	 *            日期
	 * @param minSecond
	 *            毫秒数
	 * @return 计算后的日期
	 */
	public static Date addMinSecond(Date d, int minSecond) {
		Date newD = new Date(d.getTime());
		newD.setTime(newD.getTime() + (long) minSecond * 1L);
		return newD;
	}

	/**
	 * 将元数据前补零，补后的总长度为指定的长度，以字符串的形式返回
	 * 
	 * @param sourceData
	 *            源数据
	 * @param formatLength
	 *            长度
	 * @return 重组后的数据
	 */
	public static String frontCompWithZore(int sourceData, int formatLength) {
		/*
		 * 0 指前面补充零 formatLength 字符总长度为 formatLength d 代表为正数。
		 */
		String newString = String.format("%0" + formatLength + "d", sourceData);
		return newString;
	}
}
