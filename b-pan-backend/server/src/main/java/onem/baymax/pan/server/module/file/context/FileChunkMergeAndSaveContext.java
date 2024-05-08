package onem.baymax.pan.server.module.file.context;

import java.io.Serializable;
import lombok.Data;
import onem.baymax.pan.server.module.file.entity.BPanFile;

/**
 * 文件分片合并的上下文实体对象
 *
 * @author hujiabin wrote in 2024/5/8 09:04
 */
@Data
public class FileChunkMergeAndSaveContext implements Serializable {

    private static final long serialVersionUID = -3913020166000401092L;

    /**
     * 文件名称
     */
    private String filename;

    /**
     * 文件唯一标识
     */
    private String identifier;

    /**
     * 文件总大小
     */
    private Long totalSize;

    /**
     * 文件的父文件夹ID
     */
    private Long parentId;

    /**
     * 当前登录的用户ID
     */
    private Long userId;

    /**
     * 物理文件记录
     */
    private BPanFile record;

    /**
     * 文件合并之后存储的真实的物理路径
     */
    private String realPath;

}
