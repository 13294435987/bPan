package onem.baymax.pan.server.module.file.service;

import java.util.List;
import onem.baymax.pan.server.module.file.context.FileChunkMergeAndSaveContext;
import onem.baymax.pan.server.module.file.context.FileSaveContext;
import onem.baymax.pan.server.module.file.context.QueryRealFileListContext;
import onem.baymax.pan.server.module.file.entity.BPanFile;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author hujiabin
 */
public interface IFileService extends IService<BPanFile> {

    /**
     * 根据条件查询用户的实际文件列表
     *
     * @param context context
     * @return  list
     */
    List<BPanFile> getFileList(QueryRealFileListContext context);

    /**
     * 上传单文件并保存实体记录
     *
     * @param context context
     */
    void saveFile(FileSaveContext context);

    /**
     * 合并物理文件并保存物理文件记录
     *
     * @param context context
     */
    void mergeFileChunkAndSaveFile(FileChunkMergeAndSaveContext context);

}
