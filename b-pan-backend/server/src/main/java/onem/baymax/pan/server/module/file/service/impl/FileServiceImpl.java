package onem.baymax.pan.server.module.file.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onem.baymax.pan.core.exception.BPanBusinessException;
import onem.baymax.pan.core.util.FileUtils;
import onem.baymax.pan.core.util.IdUtil;
import onem.baymax.pan.server.common.event.log.ErrorLogEvent;
import onem.baymax.pan.server.module.file.context.FileChunkMergeAndSaveContext;
import onem.baymax.pan.server.module.file.context.FileSaveContext;
import onem.baymax.pan.server.module.file.context.QueryRealFileListContext;
import onem.baymax.pan.storage.core.context.DeleteFileContext;
import onem.baymax.pan.storage.core.context.StoreFileContext;
import onem.baymax.pan.server.module.file.entity.BPanFile;
import onem.baymax.pan.server.module.file.service.IFileService;
import onem.baymax.pan.server.module.file.mapper.BPanFileMapper;
import onem.baymax.pan.storage.core.StorageEngine;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @author hujiabin
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FileServiceImpl extends ServiceImpl<BPanFileMapper, BPanFile> implements IFileService, ApplicationContextAware {

    private final StorageEngine storageEngine;

    private ApplicationContext applicationContext;

    @Override public List<BPanFile> getFileList(QueryRealFileListContext context) {
        Long userId = context.getUserId();
        String identifier = context.getIdentifier();
        LambdaQueryWrapper<BPanFile> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(userId), BPanFile::getCreateUser, userId);
        queryWrapper.eq(StringUtils.isNotBlank(identifier), BPanFile::getIdentifier, identifier);
        return list(queryWrapper);
    }

    @Override public void saveFile(FileSaveContext context) {
        storeMultipartFile(context);
        BPanFile record = doSaveFile(context.getFilename(),
                context.getRealPath(),
                context.getTotalSize(),
                context.getIdentifier(),
                context.getUserId());
        context.setRecord(record);
    }

    @Override public void mergeFileChunkAndSaveFile(FileChunkMergeAndSaveContext context) {

    }

    private void storeMultipartFile(FileSaveContext context) {
        try {
            StoreFileContext storeFileContext = new StoreFileContext();
            storeFileContext.setInputStream(context.getFile().getInputStream());
            storeFileContext.setFilename(context.getFilename());
            storeFileContext.setTotalSize(context.getTotalSize());
            storageEngine.store(storeFileContext);
            context.setRealPath(storeFileContext.getRealPath());
        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new BPanBusinessException("文件上传失败");
        }
    }

    private BPanFile doSaveFile(String filename, String realPath, Long totalSize, String identifier, Long userId) {
        // 拼装实体对象
        BPanFile record = assembleBpanFile(filename, realPath, totalSize, identifier, userId);
        if (!save(record)) {
            try {
                DeleteFileContext deleteFileContext = new DeleteFileContext();
                deleteFileContext.setRealFilePathList(Lists.newArrayList(realPath));
                storageEngine.delete(deleteFileContext);
            } catch (IOException e) {
                log.error("文件物理删除失败，请执行手动删除！文件路径: " + realPath, e);
                ErrorLogEvent errorLogEvent = new ErrorLogEvent(this, "文件物理删除失败，请执行手动删除！文件路径: " + realPath, userId);
                applicationContext.publishEvent(errorLogEvent);
            }
        }
        return record;
    }

    private BPanFile assembleBpanFile(String filename, String realPath, Long totalSize, String identifier, Long userId) {
        BPanFile record = new BPanFile();

        record.setFileId(IdUtil.get());
        record.setFilename(filename);
        record.setRealPath(realPath);
        record.setFileSize(String.valueOf(totalSize));
        record.setFileSizeDesc(FileUtils.byteCountToDisplaySize(totalSize));
        record.setFileSuffix(FileUtils.getFileSuffix(filename));
        record.setIdentifier(identifier);
        record.setCreateUser(userId);
        record.setCreateTime(new Date());

        return record;
    }

    @Override public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}




