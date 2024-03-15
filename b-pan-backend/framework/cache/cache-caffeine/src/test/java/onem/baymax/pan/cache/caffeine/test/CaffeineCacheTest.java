package onem.baymax.pan.cache.caffeine.test;

import cn.hutool.core.lang.Assert;
import onem.baymax.pan.cache.caffeine.config.CaffeineCacheConfig;
import onem.baymax.pan.cache.caffeine.test.instance.CacheAnnotationTester;
import onem.baymax.pan.cache.core.constant.CacheConstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * CaffeineCache单元测试
 *
 * @author hujiabin wrote in 2024/3/15 23:11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CaffeineCacheConfig.class)
public class CaffeineCacheTest {

    @Resource
    private CacheManager cacheManager;

    @Resource
    private CacheAnnotationTester cacheAnnotationTester;

    @Test
    public void caffeineCacheManagerTest() {
        Cache cache = cacheManager.getCache(CacheConstant.B_PAN_CACHE_NAME);
        assert cache != null;
        cache.put("name", "value");
        String value = cache.get("name", String.class);
        Assert.isTrue("value".equals(value));
    }

    @Test
    public void caffeineCacheAnnotationTest() {
        for (int i = 0; i < 2; i++) {
            assert "hello baymax".equals(cacheAnnotationTester.testCacheable("baymax"));
        }
    }
}
