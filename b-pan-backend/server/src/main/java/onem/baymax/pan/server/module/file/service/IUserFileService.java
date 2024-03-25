package onem.baymax.pan.server.module.file.service;

import onem.baymax.pan.server.module.file.context.CreateFolderContext;
import onem.baymax.pan.server.module.file.context.QueryFileListContext;
import onem.baymax.pan.server.module.file.entity.BPanUserFile;
import com.baomidou.mybatisplus.extension.service.IService;
import onem.baymax.pan.server.module.file.vo.BPanUserFileVo;

import java.util.List;

/**
 * @author hujiabin
 * @description 针对表【b_pan_user_file(用户文件信息表)】的数据库操作Service
 * @createDate 2024-03-14 11:22:44
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
}
