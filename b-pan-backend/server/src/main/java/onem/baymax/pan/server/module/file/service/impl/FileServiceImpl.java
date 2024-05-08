package onem.baymax.pan.server.module.file.service.impl;

import java.util.List;
import java.util.Objects;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import onem.baymax.pan.server.module.file.context.FileChunkMergeAndSaveContext;
import onem.baymax.pan.server.module.file.context.FileSaveContext;
import onem.baymax.pan.server.module.file.context.QueryRealFileListContext;
import onem.baymax.pan.server.module.file.entity.BPanFile;
import onem.baymax.pan.server.module.file.service.IFileService;
import onem.baymax.pan.server.module.file.mapper.BPanFileMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author hujiabin
 */
@Service
public class FileServiceImpl extends ServiceImpl<BPanFileMapper, BPanFile>
        implements IFileService {

    @Override public List<BPanFile> getFileList(QueryRealFileListContext context) {
        Long userId = context.getUserId();
        String identifier = context.getIdentifier();
        LambdaQueryWrapper<BPanFile> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(userId), BPanFile::getCreateUser, userId);
        queryWrapper.eq(StringUtils.isNotBlank(identifier), BPanFile::getIdentifier, identifier);
        return list(queryWrapper);
    }

    @Override public void saveFile(FileSaveContext context) {

    }

    @Override public void mergeFileChunkAndSaveFile(FileChunkMergeAndSaveContext context) {

    }

}




