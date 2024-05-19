package onem.baymax.pan.storage.core;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;
import cn.hutool.core.lang.Assert;
import onem.baymax.pan.cache.core.constant.CacheConstant;
import onem.baymax.pan.core.exception.BPanFrameworkException;
import onem.baymax.pan.storage.core.context.DeleteFileContext;
import onem.baymax.pan.storage.core.context.StoreFileContext;
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

    @Override public void store(StoreFileContext context) throws IOException {
        checkStoreFileContext(context);
        doStore(context);
    }

    /**
     * 执行保存物理文件的动作
     * 下沉到具体的子类去实现
     *
     * @param context context
     */
    protected abstract void doStore(StoreFileContext context) throws IOException;

    @Override public void delete(DeleteFileContext context) throws IOException {
        checkDeleteFileContext(context);
        doDelete(context);
    }

    /**
     * 执行删除物理文件的动作
     * 下沉到子类去实现
     *
     * @param context context
     * @throws IOException 异常
     */
    protected abstract void doDelete(DeleteFileContext context) throws IOException;

    private void checkStoreFileContext(StoreFileContext context) {
        Assert.notBlank(context.getFilename(), "文件名称不能为空");
        Assert.notNull(context.getTotalSize(), "文件的总大小不能为空");
        Assert.notNull(context.getInputStream(), "文件不能为空");
    }

    private void checkDeleteFileContext(DeleteFileContext context) {
        Assert.notEmpty(context.getRealFilePathList(), "要删除的文件路径列表不能为空");
    }

}
