package onem.baymax.pan.server.module.file.context;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建文件夹上下文实体
 *
 * @author hujiabin wrote in 2024/3/17 19:02
 */
@Data
public class CreateFolderContext implements Serializable {

    private static final long serialVersionUID = -861882709652125971L;

    /**
     * 父文件夹ID
     */
    private Long parentId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 文件夹名称
     */
    private String folderName;

}

