package zj.dwr.bean;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import zj.check.util.CheckUtil;

/**
 * dwr注册类<br>
 * 
 * @version 1.00 （2011/11/08）
 * @author 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public final class DwrSession implements Serializable {
	private static final long serialVersionUID = 1L;
	private transient static final Logger log = Logger.getLogger(DwrSession.class);
	/** Dwr内存注册 **/
	public static final Map<String, ScriptSession> SCRIPT_SESSIONS = Collections.synchronizedMap(new HashMap<String, ScriptSession>());

	/**
	 * 注册session
	 * 
	 * @param key
	 */
	public final void regScriptSession(String key) {
		try {
			WebContext webContext = WebContextFactory.get();
			/** 获取页面长连接session */
			ScriptSession scriptSession = webContext.getScriptSession();
			// WebSession webSession = GlobalOperation.getWebSession(webContext.getSession().getId());
			// if (webSession != null) {
			// User user = webSession.getUser();
			// // 用户工号+长连接标识=scriptSession唯一标识
			// // scriptSession保存内存中
			// MemoryConstant.ssMapScriptSession.put(key, scriptSession);
			// }
			if (CheckUtil.isNotNull(key)) {
				SCRIPT_SESSIONS.put(key, scriptSession);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 错误信息
	 * 
	 * @param errorMessage
	 * @param scriptURL
	 * @param lineNumber
	 */
	public final void writeJsError(String errorMessage, String scriptURL, String lineNumber) {
		if (CheckUtil.isNotNull(scriptURL))
			log.error("scriptURL:" + scriptURL);
		if (CheckUtil.isNotNull(errorMessage))
			log.error("errorMessage:" + errorMessage);
		if (CheckUtil.isNotNull(lineNumber))
			log.error("lineNumber:" + lineNumber);
	}
}
