package onem.baymax.pan.cache.redis.test;

import lombok.extern.slf4j.Slf4j;
import onem.baymax.pan.cache.core.constant.CacheConstant;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * CacheAnnotationTester
 *
 * @author hujiabin wrote in 2024/3/16 13:18
 */
@Component
@Slf4j
public class CacheAnnotationTester {

    /**
     * 测试自适应缓存注解
     *
     * @param name name
     * @return "hello " + name
     */
    @Cacheable(cacheNames = CacheConstant.B_PAN_CACHE_NAME, key = "#name", sync = true)
    public String testCacheable(String name) {
        log.info(" param is {}", name);
        return "hello " + name;
    }

}
