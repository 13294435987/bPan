package onem.baymax.pan.server.module.file.context;

import java.io.Serializable;
import lombok.Data;
import onem.baymax.pan.server.module.file.entity.BPanFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * 单文件上传的上下文实体
 *
 * @author hujiabin wrote in 2024/5/18 12:37
 */
@Data
public class FileUploadContext implements Serializable {

    private static final long serialVersionUID = 3046407826281125463L;

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
     * 文件的父文件夹ID
     */
    private Long parentId;

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

}

