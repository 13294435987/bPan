package onem.baymax.pan.server.module.file.context;

import java.io.Serializable;
import lombok.Data;
import onem.baymax.pan.server.module.file.entity.BPanFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * 保存单文件的上下文实体
 *
 * @author hujiabin wrote in 2024/5/8 09:03
 */
@Data
public class FileSaveContext implements Serializable {

    /**
     * 文件名称
     */
    private String filename;

    /**
     * 文件唯一标识
     */
    private String identifier;

    /**
     * 文件大小
     */
    private Long totalSize;

    /**
     * 要上传的文件实体
     */
    private MultipartFile file;

    /**
     * 当前登录的用户ID
     */
    private Long userId;

    /**
     * 实体文件记录
     */
    private BPanFile record;

    /**
     * 文件上传的物理路径
     */
    private String realPath;

}

