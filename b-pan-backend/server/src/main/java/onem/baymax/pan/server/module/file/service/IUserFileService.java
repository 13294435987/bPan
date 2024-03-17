package onem.baymax.pan.server.module.file.service;

import onem.baymax.pan.server.module.file.context.CreateFolderContext;
import onem.baymax.pan.server.module.file.entity.BPanUserFile;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
