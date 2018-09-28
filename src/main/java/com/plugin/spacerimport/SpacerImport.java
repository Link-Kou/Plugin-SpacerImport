package com.plugin.spacerimport;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SpacerImport {

	/**
	 * 方法名称
	 * @return
	 */
	String ServiceClassMethods();

	/**
	 * 服务名称
	 * @return
	 */
	String BeanName() default "";

}
