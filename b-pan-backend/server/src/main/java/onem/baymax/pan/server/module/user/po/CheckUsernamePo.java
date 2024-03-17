package onem.baymax.pan.server.module.user.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 用户忘记密码-校验用户名参数
 *
 * @author hujiabin wrote in 2024/3/17 21:49
 */
@ApiModel(value = "用户忘记密码-校验用户名参数")
@Data
public class CheckUsernamePo implements Serializable {

    private static final long serialVersionUID = 1795641889740242870L;

    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message = "用户名称不能为空")
    @Pattern(regexp = "^[0-9A-Za-z]{6,16}$", message = "请输入6-16位只包含数字和字母的用户名")
    private String username;

}
