package onem.baymax.pan.server.module.file.converter;

import onem.baymax.pan.server.module.file.context.CreateFolderContext;
import onem.baymax.pan.server.module.file.po.CreateFolderPo;
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

}
