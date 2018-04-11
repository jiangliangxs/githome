/*
 * 张军项目模板
 * Copyright (c) 2011张军版权所有 All Rights Reserved.
 * 版权制作
 *   ver 1.0 : 2011.11.08 首次版本
 */
package test.proxy.util4;

import net.sf.cglib.proxy.LazyLoader;
/**
 * 系统名：张军项目模板<br>
 * 类名 ：LazyLoaderImpl<br>
 * 概况 ：LazyLoaderImpl类<br>
 *
 * @version 1.00 （2011/11/08）
 * @author SHNKCS 张军 {@link <a
 *         href=http://user.qzone.qq.com/360901061>张军QQ空间</a>}
 */
public class LazyLoaderImpl implements LazyLoader{

	@Override
	public Object loadObject() throws Exception {
		System.out.println("LazyLoaderImpl...");
		return null;
	}

}
