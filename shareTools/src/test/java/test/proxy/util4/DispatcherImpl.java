/*
 * 张军项目模板
 * Copyright (c) 2011张军版权所有 All Rights Reserved.
 * 版权制作
 *   ver 1.0 : 2011.11.08 首次版本
 */
package test.proxy.util4;
import net.sf.cglib.proxy.Dispatcher;
/**
 * 系统名：张军项目模板<br>
 * 类名 ：DispatcherImpl<br>
 * 概况 ：DispatcherImpl类<br>
 *
 * @version 1.00 （2011/11/08）
 * @author SHNKCS 张军 {@link <a
 *         href=http://user.qzone.qq.com/360901061>张军QQ空间</a>}
 */
public class DispatcherImpl implements Dispatcher{

	@Override
	public Object loadObject() throws Exception {
		System.out.println("DispatcherImpl...");
		return null;
	}

}
