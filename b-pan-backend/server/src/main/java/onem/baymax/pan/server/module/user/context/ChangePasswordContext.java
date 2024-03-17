package onem.baymax.pan.server.module.user.context;

import lombok.Data;
import onem.baymax.pan.server.module.user.entity.BPanUser;

import java.io.Serializable;

/**
 * 用户在线修改密码上下文信息实体
 *
 * @author hujiabin wrote in 2024/3/17 21:30
 */
@Data
public class ChangePasswordContext implements Serializable {

    /**
     * 当前登录的用户ID
     */
    private Long userId;

    /**
     * 旧密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 当前登录用户的实体信息
     */
    private BPanUser entity;

}
