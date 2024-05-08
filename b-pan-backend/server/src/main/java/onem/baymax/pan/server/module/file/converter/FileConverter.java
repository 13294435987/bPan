package onem.baymax.pan.server.module.file.converter;

import onem.baymax.pan.server.module.file.context.CreateFolderContext;
import onem.baymax.pan.server.module.file.context.DeleteFileContext;
import onem.baymax.pan.server.module.file.context.SecUploadFileContext;
import onem.baymax.pan.server.module.file.context.UpdateFilenameContext;
import onem.baymax.pan.server.module.file.po.CreateFolderPo;
import onem.baymax.pan.server.module.file.po.DeleteFilePo;
import onem.baymax.pan.server.module.file.po.SecUploadFilePo;
import onem.baymax.pan.server.module.file.po.UpdateFilenamePo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 文件模块实体转化工具类
 *
 * @author hujiabin wrote in 2024/3/26 17:36
 */
@Mapper(componentModel = "spring")
public interface FileConverter {

    /**
     * 创建文件参数转换为创建文件上下文对象
     *
     * @param createFolderPo po
     * @return context
     */
    @Mapping(target = "parentId", expression = "java(onem.baymax.pan.core.util.IdUtil.decrypt(createFolderPo.getParentId()))")
    @Mapping(target = "userId", expression = "java(onem.baymax.pan.server.common.util.UserIdUtil.get())")
    CreateFolderContext createFolderPo2CreateFolderContext(CreateFolderPo createFolderPo);

    @Mapping(target = "fileId", expression = "java(onem.baymax.pan.core.util.IdUtil.decrypt(updateFilenamePo.getFileId()))")
    @Mapping(target = "userId", expression = "java(onem.baymax.pan.server.common.util.UserIdUtil.get())")
    UpdateFilenameContext updateFilenamePo2UpdateFilenameContext(UpdateFilenamePo updateFilenamePo);

    @Mapping(target = "userId", expression = "java(onem.baymax.pan.server.common.util.UserIdUtil.get())")
    DeleteFileContext deleteFilePo2DeleteFileContext(DeleteFilePo deleteFilePo);

    @Mapping(target = "parentId", expression = "java(onem.baymax.pan.core.utils.IdUtil.decrypt(secUploadFilePo.getParentId()))")
    @Mapping(target = "userId", expression = "java(onem.baymax.pan.server.common.utils.UserIdUtil.get())")
    SecUploadFileContext secUploadFilePo2SecUploadFileContext(SecUploadFilePo secUploadFilePo);

}
