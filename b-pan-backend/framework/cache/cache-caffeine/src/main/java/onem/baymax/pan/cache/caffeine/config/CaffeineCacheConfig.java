package onem.baymax.pan.cache.caffeine.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import onem.baymax.pan.cache.core.constant.CacheConstant;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * CaffeineCacheConfig
 *
 * @author hujiabin wrote in 2024/3/15 13:47
 */
@SpringBootConfiguration
@EnableCaching
@Slf4j
public class CaffeineCacheConfig {

    @Resource
    private CaffeineCacheProperties caffeineCacheProperties;

    @Bean
    public CacheManager caffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(CacheConstant.B_PAN_CACHE_NAME);
        cacheManager.setAllowNullValues(caffeineCacheProperties.getAllowNullValue());
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
                .initialCapacity(caffeineCacheProperties.getInitCacheCapacity())
                .maximumSize(caffeineCacheProperties.getMaxCacheCapacity());
        cacheManager.setCaffeine(caffeine);
        log.info("the caffeine cache manager is loaded successfully!");
        return cacheManager;
    }
}
