package zj.tree.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import zj.java.util.JavaUtil;
import zj.reflect.util.FieldUtil;

/**
 * 类名 ：TreeUtil<br>
 * 概况 ：树工具类<br>
 * 
 * @version 1.00 （2014.09.15）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class TreeUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 回调取机构父对象
	 * 
	 * @param p_entity
	 * @param p_coll
	 */
	@SuppressWarnings("unchecked")
	public static <T> void callPentity(T p_entity, List<T> p_coll, String p_pentityName){
		if (p_entity == null)
			return;
		p_coll.add(p_entity);
		Object objEntity = null;
		try {
			objEntity = FieldUtil.get(p_entity, p_pentityName, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (objEntity==null){
			return;
		}
		callPentity((T) objEntity, p_coll, p_pentityName);
	}

	/**
	 * 取得树路径
	 * 
	 * @param p_entity
	 */
	public static <T> String entityTextJoin(T p_entity, String text, String p_pentityName){
		return entityTextJoin(p_entity, text, p_pentityName, "\\");
	}

	/**
	 * 取得树路径
	 * 
	 * @param p_entity
	 */
	public static <T> String entityTextJoin(T p_entity, String text, String p_pentityName, String split) {
		List<T> coll = new ArrayList<T>();
		callPentity(p_entity, coll, p_pentityName);
		StringBuffer textJoin = new StringBuffer(200);
		for (int i = coll.size() - 1; i >= 0; i--) {
			T entity = coll.get(i);
			if (!"".equals(textJoin.toString())) {
				textJoin.append(split);
			}
			Object objText = null;
			try {
				objText = FieldUtil.get(entity, text, true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			textJoin.append(JavaUtil.objToStr(objText));
		}
		return textJoin.toString();
	}
}