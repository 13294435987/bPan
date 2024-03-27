package onem.baymax.pan.server.module.file.context;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 批量删除文件上下文实体对象
 *
 * @author hujiabin wrote in 2024/3/27 23:11
 */
@Data
public class DeleteFileContext implements Serializable {

    private static final long serialVersionUID = -5040051387091567725L;

    /**
     * 要删除的文件ID集合
     */
    private List<Long> fileIdList;

    /**
     * 当前的登录用户ID
     */
    private Long userId;

}
