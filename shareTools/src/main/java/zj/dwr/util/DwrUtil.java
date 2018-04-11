package zj.dwr.util;

import java.util.List;

import org.directwebremoting.ScriptSession;
import org.directwebremoting.proxy.dwr.Util;

import zj.dwr.bean.DwrBean;
import zj.dwr.bean.DwrSession;

/**
 * 工具类<br>
 * 
 * @version 1.00 （2011/11/08）
 * @author 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class DwrUtil {

	/** 向前台推送消息 **/
	public final static <T> void sendAllMessage(DwrBean<T> dwrBean) {
		List<String> funNames = dwrBean.getFunNameList();
		for (String key : DwrSession.SCRIPT_SESSIONS.keySet()) {
			ScriptSession scriptSession = DwrSession.SCRIPT_SESSIONS.get(key);
			if (scriptSession != null) {
				Util util = new Util(scriptSession);
				if (funNames != null && funNames.size() > 0)
					for (String funName : funNames) {
						util.addFunctionCall(funName, dwrBean.getTvalue());
					}
			}
		}
	}

	/** 向前台推送单个用户消息 **/
	public final static <T> void sendMessage(String userId, DwrBean<T> dwrBean) {
		List<String> funNames = dwrBean.getFunNameList();
		ScriptSession scriptSession = DwrSession.SCRIPT_SESSIONS.get(userId);
		if (scriptSession != null) {
			Util util = new Util(scriptSession);
			if (funNames != null && funNames.size() > 0)
				for (String funName : funNames) {
					util.addFunctionCall(funName, dwrBean.getTvalue());
				}
		}
	}
}
