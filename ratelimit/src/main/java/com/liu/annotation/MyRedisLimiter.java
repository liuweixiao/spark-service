package com.liu.annotation;


import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyRedisLimiter {

    //每秒加入桶中的token
    double limitNum() default 10;

    //按入参限流
    String limitParamName() default "";
}
