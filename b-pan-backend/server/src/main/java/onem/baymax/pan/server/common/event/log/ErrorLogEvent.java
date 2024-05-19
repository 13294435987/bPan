package onem.baymax.pan.server.common.event.log;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * 错误日志事件
 *
 * @author hujiabin wrote in 2024/5/19 11:11
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class ErrorLogEvent extends ApplicationEvent {

    /**
     * 错误日志的内容
     */
    private String errorMsg;

    /**
     * 当前登录的用户ID
     */
    private Long userId;

    public ErrorLogEvent(Object source, String errorMsg, Long userId) {
        super(source);
        this.errorMsg = errorMsg;
        this.userId = userId;
    }

}
