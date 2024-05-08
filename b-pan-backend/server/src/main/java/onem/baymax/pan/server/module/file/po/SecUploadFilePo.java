package onem.baymax.pan.server.module.file.po;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * 文件秒传的接口参数实体对象
 *
 * @author hujiabin wrote in 2024/5/7 12:12
 */
@ApiModel("秒传文件接口参数对象实体")
@Data
public class SecUploadFilePo implements Serializable {

    private static final long serialVersionUID = 5638531322051664526L;

    @ApiModelProperty(value = "秒传的父文件夹ID", required = true)
    @NotBlank(message = "父文件夹ID不能为空")
    private String parentId;

    @ApiModelProperty(value = "文件名称", required = true)
    @NotBlank(message = "文件名称不能为空")
    private String filename;

    @ApiModelProperty(value = "文件的唯一标识", required = true)
    @NotBlank(message = "文件的唯一标识不能为空")
    private String identifier;

}
