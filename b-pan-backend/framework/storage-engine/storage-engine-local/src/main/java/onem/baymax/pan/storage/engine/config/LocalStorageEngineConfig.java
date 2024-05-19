package onem.baymax.pan.storage.engine.config;

import lombok.Data;
import onem.baymax.pan.core.util.FileUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 本地存储引擎配置
 *
 * @author hujiabin wrote in 2024/5/18 16:18
 */
@Component
@ConfigurationProperties(prefix = "onem.baymax.pan.storage.engine.local")
@Data
public class LocalStorageEngineConfig {

    /**
     * 实际存放路径的前缀
     */
    private String rootFilePath = FileUtils.generateDefaultStoreFileRealPath();

    /**
     * 实际存放文件分片的路径的前缀
     */
    private String rootFileChunkPath = FileUtils.generateDefaultStoreFileChunkRealPath();

}
