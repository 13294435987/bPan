package onem.baymax.pan.schedule;

/**
 * 定时任务的任务接口
 *
 * @author hujiabin wrote in 2024/3/16 13:47
 */
public interface ScheduleTask extends Runnable {


    /**
     * 获取定时任务的名称
     *
     * @return name
     */
    String getName();
}
