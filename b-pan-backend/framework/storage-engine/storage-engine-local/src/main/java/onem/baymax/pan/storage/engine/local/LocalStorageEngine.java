package onem.baymax.pan.storage.engine.local;

import java.io.File;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import onem.baymax.pan.core.util.FileUtils;
import onem.baymax.pan.storage.core.AbstractStorageEngine;
import onem.baymax.pan.storage.core.context.DeleteFileContext;
import onem.baymax.pan.storage.core.context.StoreFileContext;
import onem.baymax.pan.storage.engine.config.LocalStorageEngineConfig;
import org.springframework.stereotype.Component;

/**
 * 本地存储实现
 *
 * @author hujiabin wrote in 2024/5/15 23:29
 */
@Component
@RequiredArgsConstructor
public class LocalStorageEngine extends AbstractStorageEngine {

    private final LocalStorageEngineConfig config;

    @Override
    protected void doStore(StoreFileContext context) throws IOException {
        String basePath = config.getRootFilePath();
        String realFilePath = FileUtils.generateStoreFileRealPath(basePath, context.getFilename());
        FileUtils.writeStream2File(context.getInputStream(), new File(realFilePath), context.getTotalSize());
        context.setRealPath(realFilePath);
    }

    @Override protected void doDelete(DeleteFileContext context) throws IOException {
        FileUtils.deleteFiles(context.getRealFilePathList());
    }

}
