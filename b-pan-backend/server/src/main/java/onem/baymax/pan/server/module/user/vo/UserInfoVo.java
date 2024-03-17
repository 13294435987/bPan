package onem.baymax.pan.server.module.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import onem.baymax.pan.web.serializer.IdEncryptSerializer;

import java.io.Serializable;

/**
 * 用户基本信息实体
 *
 * @author hujiabin wrote in 2024/3/17 21:54
 */
@ApiModel(value = "用户基本信息实体")
@Data
public class UserInfoVo implements Serializable {

    private static final long serialVersionUID = 831556981609248699L;

    @ApiModelProperty("用户名称")
    private String username;

    @ApiModelProperty("用户根目录的加密ID")
    @JsonSerialize(using = IdEncryptSerializer.class)
    private Long rootFileId;

    @ApiModelProperty("用户根目录名称")
    private String rootFilename;

}