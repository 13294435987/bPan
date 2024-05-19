package onem.baymax.pan.storage.core;

import java.io.IOException;
import onem.baymax.pan.storage.core.context.DeleteFileContext;
import onem.baymax.pan.storage.core.context.StoreFileContext;

/**
 * 存储引擎接口
 *
 * @author hujiabin wrote in 2024/5/15 23:19
 */
public interface StorageEngine {

    /**
     * 存储物理文件
     *
     * @param context context
     * @throws IOException 异常
     */
    void store(StoreFileContext context) throws IOException;

    /**
     * 删除物理文件
     *
     * @param context context
     * @throws IOException 异常
     */
    void delete(DeleteFileContext context) throws IOException;

}
