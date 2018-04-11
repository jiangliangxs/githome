package test.date;

import zj.date.util.DateUtil;

public class TUtil {
	public static void main(String[] args) {
		String loginDateDBStr = DateUtil.dateParse(null, "yyyy-MM-dd");
		System.out.println("loginDateDBStr:"+loginDateDBStr+",");
		
	}
}
