package test.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test4 {
	 /**
     * {"内容"}替换成表字段信息{问题}替换成SQL
     * @param str
     * @return
     */
	private static String fillStringByArgs(String str) {
		try {
			//[\u4E00-\u9FA5]*[0-9]*[a-z]*)([A-Z]*
			String pile = "\\{([\u4E00-\u9FA5]*[0-9a-zA-Z_-]*)\\}";
			Matcher m = Pattern.compile(pile).matcher(str);
			System.out.println("groupCount:"+m.groupCount());
			while (m.find()) {
				String name = m.group(1);
				System.out.println("name:"+name);
			}
		} catch (Exception e) {
			//  自动生成 catch 块
			e.printStackTrace();
			return " 1=1 ";

		}
		return str;
	}
    public static void main(String[] args){
    	String sql="select from  atd_holiday where scids in({中国人}) and hrids({beginDate})  in {中国人} and valid_date between ({beginDate}) and $endDate$";
    	fillStringByArgs(sql);

    }
}
