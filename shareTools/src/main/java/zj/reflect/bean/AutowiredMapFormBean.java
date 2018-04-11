package zj.reflect.bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 类名 ：AutowiredMapFormBean<br>
 * 概况 ：自动注解map到属性及方法<br>
 * 
 * @version 1.00 （2014.09.15）
 * @author SHNKCS 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.LOCAL_VARIABLE, ElementType.TYPE, ElementType.FIELD, ElementType.METHOD })
// annotation local_variable,type,method
public @interface AutowiredMapFormBean {
	/**
	 * map中的key
	 * 
	 * @return
	 */
	public String key() default "";
}
