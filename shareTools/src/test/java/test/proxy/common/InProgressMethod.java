package test.proxy.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.LOCAL_VARIABLE, ElementType.TYPE, ElementType.METHOD})
//annotation local_variable,type,method
public @interface InProgressMethod {
    public String excuteMethod() default "doExcute";
}
