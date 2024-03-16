package onem.baymax.pan.schedule;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 定时模块配置类 <p>
 * 配置定时器执行器
 *
 * @author hujiabin wrote in 2024/3/16 13:41
 */
@SpringBootConfiguration
public class ScheduleConfig {

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        return new ThreadPoolTaskScheduler();
    }
}
