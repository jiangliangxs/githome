package test.word;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import sun.misc.BASE64Encoder;
import zj.date.util.DateUtil;
import zj.java.util.JavaUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 测试word生成工具
 * 
 * @version 1.00 （2014.09.15）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class TestWordUtil {
	public static void main(String[] args) {
		String templateDir = TestWordUtil.class.getResource("/").getFile() + "generate.doc";
		System.out.println(templateDir);
	}

	@Test
	public void dataSource() {
		AccountBalanceExportPage page = null;
		List<AccountBalanceExportPage> pages = new ArrayList<AccountBalanceExportPage>();
		String[] currentDate = JavaUtil.split(DateUtil.dateParse(new Date(), "yyyy-MM-dd"), "-");
		String signFile = this.getClass().getResource("sign.png").getFile();
		System.out.println("图片地址：" + signFile);
		for (int m = 1; m < 10; m++) {
			page = new AccountBalanceExportPage();
			page.setCustomerName("客户名称" + m);
			page.setFundAccountNo("基金账号" + m);
			page.setDeadline(DateUtil.dateParse(new Date(), "yyyy-MM-dd"));
			page.setFundCode("基金代码" + m);
			page.setFundName("ABC" + "基金名称" + "资产管理产品" + m);
			page.setLatestMarketValue("最新市值" + m);
			page.setShareBalance("份额余额" + m);
			page.setTradeAccountNo("交易账号" + m);
			page.setWebsite("网点" + m);
			page.setYear(currentDate[0]);
			page.setMonth(currentDate[1]);
			page.setDay(currentDate[2]);
			page.setUnitWorth("净值(从txt文件中取得)" + m);
			page.setSignDir(getImageStr(signFile));
			pages.add(page);
		}
		createDoc(pages, this.getClass().getResource("").getFile() + "generate.doc");
	}

	public void createDoc(List<AccountBalanceExportPage> balances, String balanceExpDir) {
		Configuration configuration = new Configuration(Configuration.getVersion());
		configuration.setDefaultEncoding("utf-8");
		// configuration.setClassForTemplateLoading(this.getClass(), "/word/freemaker/fm");
		String templateDir = this.getClass().getResource("").getFile();
		try {
			configuration.setDirectoryForTemplateLoading(new File(templateDir));
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("balances", balances);
			Template t = null;
			t = configuration.getTemplate("balance.ftl");
			t.setEncoding("utf-8");
			File destPath = new File(balanceExpDir);
			if (!destPath.getParentFile().exists()) {
				destPath.getParentFile().mkdirs();
			}
			File outFile = new File(balanceExpDir);
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
			t.process(dataMap, out);
			if (outFile.exists() && outFile.length() > 0) {
				System.out.println("生成word成功:" + balanceExpDir);
			} else {
				System.out.println("生成word失败");
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getImageStr(String imgFile) {  
        InputStream in = null;  
        byte[] data = null;  
        try {  
            in = new FileInputStream(imgFile);  
            data = new byte[in.available()];  
            in.read(data);  
            in.close();  
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        BASE64Encoder encoder = new BASE64Encoder();  
        return encoder.encode(data);  
    }
}
