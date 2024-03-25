package onem.baymax.pan.server.module.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import onem.baymax.pan.core.constant.BPanConstant;
import onem.baymax.pan.core.exception.BPanBusinessException;
import onem.baymax.pan.core.util.IdUtil;
import onem.baymax.pan.server.module.file.constant.FileConstant;
import onem.baymax.pan.server.module.file.context.CreateFolderContext;
import onem.baymax.pan.server.module.file.context.QueryFileListContext;
import onem.baymax.pan.server.module.file.entity.BPanUserFile;
import onem.baymax.pan.server.module.file.enums.DelFlagEnum;
import onem.baymax.pan.server.module.file.enums.FolderFlagEnum;
import onem.baymax.pan.server.module.file.service.IUserFileService;
import onem.baymax.pan.server.module.file.mapper.BPanUserFileMapper;
import onem.baymax.pan.server.module.file.vo.BPanUserFileVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
        return saveUserFile(createFolderContext.getParentId(),
                createFolderContext.getFolderName(),
                FolderFlagEnum.YES,
                null,
                null,
                createFolderContext.getUserId(),
                null);
    }

    @Override
    public BPanUserFile getUserRootFile(Long userId) {
        QueryWrapper<BPanUserFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("parent_id", FileConstant.TOP_PARENT_ID);
        queryWrapper.eq("del_flag", DelFlagEnum.NO.getCode());
        queryWrapper.eq("folder_flag", FolderFlagEnum.YES.getCode());
        return getOne(queryWrapper);
    }

    @Override
    public List<BPanUserFileVo> getFileList(QueryFileListContext context) {
        return baseMapper.selectFileList(context);
    }


    private Long saveUserFile(Long parentId,
                              String filename,
                              FolderFlagEnum folderFlagEnum,
                              Integer fileType,
                              Long realFileId,
                              Long userId,
                              String fileSizeDesc) {
        // 保存用户文件的映射记录
        BPanUserFile entity = assemblePanUserFile(parentId, userId, filename, folderFlagEnum, fileType, realFileId, fileSizeDesc);
        if (!save((entity))) {
            throw new BPanBusinessException("保存文件信息失败");
        }
        return entity.getFileId();
    }

    private BPanUserFile assemblePanUserFile(Long parentId, Long userId, String filename, FolderFlagEnum folderFlagEnum,
                                             Integer fileType, Long realFileId, String fileSizeDesc) {
        // 用户文件映射关系实体转化
        // 构建并填充实体
        BPanUserFile entity = new BPanUserFile();

        entity.setFileId(IdUtil.get());
        entity.setUserId(userId);
        entity.setParentId(parentId);
        entity.setRealFileId(realFileId);
        entity.setFilename(filename);
        entity.setFolderFlag(folderFlagEnum.getCode());
        entity.setFileSizeDesc(fileSizeDesc);
        entity.setFileType(fileType);
        entity.setDelFlag(DelFlagEnum.NO.getCode());
        entity.setCreateUser(userId);
        entity.setCreateTime(new Date());
        entity.setUpdateUser(userId);
        entity.setUpdateTime(new Date());

        // 处理文件命名一致的问题
        handleDuplicateFilename(entity);

        return entity;
    }

    private void handleDuplicateFilename(BPanUserFile entity) {
        /* 处理用户重复名称
         * 如果同一文件夹下面有文件名称重复
         * 按照系统级规则重命名文件
         */
        String filename = entity.getFilename(),
                newFilenameWithoutSuffix,
                newFilenameSuffix;
        int newFilenamePointPosition = filename.lastIndexOf(BPanConstant.POINT_STR);
        if (newFilenamePointPosition == BPanConstant.MINUS_ONE_INT) {
            newFilenameWithoutSuffix = filename;
            newFilenameSuffix = StringUtils.EMPTY;
        } else {
            newFilenameWithoutSuffix = filename.substring(BPanConstant.ZERO_INT, newFilenamePointPosition);
            newFilenameSuffix = filename.replace(newFilenameWithoutSuffix, StringUtils.EMPTY);
        }

        // 查找同名文件数量
        int count = getDuplicateFilename(entity, newFilenameWithoutSuffix);

        if (count == 0) {
            return;
        }

        String newFilename = assembleNewFilename(newFilenameWithoutSuffix, count, newFilenameSuffix);
        entity.setFilename(newFilename);
    }

    private int getDuplicateFilename(BPanUserFile entity, String newFilenameWithoutSuffix) {
        QueryWrapper<BPanUserFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", entity.getParentId());
        queryWrapper.eq("folder_flag", entity.getFolderFlag());
        queryWrapper.eq("user_id", entity.getUserId());
        queryWrapper.eq("del_flag", DelFlagEnum.NO.getCode());
        queryWrapper.likeLeft("filename", newFilenameWithoutSuffix);
        return count(queryWrapper);
    }

    private String assembleNewFilename(String newFilenameWithoutSuffix, int count, String newFilenameSuffix) {
        /*
         * 拼装新文件名称
         * 拼装规则参考操作系统重复文件名称的重命名规范
         */
        return newFilenameWithoutSuffix +
                FileConstant.CN_LEFT_PARENTHESES_STR +
                count +
                FileConstant.CN_RIGHT_PARENTHESES_STR +
                newFilenameSuffix;
    }
}




