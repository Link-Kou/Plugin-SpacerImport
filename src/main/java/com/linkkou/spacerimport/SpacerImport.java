package com.linkkou.spacerimport;

import java.lang.annotation.*;

/**
 * 注解
 * @author lk
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SpacerImport {

	/**
	 * 方法名称
	 * @return
	 */
	String value();

	/**
	 * 服务名称
	 * @return
	 */
	String BeanName() default "";

}
