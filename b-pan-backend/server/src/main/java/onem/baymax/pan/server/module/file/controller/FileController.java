package onem.baymax.pan.server.module.file.controller;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import onem.baymax.pan.core.constant.BPanConstant;
import onem.baymax.pan.core.response.R;
import onem.baymax.pan.core.util.IdUtil;
import onem.baymax.pan.server.common.util.UserIdUtil;
import onem.baymax.pan.server.module.file.constant.FileConstant;
import onem.baymax.pan.server.module.file.context.QueryFileListContext;
import onem.baymax.pan.server.module.file.enums.DelFlagEnum;
import onem.baymax.pan.server.module.file.service.IUserFileService;
import onem.baymax.pan.server.module.file.vo.BPanUserFileVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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

    /**
     * 查询文件列表
     *
     * @param parentId  父文件夹
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
    public R<List<BPanUserFileVo>> list(@NotBlank(message = "父文件夹ID不能为空") @RequestParam(value = "parentId", required = false) String parentId,
                                        @RequestParam(value = "fileTypes", required = false, defaultValue = FileConstant.ALL_FILE_TYPE) String fileTypes) {
        // 加密处理一下
        Long realParentId = IdUtil.decrypt(parentId);

        List<Integer> fileTypeArray = null;

        if (!Objects.equals(FileConstant.ALL_FILE_TYPE, fileTypes)) {
            fileTypeArray = Arrays.stream(StrUtil.split(fileTypes, BPanConstant.COMMON_SEPARATOR))
                    .filter(StringUtils::isNotBlank) // 可选：过滤掉空字符串（根据实际情况决定是否需要）
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
}
