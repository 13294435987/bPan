package onem.baymax.pan.server.common.listener.log;

import java.util.Date;
import lombok.RequiredArgsConstructor;
import onem.baymax.pan.core.util.IdUtil;
import onem.baymax.pan.server.common.event.log.ErrorLogEvent;
import onem.baymax.pan.server.module.log.entity.BPanErrorLog;
import onem.baymax.pan.server.module.log.service.BPanErrorLogService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 系统错误日志监听器
 *
 * @author hujiabin wrote in 2024/5/19 11:20
 */
@Component
@RequiredArgsConstructor
public class ErrorLogEventListener {

    private final BPanErrorLogService errorLogService;

    /**
     * 监听系统错误日志事件，并保存到数据库中
     *
     * @param event 系统错误日志事件
     */
    @EventListener(ErrorLogEvent.class)
    @Async(value = "eventListenerTaskExecutor")
    public void saveErrorLog(ErrorLogEvent event) {
        BPanErrorLog record = new BPanErrorLog();
        record.setId(IdUtil.get());
        record.setLogContent(event.getErrorMsg());
        record.setLogStatus(0);
        record.setCreateUser(event.getUserId());
        record.setCreateTime(new Date());
        record.setUpdateUser(event.getUserId());
        record.setUpdateTime(new Date());
        errorLogService.save(record);
    }

}
