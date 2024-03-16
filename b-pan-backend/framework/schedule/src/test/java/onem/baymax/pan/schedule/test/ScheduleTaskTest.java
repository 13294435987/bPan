package onem.baymax.pan.schedule.test;

import onem.baymax.pan.schedule.ScheduleManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * ScheduleTaskTest
 *
 * @author hujiabin wrote in 2024/3/16 14:28
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ScheduleTestConfig.class)
public class ScheduleTaskTest {

    @Resource
    private ScheduleManager manager;

    @Resource
    private SimpleScheduleTask scheduleTask;

    @Test
    public void testRunScheduleTask() throws Exception {

        String cron = "0/5 * * * * ? ";

        String key = manager.startTask(scheduleTask, cron);

        Thread.sleep(10000);

        cron = "0/1 * * * * ? ";

        key = manager.changeTask(key, cron);

        Thread.sleep(10000);

        manager.stopTask(key);

    }
}
