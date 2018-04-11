//Copyright Sun Microsystems Inc. 2004 - 2005.

package test.proxy.util2;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target( { FIELD })
@Retention(RUNTIME)
public @interface Resource {
	// String name() default "";
	// Class<?> type() default java.lang.Object.class;
	// enum AuthenticationType {
	// CONTAINER, APPLICATION
	// }
	// AuthenticationType authenticationType() default
	// AuthenticationType.CONTAINER;
	// boolean shareable() default true;
	// String mappedName() default "";
	// String description() default "";

}
