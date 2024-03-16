package onem.baymax.pan.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.concurrent.ScheduledFuture;

/**
 * 定时任务和定时任务结果的缓存对象
 *
 * @author hujiabin wrote in 2024/3/16 13:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleTaskHolder implements Serializable {

    /**
     * 执行任务实体
     */
    private ScheduleTask scheduleTask;

    /**
     * 执行任务的结果实体
     */
    private ScheduledFuture<?> scheduledFuture;

}
