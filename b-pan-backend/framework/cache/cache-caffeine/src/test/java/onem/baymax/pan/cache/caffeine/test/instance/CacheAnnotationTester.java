package onem.baymax.pan.cache.caffeine.test.instance;

import lombok.extern.slf4j.Slf4j;
import onem.baymax.pan.cache.core.constant.CacheConstant;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * CacheAnnotationTester
 *
 * @author hujiabin wrote in 2024/3/15 22:53
 */
@Component
@Slf4j
public class CacheAnnotationTester {


    /**
     * testCacheable
     *
     * @param name name
     * @return "hello " + name
     */
    @Cacheable(cacheNames = CacheConstant.B_PAN_CACHE_NAME, key = "#name", sync = true)
    public String testCacheable(String name) {
        log.info("param is {}", name);
        return "hello " + name;
    }
}
