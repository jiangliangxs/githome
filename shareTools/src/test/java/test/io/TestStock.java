package test.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import zj.check.util.CheckUtil;
import zj.io.util.FileUtil;
import zj.java.util.JavaUtil;

/**
 * 查询股票数据
 * 
 * @author zhangjun
 * 
 */
public class TestStock {
	public String fileName = "C:/Users/Administrator/Desktop/600566.txt";

	@Test
	public void m2() {
		System.out.println(JavaUtil.split("a a   a", " ").length);
	}

	@Test
	public void m1() {
		try {
			// String minMoney = "23.81";
			List<String> values = new ArrayList<String>();
			FileUtil.readLines(values, new File(fileName), "GBK");
			for (String value : values) {
				if (value.indexOf(":") > -1) {
					String[] valueAry = JavaUtil.split(value, "\\s+");
					valueAry = value.split("\\s+");
					System.out.println(valueAry.length);
					// String[] valueAry = JavaUtil.split(value, " ");
					// List<String> valueList = new ArrayList<String>();
					// for (String svalue : valueAry){
					// if (CheckUtil.isNotNull(svalue)){
					// valueList.add(svalue);
					// }
					// }
					// System.out.println(valueList + "-" + valueList.size());
					// // if (minMoney.equals(valueAry[1])) {
					// // System.out.println("时间" + valueAry[0]);
					// // }
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
