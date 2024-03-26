package onem.baymax.pan.server.module.file.po;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 文件重命名参数对象
 *
 * @author hujiabin wrote in 2024/3/26 18:02
 */
@Data
@ApiModel(value = "文件重命名参数对象")
public class UpdateFilenamePo implements Serializable {

    private static final long serialVersionUID = -8138754986668154124L;

    @ApiModelProperty(value = "更新的文件ID", required = true)
    @NotBlank(message = "更新的文件ID不能为空")
    private String fileId;

    @ApiModelProperty(value = "新的文件名称", required = true)
    @NotBlank(message = "新的文件名称不能为空")
    private String newFilename;

}
