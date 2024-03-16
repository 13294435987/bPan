package onem.baymax.pan.schedule.test;

import onem.baymax.pan.core.constant.BPanConstant;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * ScheduleTestConfig
 *
 * @author hujiabin wrote in 2024/3/16 14:28
 */

@SpringBootConfiguration
@ComponentScan(BPanConstant.BASE_COMPONENT_SCAN_PATH + ".schedule")
public class ScheduleTestConfig {
}
