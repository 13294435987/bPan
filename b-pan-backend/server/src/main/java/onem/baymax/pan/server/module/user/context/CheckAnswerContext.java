package onem.baymax.pan.server.module.user.context;

import lombok.Data;

import java.io.Serializable;

/**
 * 校验密保答案信息实体
 *
 * @author hujiabin wrote in 2024/3/17 21:31
 */
@Data
public class CheckAnswerContext implements Serializable {

    private static final long serialVersionUID = -947015711857341702L;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 密保问题
     */
    private String question;

    /**
     * 密保答案
     */
    private String answer;

}
