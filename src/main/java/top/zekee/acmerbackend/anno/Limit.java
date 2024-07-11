package top.zekee.acmerbackend.anno;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Limit {
    long interval() default 60000; // 1min

    int value() default 5;
}
