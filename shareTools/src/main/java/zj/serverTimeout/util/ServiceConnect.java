package zj.serverTimeout.util;

import zj.serverTimeout.bean.InArgs_MyRunner;
import zj.serverTimeout.bean.InArgs_MyTimer;
import zj.serverTimeout.bean.OutArgs;
import zj.serverTimeout.bean.TimeOutException;
import zj.serverTimeout.bean.TimerActionInterf;
import zj.serverTimeout.bean.TimerI;

/**
 * 类名 ：ServiceConnect<br>
 * 概况 ：接口连接使用的工具类<br>
 * 
 * @version 1.00 （2014.09.15）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class ServiceConnect {
	/**
	 * 超时调用方法
	 * @param clazz
	 * @param methodName
	 * @param outTime
	 * @return
	 * @throws TimeOutException
	 */
	public static Object getServiceValue(Object clazz, String methodName, long outTime) throws TimeOutException {
		return getServiceValue(clazz, methodName, null, outTime, "处理响应超时");
	}

	/**
	 * 超时调用方法
	 * @param clazz
	 * @param methodName
	 * @param args
	 * @param outTime
	 * @return
	 * @throws TimeOutException
	 */
	public static Object getServiceValue(Object clazz, String methodName, Object[] args, long outTime) throws TimeOutException {
		return getServiceValue(clazz, methodName, args, outTime, "处理响应超时");
	}

	/**
	 * 超时调用方法
	 * @param clazz
	 * @param methodName
	 * @param args
	 * @param outTime
	 * @param timerI
	 * @return
	 * @throws TimeOutException
	 */
	public static Object getServiceValue(Object clazz, String methodName, Object[] args, long outTime, TimerI timerI) throws TimeOutException {
		return getServiceValue(clazz, methodName, args, outTime, "处理响应超时", timerI);
	}

	/**
	 * 超时调用方法
	 * @param clazz
	 * @param methodName
	 * @param args
	 * @param outTime
	 * @param errMsg
	 * @return
	 * @throws TimeOutException
	 */
	public static Object getServiceValue(Object clazz, String methodName, Object[] args, long outTime, String errMsg) throws TimeOutException {
		return getServiceValue(clazz, methodName, args, outTime, errMsg, null);
	}

	/**
	 * 超时调用方法
	 * @param clazz
	 * @param methodName
	 * @param args
	 * @param outTime
	 * @param errMsg
	 * @param timerI
	 * @return
	 * @throws TimeOutException
	 */
	public static Object getServiceValue(Object clazz, String methodName, Object[] args, long outTime, String errMsg, TimerI timerI) throws TimeOutException {
		InArgs_MyRunner inargs_myrunner = new InArgs_MyRunner(clazz, methodName, args);
		InArgs_MyTimer inargs_mytimer = new InArgs_MyTimer(outTime);
		OutArgs outargs = new OutArgs();
		TimerActionInterf tifa = new TimerActionInterf(inargs_myrunner, inargs_mytimer, outargs, true);
		tifa.execute(timerI);
		while (true) {
			if (outargs.isPrograme()) {
				if (outargs.isComplete()) {
					// System.out.println("请求成功");
					// String [] str = (String [])outargs.getResult();
					return outargs.getResult();
				} else {
					// System.out.println("请求超时");
					throw new TimeOutException(errMsg);
				}
				// break;
			}
			// 加上此句就可以
			// System.out.println("hello...");
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
