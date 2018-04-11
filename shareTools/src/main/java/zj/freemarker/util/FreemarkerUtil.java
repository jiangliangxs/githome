package zj.freemarker.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Map;

import zj.check.util.CheckUtil;
import zj.freemarker.bean.Freemarker;
import zj.io.util.FileUtil;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * FreemarkerUtil工具类
 * 
 * @version 1.00 （2014.09.15）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class FreemarkerUtil {
	// private transient static final Logger logger = Logger.getLogger(TestXml.class);
	// /**
	// * 取得freemarker模板
	// *
	// * @param name
	// * 模板名
	// * @param path
	// * 模板路径
	// * @return 模板对象
	// * @throws IOException
	// */
	// public static final Template getTemplate(String name) throws IOException {
	// // 通过Freemarker的Configuration读取相应的ftl
	// Configuration cfg = new Configuration(Configuration.getVersion());
	// // // 设定去哪里去读取相应的ftl模板文件
	// cfg.setClassForTemplateLoading(FreemarkerUtil.class, "/ftl/mybatis");
	// // cfg.setServletContextForTemplateLoading(servletContext, path);
	// // cfg.setDirectoryForTemplateLoading(new File("E:/versionManager/sources/java/zj-model/freemarker/freemarker-helloworld/src/main/resources/ftl"));
	// // 在模板文件目录中找到名称为name的文件
	// Template temp = cfg.getTemplate(name);
	// return temp;
	// }

	/**
	 * 取得freemarker模板
	 * 
	 * @param name
	 *            模板名
	 * @param path
	 *            模板路径
	 * @return 模板对象
	 * @throws IOException
	 */
	public static final Template getTemplates(Freemarker freemarker) throws IOException {
		// 通过Freemarker的Configuration读取相应的ftl
		Configuration cfg = new Configuration(Configuration.getVersion());
		// // 设定去哪里去读取相应的ftl模板文件
		// cfg.setClassForTemplateLoading(FreemarkerUtil.class, "/ftl/mybatis");
		// cfg.setServletContextForTemplateLoading(servletContext, path);
		if (freemarker.getFtlPaths() == null) {
			freemarker.setFtlPaths(new String[] { "/ftl" });
		}
		TemplateLoader[] loaders = new TemplateLoader[freemarker.getFtlPaths().length];
		for (int i = 0; i < freemarker.getFtlPaths().length; i++) {
			loaders[i] = new ClassTemplateLoader(FreemarkerUtil.class, freemarker.getFtlPaths()[i]);
		}
		MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);
		cfg.setTemplateLoader(mtl);
		// cfg.setDirectoryForTemplateLoading(new File("E:/versionManager/sources/java/zj-model/freemarker/freemarker-helloworld/src/main/resources/ftl"));
		// 在模板文件目录中找到名称为name的文件
		Template temp = cfg.getTemplate(freemarker.getName());
		return temp;
	}

	/**
	 * 写入文件内容
	 * 
	 * @param freemarker
	 *            模板对象值
	 * 
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static final void writeFile(Freemarker freemarker) throws IOException, TemplateException {
		Map<String, Object> rootMap = freemarker.getRootMap();
		File outFile = freemarker.getOutFile();
		String charsetName = freemarker.getCharsetName();
		boolean append = freemarker.isAppend();
		FileUtil.createFolderOrFile(outFile, false);
		BufferedWriter bufferedWriter = null;
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(outFile, append);
			if (CheckUtil.isNull(charsetName)) {
				charsetName = "UTF-8";
			}
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, charsetName);
			bufferedWriter = new BufferedWriter(outputStreamWriter);
			Template temp = getTemplates(freemarker);
			temp.process(rootMap, bufferedWriter);
			bufferedWriter.flush();
		} finally {
			if (bufferedWriter != null) {
				bufferedWriter.close();
			}
		}
	}

	/**
	 * 打印控制台
	 * 
	 * @param freemarker
	 *            模板对象值
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static final void printConsole(Freemarker freemarker) throws IOException, TemplateException {
		Template temp = getTemplates(freemarker);
		// 通过模板文件输出到相应的流中
		temp.process(freemarker.getRootMap(), new PrintWriter(System.out));
	}
}
