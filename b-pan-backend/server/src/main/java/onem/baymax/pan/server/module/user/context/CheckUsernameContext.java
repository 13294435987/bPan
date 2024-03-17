package onem.baymax.pan.server.module.user.context;

import lombok.Data;

import java.io.Serializable;

/**
 * 校验用户名称信息实体
 *
 * @author hujiabin wrote in 2024/3/17 21:33
 */
@Data
public class CheckUsernameContext implements Serializable {

    private static final long serialVersionUID = -7117844539768126736L;

    /**
     * 用户名称
     */
    private String username;

}
