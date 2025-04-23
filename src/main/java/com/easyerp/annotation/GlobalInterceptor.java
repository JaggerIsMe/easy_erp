package com.easyerp.annotation;

import java.lang.annotation.*;

/**
 * @author Jagger
 * * @date 2025/4/23
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GlobalInterceptor {

    /**
     * 检查登录
     * @return
     */
    boolean checkLogin() default true;


}
