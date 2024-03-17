package onem.baymax.pan.server.common.annotation;

import java.lang.annotation.*;

/**
 * 该注解主要影响那些不需要登录的接口<p>
 * 标注该注解的方法会自动屏蔽统一的登录拦截校验逻辑
 *
 * @author hujiabin wrote in 2024/3/17 20:40
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface LoginIgnore {
}
