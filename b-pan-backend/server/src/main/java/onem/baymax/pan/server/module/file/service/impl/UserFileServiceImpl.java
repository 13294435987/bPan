package onem.baymax.pan.server.module.file.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import onem.baymax.pan.server.module.file.context.CreateFolderContext;
import onem.baymax.pan.server.module.file.entity.BPanUserFile;
import onem.baymax.pan.server.module.file.service.IUserFileService;
import onem.baymax.pan.server.module.file.mapper.BPanUserFileMapper;
import org.springframework.stereotype.Service;

/**
 * @author hujiabin
 * @description 针对表【b_pan_user_file(用户文件信息表)】的数据库操作Service实现
 * @createDate 2024-03-14 11:22:44
 */
@Service(value = "userFileService")
public class UserFileServiceImpl extends ServiceImpl<BPanUserFileMapper, BPanUserFile>
        implements IUserFileService {

    @Override
    public Long createFolder(CreateFolderContext createFolderContext) {
        // todo
        return null;
    }
}




