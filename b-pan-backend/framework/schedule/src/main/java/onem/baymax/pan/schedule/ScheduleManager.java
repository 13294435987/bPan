package onem.baymax.pan.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onem.baymax.pan.core.exception.BPanFrameworkException;
import onem.baymax.pan.core.util.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * 定时任务管理器 <p>
 * 1、创建并启动一个定时任务 <p>
 * 2、停止一个定时任务 <p>
 * 3、更新一个定时任务
 *
 * @author hujiabin wrote in 2024/3/16 13:50
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduleManager {

    private final ThreadPoolTaskScheduler taskScheduler;

    /**
     * 内部正在执行的定时任务缓存
     */
    private final Map<String, ScheduleTaskHolder> taskingCache = new ConcurrentHashMap<>();

    /**
     * 启动一个定时任务
     *
     * @param scheduleTask 定时任务实现类
     * @param cron         定时任务的cron表达式
     * @return 任务的唯一标识
     */
    public String startTask(ScheduleTask scheduleTask, String cron) {
        ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(scheduleTask, new CronTrigger(cron));
        String key = UUIDUtil.getUuid();
        ScheduleTaskHolder holder = new ScheduleTaskHolder(scheduleTask, scheduledFuture);
        taskingCache.put(key, holder);
        log.info("{} 启动成功！唯一标识为：{}", scheduleTask.getName(), key);
        return key;
    }

    /**
     * 停止一个定时任务
     *
     * @param key 定时任务的唯一标识
     */
    public void stopTask(String key) {
        if (StringUtils.isBlank(key)) {
            log.warn("任务标识为空！【{}】", key);
            return;
        }

        ScheduleTaskHolder holder = taskingCache.get(key);
        if (Objects.isNull(holder)) {
            log.warn("获取不到当前任务！【{}】", key);
            return;
        }

        // 停止任务
        ScheduledFuture<?> scheduledFuture = holder.getScheduledFuture();
        boolean cancel = scheduledFuture.cancel(true);
        if (cancel) {
            log.info("{} 停止成功！唯一标识为：{}", holder.getScheduleTask().getName(), key);
        } else {
            log.error("{} 停止失败！唯一标识为：{}", holder.getScheduleTask().getName(), key);
        }
    }

    /**
     * 更新一个定时任务的执行时间
     *
     * @param key  定时任务的唯一标识
     * @param cron 新的cron表达式
     * @return 更新后的任务标识
     */
    public String changeTask(String key, String cron) {
        if (StringUtils.isAnyBlank(key, cron)) {
            throw new BPanFrameworkException("定时任务的唯一标识以及新的执行表达式不能为空");
        }

        ScheduleTaskHolder holder = taskingCache.get(key);
        if (Objects.isNull(holder)) {
            throw new BPanFrameworkException(key + "唯一标识不存在");
        }
        // 先停，后启
        stopTask(key);
        return startTask(holder.getScheduleTask(), cron);
    }


}
