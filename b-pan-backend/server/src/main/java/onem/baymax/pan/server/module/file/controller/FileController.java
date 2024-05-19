package onem.baymax.pan.server.module.file.controller;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import onem.baymax.pan.core.constant.BPanConstant;
import onem.baymax.pan.core.response.R;
import onem.baymax.pan.core.util.IdUtil;
import onem.baymax.pan.server.common.util.UserIdUtil;
import onem.baymax.pan.server.module.file.constant.FileConstant;
import onem.baymax.pan.server.module.file.context.CreateFolderContext;
import onem.baymax.pan.server.module.file.context.DeleteFileContext;
import onem.baymax.pan.server.module.file.context.FileUploadContext;
import onem.baymax.pan.server.module.file.context.QueryFileListContext;
import onem.baymax.pan.server.module.file.context.SecUploadFileContext;
import onem.baymax.pan.server.module.file.context.UpdateFilenameContext;
import onem.baymax.pan.server.module.file.converter.FileConverter;
import onem.baymax.pan.server.module.file.enums.DelFlagEnum;
import onem.baymax.pan.server.module.file.po.CreateFolderPo;
import onem.baymax.pan.server.module.file.po.DeleteFilePo;
import onem.baymax.pan.server.module.file.po.FileUploadPo;
import onem.baymax.pan.server.module.file.po.SecUploadFilePo;
import onem.baymax.pan.server.module.file.po.UpdateFilenamePo;
import onem.baymax.pan.server.module.file.service.IUserFileService;
import onem.baymax.pan.server.module.file.vo.BPanUserFileVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 操作文件接口
 *
 * @author hujiabin wrote in 2024/3/19 17:50
 */
@RestController
@Validated
@Api(tags = "文件模块")
public class FileController {

    @Resource
    private IUserFileService userFileService;

    @Resource
    private FileConverter fileConverter;

    /**
     * 查询文件列表
     *
     * @param parentId 父文件夹
     * @param fileTypes 文件类型
     * @return vo
     */
    @ApiOperation(
            value = "查询文件列表",
            notes = "该接口提供了用户插叙某文件夹下面某些文件类型的文件列表的功能",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @GetMapping("files")
    public R<List<BPanUserFileVo>> list(
            @NotBlank(message = "父文件夹ID不能为空") @RequestParam(value = "parentId", required = false) String parentId,
            @RequestParam(value = "fileTypes", required = false, defaultValue = FileConstant.ALL_FILE_TYPE) String fileTypes) {
        // 加密处理一下
        Long realParentId = IdUtil.decrypt(parentId);

        List<Integer> fileTypeArray = null;

        if (!Objects.equals(FileConstant.ALL_FILE_TYPE, fileTypes)) {
            fileTypeArray = Arrays.stream(StrUtil.split(fileTypes, BPanConstant.COMMON_SEPARATOR))
                    // 可选：过滤掉空字符串（根据实际情况决定是否需要）
                    .filter(StringUtils::isNotBlank)
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());
        }

        QueryFileListContext context = new QueryFileListContext();
        context.setParentId(realParentId);
        context.setFileTypeArray(fileTypeArray);
        context.setUserId(UserIdUtil.get());
        context.setDelFlag(DelFlagEnum.NO.getCode());

        return R.data(userFileService.getFileList(context));
    }

    /**
     * 创建文件夹
     *
     * @param createFolderPo po
     * @return id
     */
    @ApiOperation(
            value = "创建文件夹",
            notes = "该接口提供了创建文件夹的功能",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @PostMapping("file/folder")
    public R<String> createFolder(@Validated @RequestBody CreateFolderPo createFolderPo) {
        CreateFolderContext context = fileConverter.createFolderPo2CreateFolderContext(createFolderPo);
        Long fileId = userFileService.createFolder(context);
        return R.data(IdUtil.encrypt(fileId));
    }

    /**
     * 重命名
     *
     * @param updateFilenamePo po
     * @return r
     */
    @ApiOperation(
            value = "文件重命名",
            notes = "该接口提供了文件重命名的功能",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @PutMapping("file")
    public R<String> updateFilename(@Validated @RequestBody UpdateFilenamePo updateFilenamePo) {
        UpdateFilenameContext context = fileConverter.updateFilenamePo2UpdateFilenameContext(updateFilenamePo);
        userFileService.updateFilename(context);
        return R.success();
    }

    @ApiOperation(
            value = "批量删除文件",
            notes = "该接口提供了批量删除文件的功能",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @DeleteMapping("file")
    public R<String> deleteFile(@Validated @RequestBody DeleteFilePo deleteFilePo) {
        DeleteFileContext context = fileConverter.deleteFilePo2DeleteFileContext(deleteFilePo);

        String fileIds = deleteFilePo.getFileIds();
        List<Long> fileIdList = Arrays.stream(StringUtils.split(fileIds, BPanConstant.COMMON_SEPARATOR)).map(IdUtil::decrypt)
                .collect(Collectors.toList());

        context.setFileIdList(fileIdList);
        userFileService.deleteFile(context);
        return R.success();
    }

    @ApiOperation(
            value = "文件秒传",
            notes = "该接口提供了文件秒传的功能",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @PostMapping("file/sec-upload")
    public R<String> secUpload(@Validated @RequestBody SecUploadFilePo secUploadFilePo) {
        SecUploadFileContext context = fileConverter.secUploadFilePo2SecUploadFileContext(secUploadFilePo);
        boolean result = userFileService.secUpload(context);
        if (result) {
            return R.success();
        }
        return R.fail("文件唯一标识不存在，请手动执行文件上传");
    }

    @ApiOperation(
            value = "单文件上传",
            notes = "该接口提供了单文件上传的功能",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @PostMapping("file/upload")
    public R<String> upload(@Validated FileUploadPo fileUploadPo) {
        FileUploadContext context = fileConverter.fileUploadPo2FileUploadContext(fileUploadPo);
        userFileService.upload(context);
        return R.success();
    }

}
