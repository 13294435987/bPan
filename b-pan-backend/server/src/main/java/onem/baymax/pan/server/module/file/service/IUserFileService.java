package onem.baymax.pan.server.module.file.service;

import onem.baymax.pan.server.module.file.context.CreateFolderContext;
import onem.baymax.pan.server.module.file.context.DeleteFileContext;
import onem.baymax.pan.server.module.file.context.QueryFileListContext;
import onem.baymax.pan.server.module.file.context.SecUploadFileContext;
import onem.baymax.pan.server.module.file.context.UpdateFilenameContext;
import onem.baymax.pan.server.module.file.entity.BPanUserFile;
import com.baomidou.mybatisplus.extension.service.IService;
import onem.baymax.pan.server.module.file.vo.BPanUserFileVo;

import java.util.List;

/**
 * @author hujiabin
 */
public interface IUserFileService extends IService<BPanUserFile> {

    /**
     * 创建文件夹信息
     *
     * @param createFolderContext context
     * @return 文件ID
     */
    Long createFolder(CreateFolderContext createFolderContext);

    /**
     * 查询用户的根文件夹信息
     *
     * @param userId userId
     * @return userFile
     */
    BPanUserFile getUserRootFile(Long userId);

    /**
     * 查询用户的文件列表
     *
     * @param context context
     * @return list
     */
    List<BPanUserFileVo> getFileList(QueryFileListContext context);

    /**
     * 更新文件名称
     *
     * @param context context
     */
    void updateFilename(UpdateFilenameContext context);

    /**
     * 删除文件
     *
     * @param context context
     */
    void deleteFile(DeleteFileContext context);

    /**
     * 文件秒传
     *
     * @param context 上下文对象
     * @return true/false
     */
    boolean secUpload(SecUploadFileContext context);

}
