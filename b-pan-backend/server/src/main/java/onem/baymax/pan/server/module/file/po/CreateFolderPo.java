package onem.baymax.pan.server.module.file.po;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 创建文件夹实体参数
 *
 * @author hujiabin wrote in 2024/3/26 17:35
 */
@ApiModel(value = "创建文件夹参数实体")
@Data
public class CreateFolderPo implements Serializable {

    private static final long serialVersionUID = 5475817231508440546L;

    @ApiModelProperty(value = "加密的父文件夹ID", required = true)
    @NotBlank(message = "父文件夹ID不能为空")
    private String parentId;

    @ApiModelProperty(value = "文件夹名称", required = true)
    @NotBlank(message = "文件夹名称不能为空")
    private String folderName;

}
