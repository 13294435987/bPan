package onem.baymax.pan.storage.core;

import javax.annotation.Resource;
import java.util.Objects;
import onem.baymax.pan.cache.core.constant.CacheConstant;
import onem.baymax.pan.core.exception.BPanFrameworkException;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

/**
 * 存储引擎抽象类
 *
 * @author hujiabin wrote in 2024/5/15 23:20
 */
public abstract class AbstractStorageEngine implements StorageEngine {

    @Resource
    private CacheManager cacheManager;

    /**
     * 公用的获取缓存的方法
     *
     * @return {@link Cache}
     */
    protected Cache getCache() {
        if (Objects.isNull(cacheManager)) {
            throw new BPanFrameworkException("The cacheManager is null!");
        }
        return cacheManager.getCache(CacheConstant.B_PAN_CACHE_NAME);
    }

}
