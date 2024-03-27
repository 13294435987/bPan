package onem.baymax.pan.server.module.file.po;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 批量删除文件入参对象实体
 *
 * @author hujiabin wrote in 2024/3/27 23:06
 */
@ApiModel(value = "批量删除文件入参对象实体")
@Data
public class DeleteFilePo implements Serializable {

    private static final long serialVersionUID = 3098611201745909528L;

    @ApiModelProperty(value = "要删除的文件ID，多个使用公用的分隔符分割", required = true)
    @NotBlank(message = "请选择要删除的文件信息")
    private String fileIds;

}
