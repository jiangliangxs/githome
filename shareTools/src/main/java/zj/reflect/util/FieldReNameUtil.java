package zj.reflect.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import zj.reflect.bean.FieldReNameAnn;

/**
 * 方法工具类
 * 
 * @author zhangjun
 * 
 */
public class FieldReNameUtil implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 获取属性重命名
	 * 
	 * @param objInstance
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Map<String, Object>> getReNameMap(Object objInstance) throws Exception {
		Map<String, Map<String, Object>> maps = new HashMap<String, Map<String, Object>>();
		Map<Field, Object> fieldsMap = FieldUtil.getFieldsMap(objInstance, true);
		for (Field field : fieldsMap.keySet()) {
			if (field.isAnnotationPresent(FieldReNameAnn.class)) {
				// 获取注解
				FieldReNameAnn ann = field.getAnnotation(FieldReNameAnn.class);
				if (ann != null) {
					// 属性集合
					Map<String, Object> map = new HashMap<String, Object>();
					// 设置属性名
					map.put("name", ann.name() == null || ann.name().equals("") ? field.getName() : ann.name());
					// 设置属性对象
					map.put("field", field);
					// 设置属性值
					map.put("value", FieldUtil.get(objInstance, field.getName(), true));
					// map的key
					String key = ann.key() == null || ann.key().equals("") ? field.getName() : ann.key();
					// 设置注解key
					maps.put(key, map);
				}
			}
		}
		return maps;
	}
}
