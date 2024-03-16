package onem.baymax.pan.cache.redis.test;

import onem.baymax.pan.cache.core.constant.CacheConstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * RedisCacheTest
 *
 * @author hujiabin wrote in 2024/3/16 13:19
 */
@SpringBootTest(classes = RedisCacheTest.class)
@SpringBootApplication
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisCacheTest {

    @Resource
    private CacheManager cacheManager;

    @Resource
    private CacheAnnotationTester cacheAnnotationTester;

    /**
     * 简单测试CacheManger的功能以及获取的Cache对象的功能
     */
    @Test
    public void caffeineCacheManagerTest() {
        Cache cache = cacheManager.getCache(CacheConstant.B_PAN_CACHE_NAME);
        assert cache != null;
        cache.put("name", "value");
        String value = cache.get("name", String.class);
        assert "value".equals(value);
    }

    @Test
    public void caffeineCacheAnnotationTest() {
        for (int i = 0; i < 2; i++) {
            assert "hello baymax".equals(cacheAnnotationTester.testCacheable("baymax"));
        }
    }
}
