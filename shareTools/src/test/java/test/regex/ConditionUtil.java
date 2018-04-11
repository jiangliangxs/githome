//package utils.regex;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import com.sinosoft.sysframework.reference.DBManager;
//import com.atlife.callout.actiondef.dto.domain.Cc_action_defineDto;
//import com.atlife.callout.actiondef.dto.domain.Cc_action_definefieldnoteDto;
//import com.atlife.callout.log.LogUtil;
//import com.atlife.callout.question.dto.domain.Cc_action_naireresultDto;
//import com.atlife.callout.question.dto.domain.Cc_action_questionnaireDto;
//
///** 筛选条件工具类 */
//public class ConditionUtil {
//	private Cc_action_defineDto cc_action_defineDto;
//	private Cc_action_naireresultDto naireresultDto;
//
//	public ConditionUtil(Cc_action_defineDto cc_action_defineDto) {
//		this.cc_action_defineDto = cc_action_defineDto;
//	}
//
//	public ConditionUtil(ActionData actionData,
//			Cc_action_questionnaireDto questionnaireDto,
//			Cc_action_naireresultDto naireresultDto) throws Exception {
//		this.cc_action_defineDto = actionData.getCc_action_dataDto()
//				.getCc_action_defineDto();
//		this.naireresultDto = naireresultDto;
//	}
//
//	/** 将条件替换成数据库认识的形式 */
//	public String getCondition(String desc) throws Exception {
//
//		/*String[] conditions = desc.split("and");
//		StringBuffer sb = new StringBuffer();
//		for (int i = 0; i < conditions.length; i++) {
//			String[] cond = conditions[i].split("or");
//			for (int j = 0; j < cond.length; j++) {
//				if (cond[j].trim().equals("")) {
//					continue;
//				}
//				String value = this.replaceMinCond(cond[j]);
//				sb.append(" " + value + " or");
//			}
//			if (sb.length() > 3) {
//				sb.delete(sb.length() - 3, sb.length());
//			}
//			sb.append(" and ");
//		}
//		if (sb.length() > 4) {
//			sb.delete(sb.length() - 4, sb.length());
//		}*/
//
//		String str = this.fillStringByArgs(desc);
//		System.out.println("sb.................." + str);
//		return str;
//	}
//
//	/** 替换去掉and和or后的条件 */
//	private String replaceMinCond(String minCondition) throws Exception {
//		String newCondition = minCondition.replaceAll("\\(", "").replaceAll(
//				"\\)", "");
//
//		String[] conditions = this.split(newCondition);
//		String left = replaceOne(conditions[1], conditions[2]);
//		String right = replaceOne(conditions[2], conditions[1]);
//		String replacedValue = left + conditions[0] + right;
//		newCondition = newCondition.replaceAll("\\{", "\\\\{").replaceAll(
//				"\\}", "\\\\}").trim();
//		return minCondition.replaceAll(newCondition, replacedValue);
//	}
//
//	/** 替换运算符一边的表达式 */
//	private String replaceOne(String exp, String value) throws Exception {
//		if (exp.indexOf("{") == -1) {
//			return exp;
//		}
//		if (exp.indexOf("{问题") != -1) {
//			String displayNoS = exp.replaceAll("\\{问题", "").replaceAll("\\}",
//					"");
//			int displayNo = Integer.parseInt(displayNoS);
//			String[] answers = naireresultDto.getResultValue(displayNo);
//			if (answers != null && answers.length >= 1) {
//				return "'" + answers[0] + "'";
//			} else {
//				return "''";
//			}
//
//		}
//		String type = this.getType(value);
//		String name = exp.replaceAll("\\{", "").replaceAll("\\}", "");
//		Cc_action_definefieldnoteDto dto = cc_action_defineDto
//				.getDefinefieldnoteByName(name);
//		return replace(dto, type);
//	}
//
//	/** 判断值是什么类型,只有系统字段需要判断 */
//	private String getType(String value) throws Exception {
//		if (value.indexOf("{") == -1) {
//			return "";
//		}
//		if (value.startsWith("{问题")) {
//			return "";
//		}
//		value = value.replaceAll("\\{", "").replaceAll("\\}", "");
//		Cc_action_definefieldnoteDto dto = cc_action_defineDto
//				.getDefinefieldnoteByName(value);
//		if (dto.getDefineFieldType().equals("system")) {
//			return dto.getDefineTable();
//		} else {
//			return "";
//		}
//	}
//
//	private String replace(Cc_action_definefieldnoteDto dto, String type) {
//		if (dto.getDefineFieldType().equals("system")) {
//			return dto.getCode();
//		}
//		String value = dto.getDefineTable() + "." + dto.getCode();
//		if (!dto.getDbType().equals("date") || type == null || type.equals("")) {
//			return value;
//		}
//
//		if (type.equals("year")) {
//			return "to_number(to_char(" + value + " ,'yy'))";
//		} else if (type.equals("month")) {
//			return "to_number(to_char(" + value + " ,'mm'))";
//		} else if (type.equals("day")) {
//			return "to_number(to_char(" + value + " ,'dd'))";
//		} else if (type.equals("hour")) {
//			return "to_number(to_char(" + value + " ,'hh'))";
//		} else if (type.equals("second")) {
//			return "to_number(to_char(" + value + " ,'ss'))";
//		} else if (type.equals("week")) {
//			return "to_number(to_char(" + value + " ,'ww'))";
//		} else if (type.equals("now")) {
//			return value;
//		}
//		return value;
//	}
//
//	/** 用运算符拆开 */
//	private String[] split(String minCondition) {
//		String[] cons = minCondition.split("<>");
//		cons = minCondition.split("<>");
//		if (cons.length == 2) {
//			return addOp(cons, " <> ");
//		}
//		cons = minCondition.split("!=");
//		if (cons.length == 2) {
//			return addOp(cons, " != ");
//		}
//
//		cons = minCondition.split(">=");
//		if (cons.length == 2) {
//			return addOp(cons, " >= ");
//		}
//		cons = minCondition.split("<=");
//		if (cons.length == 2) {
//			return addOp(cons, " <= ");
//		}
//		cons = minCondition.split("=");
//		if (cons.length == 2) {
//			return addOp(cons, " = ");
//		}
//		cons = minCondition.split(">");
//		if (cons.length == 2) {
//			return addOp(cons, " > ");
//		}
//		cons = minCondition.split("<");
//		if (cons.length == 2) {
//			return addOp(cons, " < ");
//		}
//		cons = minCondition.split(" is ");
//		if (cons.length == 2) {
//			return addOp(cons, " is ");
//		}
//		cons = minCondition.split(" like ");
//		if (cons.length == 2) {
//			return addOp(cons, " like ");
//		}
//		throw new CallOutException("缺少运算符,不能解析条件:" + minCondition);
//	}
//
//	/** 将操作符一起放在返回值里 */
//	private String[] addOp(String[] s, String op) {
//		String[] strs = new String[3];
//		strs[0] = op;
//		strs[1] = s[0].trim();
//		strs[2] = s[1].trim();
//		return strs;
//	}
//    /**
//     * {"内容"}替换成表字段信息{问题}替换成SQL
//     * @param str
//     * @return
//     */
//	private String fillStringByArgs(String str) {
//		try {
//			String pile = "\\{([\u4E00-\u9FA5]*[0-9]*[a-z]*)([A-Z]*)\\}";
//			Matcher m = Pattern.compile(pile).matcher(str);
//			while (m.find()) {
//				String name = m.group(1);
//				String value="";
//				String rep = m.group().replaceAll("\\{", "\\\\{");
//				rep = rep.replaceAll("\\}", "\\\\}");
//				if (name.indexOf("问题") != -1) {
//					String displayNoS = name.replaceAll("问题", "");
//					int displayNo = Integer.parseInt(displayNoS);
//					String[] answers = naireresultDto.getResultValue(displayNo);
//					if (answers != null && answers.length >= 1) {
//						value = "'" + answers[0] + "'";
//					} else {
//						value = "''";
//					}
//				}else{
//					dto = cc_action_defineDto.getDefinefieldnoteByName(name);
//					 value = dto.getDefineTable() + "." + dto.getCode();
//				}
//				str = str.replaceFirst(rep, value);
//			}
//		} catch (Exception e) {
//			//  自动生成 catch 块
//			e.printStackTrace();
//			return " 1=1 ";
//
//		}
//		return str;
//	}
//
//	public static void main(String[] args) throws Exception {
//		String exp = " (to_date({申请日期},'yyyy-mm-dd')-{出生日期})/365 >  60 and {机构} = '8680'";
//		// String exp = " ({机构} = '8680' or {机构} = '8685')";
//		// DBManager dbManager = new DBManager();
//		// dbManager.open("platformDataSource");
//		// DBCc_action_define actionDefine = new DBCc_action_define(dbManager);
//		// Cc_action_defineDto cc_action_defineDto =
//		// actionDefine.findByPrimaryKey("e976eac9-9b58-4169-bc09-fc2347363eae");
//		// LogUtil.debug(cc_action_defineDto);
//		// ConditionUtil util = new ConditionUtil(cc_action_defineDto);
//		//
//		// String value = util.getCondition(exp);
//		// LogUtil.debug(value);
//		ActionData actionData = new ActionData(
//				"B0C131752B2B908EE0430A641133908E");
//		Cc_action_naireresultDto naireresultDto = new Cc_action_naireresultDto();
//		boolean a = ConditionUtil
//				.isMatch(actionData, null, naireresultDto, exp);
//		System.out.println(a);
//	}
//
//	/**
//	 * 判断是否符合条件
//	 *
//	 * @throws Exception
//	 */
//	public static boolean isMatch(ActionData actionData,
//			Cc_action_questionnaireDto questionnaireDto,
//			Cc_action_naireresultDto naireresultDto, String condition)
//			throws Exception {
//		try {
//			ConditionUtil util = new ConditionUtil(actionData,
//					questionnaireDto, naireresultDto);
//			condition = util.getCondition(condition);
//			if (condition == null || condition.trim().equals("")) {
//				return true;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return true;
//		}
//		DBManager dbManager = new DBManager();
//		try {
//			dbManager.open("platformDataSource");
//			String sql = "select count(*) "
//					+ "  from cc_action_data, cc_action_customerinfo, cc_action_definefield"
//					+ " where cc_action_data.dataguid = cc_action_definefield.dataguid"
//					+ "   and cc_action_data.unioncustomerid ="
//					+ "       cc_action_customerinfo.unioncustomerid"
//					+ "   and cc_action_data.dataguid = '"
//					+ actionData.getCc_action_dataDto().getDataGuid()
//					+ "' and (" + condition + ")";
//			LogUtil.debug(sql);
//			int num = dbManager.getCount(sql);
//			return num != 0;
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			return false;
//		} finally {
//			dbManager.close();
//		}
//	}
//
//	// 判断｛｝（）把它们全替换掉，然后得到的结果再把（）返回去。
//	private String replaceMinCondition(String minCondition) throws Exception {
//		String[] conditions = this.split(minCondition);
//		String leftName = conditions[1].replaceAll("\\{", "").replaceAll("\\}",
//				"");
//		// 如果左边有(并且没有).第一种情况＋＋＋＋＋＋＋＋
//		if (leftName.indexOf("(") != -1 && leftName.indexOf(")") == -1) {
//			String leftparentheses = leftName.substring(leftName.indexOf("("),
//					leftName.lastIndexOf("(") + 1);
//			leftName = leftName.replaceAll("\\(", "");
//			Cc_action_definefieldnoteDto dto = cc_action_defineDto
//					.getDefinefieldnoteByName(leftName);
//			// 右边是｛｝的形式
//			if (conditions[2].indexOf("{") != -1) {
//				String rightName = conditions[2].replaceAll("\\{", "")
//						.replaceAll("\\}", "");
//				// 右边有（且没有）。第一种情况－－－－－－－－－－－－－
//				if (rightName.indexOf("(") != -1
//						&& rightName.indexOf(")") == -1) {
//					String rightparentheses = rightName.substring(rightName
//							.indexOf("("), rightName.lastIndexOf("(") + 1);
//					rightName = rightName.replaceAll("\\(", "");
//					Cc_action_definefieldnoteDto rightDto = cc_action_defineDto
//							.getDefinefieldnoteByName(rightName);
//					String left = this.replace(dto, rightDto.getDefineTable());
//					// 把(添加上
//					left = leftparentheses + left;
//					rightName = rightparentheses + rightDto.getCode();
//					return left + " " + conditions[0] + " " + rightName;
//				}
//				// 右边有）且没有（.第二种情况－－－－－－－－－－－－－
//				if (rightName.indexOf(")") != -1
//						&& rightName.indexOf("(") == -1) {
//					String rightparentheses = rightName.substring(rightName
//							.indexOf(")"), rightName.lastIndexOf(")") + 1);
//					rightName = rightName.replaceAll("\\)", "");
//					Cc_action_definefieldnoteDto rightDto = cc_action_defineDto
//							.getDefinefieldnoteByName(rightName);
//					String left = this.replace(dto, rightDto.getDefineTable());
//					// 把)添加上
//					left = leftparentheses + left;
//					rightName = rightDto.getCode() + rightparentheses;
//					return left + " " + conditions[0] + " " + rightName;
//				}
//				// 右边有（且有）。第三种情况－－－－－－－－－－
//				if (rightName.indexOf("(") != -1
//						&& rightName.indexOf(")") != -1) {
//					String rightparenthesesleft = rightName.substring(rightName
//							.indexOf("("), rightName.lastIndexOf("(") + 1);
//					String rightparenthesesright = rightName.substring(
//							rightName.indexOf(")"),
//							rightName.lastIndexOf(")") + 1);
//					rightName = rightName.replaceAll("\\(", "").replaceAll(
//							"\\)", "");
//					Cc_action_definefieldnoteDto rightDto = cc_action_defineDto
//							.getDefinefieldnoteByName(rightName);
//					String left = this.replace(dto, rightDto.getDefineTable());
//					// 把()添加上
//					left = leftparentheses + left;
//					rightName = rightparenthesesleft + rightDto.getCode()
//							+ rightparenthesesright;
//					return left + " " + conditions[0] + " " + rightName;
//				}
//				// 右边没有（）。第四种情况－－－－－－－－－
//				else {
//					Cc_action_definefieldnoteDto rightDto = cc_action_defineDto
//							.getDefinefieldnoteByName(rightName);
//					String left = this.replace(dto, rightDto.getDefineTable());
//					left = leftparentheses + left;
//					rightName = rightDto.getCode();
//					return left + " " + conditions[0] + " " + rightName;
//				}
//			}
//			// 右边不是｛｝形式的.
//			else {
//				String left = this.replace(dto, null);
//				left = leftparentheses + left;
//				return left + " " + conditions[0] + " " + conditions[2];
//			}
//		}
//		// 如果左边有)并且没有(.第二种情况＋＋＋＋＋＋＋＋＋
//		if (leftName.indexOf(")") != -1 && leftName.indexOf("(") == -1) {
//			String leftNameright = leftName.substring(leftName.indexOf(")"),
//					leftName.lastIndexOf(")") + 1);
//			leftName = leftName.replaceAll("\\)", "");
//			Cc_action_definefieldnoteDto dto = cc_action_defineDto
//					.getDefinefieldnoteByName(leftName);
//			// 右边是｛｝的形式
//			if (conditions[2].indexOf("{") != -1) {
//				String rightName = conditions[2].replaceAll("\\{", "")
//						.replaceAll("\\}", "");
//				// 右边有（但没有）。第一种情况－－－－－－－
//				if (rightName.indexOf("(") != -1
//						&& rightName.indexOf(")") == -1) {
//					String rightNameleft = rightName.substring(rightName
//							.indexOf("("), rightName.lastIndexOf("(") + 1);
//					rightName = rightName.replaceAll("\\(", "");
//					Cc_action_definefieldnoteDto rightDto = cc_action_defineDto
//							.getDefinefieldnoteByName(rightName);
//					String left = this.replace(dto, rightDto.getDefineTable());
//					// 把(添加上
//					left = left + leftNameright;
//					rightName = rightNameleft + rightDto.getCode();
//					return left + " " + conditions[0] + " " + rightName;
//				}
//				// 右边有）但没有（。第二种情况－－－－－－－－－－
//				if (rightName.indexOf(")") != -1
//						&& rightName.indexOf("(") == -1) {
//					String rightNameright = rightName.substring(rightName
//							.indexOf(")"), rightName.lastIndexOf(")") + 1);
//					rightName = rightName.replaceAll("\\)", "");
//					Cc_action_definefieldnoteDto rightDto = cc_action_defineDto
//							.getDefinefieldnoteByName(rightName);
//					String left = this.replace(dto, rightDto.getDefineTable());
//					// 把)添加上
//					left = left + leftNameright;
//					rightName = rightDto.getCode() + rightNameright;
//					return left + " " + conditions[0] + " " + rightName;
//				}
//				// 右边有(并且有）。第三种情况－－－－－－－－－－
//				if (rightName.indexOf("(") != -1
//						&& rightName.indexOf(")") != -1) {
//					String rightNameleft = rightName.substring(rightName
//							.indexOf("("), rightName.lastIndexOf("(") + 1);
//					String rightNameright = rightName.substring(rightName
//							.indexOf(")"), rightName.lastIndexOf(")") + 1);
//					rightName = rightName.replaceAll("\\(", "").replaceAll(
//							"\\)", "");
//					Cc_action_definefieldnoteDto rightDto = cc_action_defineDto
//							.getDefinefieldnoteByName(rightName);
//					String left = this.replace(dto, rightDto.getDefineTable());
//					// 把()添加上
//					left = left + leftNameright;
//					rightName = rightNameleft + rightDto.getCode()
//							+ rightNameright;
//					return left + " " + conditions[0] + " " + rightName;
//				}
//				// 右边没有（）。第四种情况－－－－－－－－－－
//				else {
//					Cc_action_definefieldnoteDto rightDto = cc_action_defineDto
//							.getDefinefieldnoteByName(rightName);
//					String left = this.replace(dto, rightDto.getDefineTable());
//					left = left + leftNameright;
//					rightName = rightDto.getCode();
//					return left + " " + conditions[0] + " " + rightName;
//				}
//			}
//			// 右边不是｛｝形式的.
//			else {
//				String left = this.replace(dto, null);
//				left = left + leftNameright;
//				return left + " " + conditions[0] + " " + conditions[2];
//			}
//		}
//		// 如果左边有(并且也有)。第三种情况＋＋＋＋＋＋＋＋＋＋＋
//		if (leftName.indexOf("(") != -1 && leftName.indexOf(")") != -1) {
//			String leftNameleft = leftName.substring(leftName.indexOf("("),
//					leftName.lastIndexOf("(") + 1);
//			String leftNameright = leftName.substring(leftName.indexOf(")"),
//					leftName.lastIndexOf(")") + 1);
//			leftName = leftName.replaceAll("\\(", "").replaceAll("\\)", "");
//			Cc_action_definefieldnoteDto dto = cc_action_defineDto
//					.getDefinefieldnoteByName(leftName);
//			// 右边是｛｝的形式
//			if (conditions[2].indexOf("{") != -1) {
//				String rightName = conditions[2].replaceAll("\\{", "")
//						.replaceAll("\\}", "");
//				// 右边有（但没有）。第一种情况－－－－－－－－
//				if (rightName.indexOf("(") != -1
//						&& rightName.indexOf(")") == -1) {
//					String rightNameleft = rightName.substring(rightName
//							.indexOf("("), rightName.lastIndexOf("(") + 1);
//					rightName = rightName.replaceAll("\\(", "");
//					Cc_action_definefieldnoteDto rightDto = cc_action_defineDto
//							.getDefinefieldnoteByName(rightName);
//					String left = this.replace(dto, rightDto.getDefineTable());
//					// 把()添加上
//					left = leftNameleft + left + leftNameright;
//					rightName = rightNameleft + rightDto.getCode();
//					return left + " " + conditions[0] + " " + rightName;
//				}
//				// 右边有）但没有（。第二种情况－－－－－－－－－－
//				if (rightName.indexOf("(") == -1
//						&& rightName.indexOf(")") != -1) {
//					String rightNameright = rightName.substring(rightName
//							.indexOf(")"), rightName.lastIndexOf(")") + 1);
//					rightName = rightName.replaceAll("\\)", "");
//					Cc_action_definefieldnoteDto rightDto = cc_action_defineDto
//							.getDefinefieldnoteByName(rightName);
//					String left = this.replace(dto, rightDto.getDefineTable());
//					// 把()添加上
//					left = leftNameleft + left + leftNameright;
//					rightName = rightDto.getCode() + rightNameright;
//					return left + " " + conditions[0] + " " + rightName;
//				}
//				// 右边有（并且也有）。第三种情况－－－－－－－－
//				if (rightName.indexOf("(") != -1
//						&& rightName.indexOf(")") != -1) {
//					String rightNameleft = rightName.substring(rightName
//							.indexOf("("), rightName.lastIndexOf("(") + 1);
//					String rightNameright = rightName.substring(rightName
//							.indexOf(")"), rightName.lastIndexOf(")") + 1);
//					rightName = rightName.replaceAll("\\(", "").replaceAll(
//							"\\)", "");
//					Cc_action_definefieldnoteDto rightDto = cc_action_defineDto
//							.getDefinefieldnoteByName(rightName);
//					String left = this.replace(dto, rightDto.getDefineTable());
//					// 把()添加上
//					left = leftNameleft + left + leftNameright;
//					rightName = rightNameleft + rightDto.getCode()
//							+ rightNameright;
//					return left + " " + conditions[0] + " " + rightName;
//				}
//				// 右边没有（）。第四种情况－－－－－－－－－－－
//				else {
//					Cc_action_definefieldnoteDto rightDto = cc_action_defineDto
//							.getDefinefieldnoteByName(rightName);
//					String left = this.replace(dto, rightDto.getDefineTable());
//					// 把()添加上
//					left = leftNameleft + left + leftNameright;
//					return left + " " + conditions[0] + " "
//							+ rightDto.getCode();
//				}
//			}
//			// 右边不是｛｝形式的.
//			else {
//				String left = this.replace(dto, null);
//				left = leftNameleft + left + leftNameright;
//				return left + " " + conditions[0] + " " + conditions[2];
//			}
//		}
//		// 如果左边没有（也没有）.第四种情况＋＋＋＋＋＋＋＋
//		else {
//			Cc_action_definefieldnoteDto dto = cc_action_defineDto
//					.getDefinefieldnoteByName(leftName);
//			// 右边是｛｝的形式
//			if (conditions[2].indexOf("{") != -1) {
//				String rightName = conditions[2].replaceAll("\\{", "")
//						.replaceAll("\\}", "");
//				// 右边有（但没有）。第一种情况－－－－－－－－－
//				if (rightName.indexOf("(") != -1
//						&& rightName.indexOf(")") == -1) {
//					String rightNameleft = rightName.substring(rightName
//							.indexOf("("), rightName.lastIndexOf("(") + 1);
//					rightName = rightName.replaceAll("\\(", "");
//					Cc_action_definefieldnoteDto rightDto = cc_action_defineDto
//							.getDefinefieldnoteByName(rightName);
//					String left = this.replace(dto, rightDto.getDefineTable());
//					rightName = rightNameleft + rightDto.getCode();
//					return left + " " + conditions[0] + " " + rightName;
//				}
//				// 右边有）但没有（。第二种情况－－－－－－－－－－－
//				if (rightName.indexOf(")") != -1
//						&& rightName.indexOf("(") == -1) {
//					String rightNameright = rightName.substring(rightName
//							.indexOf(")"), rightName.lastIndexOf(")") + 1);
//					rightName = rightName.replaceAll("\\)", "");
//					Cc_action_definefieldnoteDto rightDto = cc_action_defineDto
//							.getDefinefieldnoteByName(rightName);
//					String left = this.replace(dto, rightDto.getDefineTable());
//					rightName = rightDto.getCode() + rightNameright;
//					return left + " " + conditions[0] + " " + rightName;
//				}
//				// 右边有（）。第三种情况－－－－－－－－－－
//				if (rightName.indexOf("(") != -1
//						&& rightName.indexOf(")") != -1) {
//					String rightNameleft = rightName.substring(rightName
//							.indexOf("("), rightName.lastIndexOf("(") + 1);
//					String rightNameright = rightName.substring(rightName
//							.indexOf(")"), rightName.lastIndexOf(")") + 1);
//					rightName = rightName.replaceAll("\\(", "").replaceAll(
//							"\\)", "");
//					Cc_action_definefieldnoteDto rightDto = cc_action_defineDto
//							.getDefinefieldnoteByName(rightName);
//					String left = this.replace(dto, rightDto.getDefineTable());
//					rightName = rightNameleft + rightDto.getCode()
//							+ rightNameright;
//					return left + " " + conditions[0] + " " + rightName;
//				}
//				// 右边没有（）。第四种情况－－－－－－－
//				else {
//					Cc_action_definefieldnoteDto rightDto = cc_action_defineDto
//							.getDefinefieldnoteByName(rightName);
//					String left = this.replace(dto, rightDto.getDefineTable());
//					return left + " " + conditions[0] + " "
//							+ rightDto.getCode();
//				}
//			}
//			// 右边不是｛｝形式的.
//			else {
//				String left = this.replace(dto, null);
//				return left + " " + conditions[0] + " " + conditions[2];
//			}
//		}
//	}
//
//}
