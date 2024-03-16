package onem.baymax.pan.schedule.test;

import lombok.extern.slf4j.Slf4j;
import onem.baymax.pan.schedule.ScheduleTask;
import org.springframework.stereotype.Component;

/**
 * SimpleScheduleTask
 *
 * @author hujiabin wrote in 2024/3/16 14:27
 */
@Component
@Slf4j
public class SimpleScheduleTask implements ScheduleTask {

    @Override
    public String getName() {
        return "测试定时任务";
    }

    @Override
    public void run() {
        log.info(getName() + "正在执行。。。");
    }
}
