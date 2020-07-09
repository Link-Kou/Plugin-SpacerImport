package com.linkkou.spacerimport;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 注解
 * @author lk
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface SpacerPipe {
}
