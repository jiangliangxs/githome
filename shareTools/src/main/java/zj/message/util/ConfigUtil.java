package zj.message.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import zj.java.util.JavaUtil;

/**
 * 资源文件工具类<br>
 * 
 * @version 1.00 （2011.12.02）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public final class ConfigUtil implements Serializable {
	private transient static final Logger log = Logger.getLogger(ConfigUtil.class);
	private static final long serialVersionUID = 1L;

	/**
	 * 根据配置文件的名字获取配置文件的值
	 * 
	 * @param path
	 *            配置文件路径
	 * @param key
	 *            配置文件信息的Key
	 * @return
	 */
	public static String getConfig(String path, String key) {
		// 根据配置文件的key获取配置文件的值
		String ret = null;
		InputStream in = null;
		try {
			key = JavaUtil.trim(key);
			path = JavaUtil.trim(path);
			// Hotdeploy的场合，XML再次导入
			// File file = ResourceUtil.getResourceAsFile(path);
			//
			// InputStream in = FileInputStreamUtil.create(file);
			in = openStream(getResourceNoException(path, null, Thread.currentThread().getContextClassLoader()));
			if (in == null)
				return key;
			// 配置文件内容解析
			Properties prop = new Properties();
			prop.load(in);
			// 根据配置文件的key获取配置文件的值
			ret = prop.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
			ret = key;
		}finally{
			try {
				if (in!=null){
					in.close();					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// if (ret == null) {
		// LogUtil.error("ConfigUtil类中出错[张军提示]:" + path + "." + key);
		// ret = key;
		// }
		return ret;
	}

	public static InputStream openStream(URL url) {
		try {
			if (url == null)
				return null;
			URLConnection connection = url.openConnection();
			connection.setUseCaches(false);
			return connection.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static URL getResourceNoException(String path, String extension, ClassLoader loader) {
		if (path == null || loader == null) {
			return null;
		} else {
			path = getResourcePath(path, extension);
			return loader.getResource(path);
		}
	}

	public static String getResourcePath(String path, String extension) {
		if (extension == null)
			return path;
		extension = "." + extension;
		if (path.endsWith(extension))
			return path;
		else
			return path.replace('.', '/') + extension;
	}

	/**
	 * 取得配置文件所有key和value值
	 * 
	 * @param paths
	 * @return
	 */
	public static Map<String, String> getConstantKeyValues(Set<String> paths) {
		// 根据配置文件的key和value的值
		Map<String, String> keyValues = new HashMap<String, String>();
		for (String path : paths) {
			try {
				InputStream in = ConfigUtil.openStream(ConfigUtil.getResourceNoException(path, null, Thread.currentThread().getContextClassLoader()));
				if (in == null)
					continue;
				// 配置文件内容解析
				Properties prop = new Properties();
				prop.load(in);
				// 根据配置文件的key获取配置文件的值
				Set<Map.Entry<Object, Object>> set = prop.entrySet();
				for (Map.Entry<Object, Object> map : set) {
					String key = JavaUtil.trim(JavaUtil.objToStr(map.getKey()));
					if (key == null || key.equals("")) {
						continue;
					}
					String value = JavaUtil.objToStr(map.getValue());
					keyValues.put(key, value);
				}
			} catch (Exception e) {
				log.error("获取配置文件[" + path + "]错误:" + e.getMessage());
			}
		}
		return keyValues;
	}
}
