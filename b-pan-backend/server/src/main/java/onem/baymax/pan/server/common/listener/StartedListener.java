package onem.baymax.pan.server.common.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 项目启动成功日志打印监听器
 *
 * @author hujiabin wrote in 2024/3/13 20:41
 */
@Component
@Slf4j
public class StartedListener implements ApplicationListener<ApplicationReadyEvent> {

    /**
     * 项目启动成功将会在日志中输出对应的启动信息
     *
     * @param applicationReadyEvent event
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        String serverPort = applicationReadyEvent.getApplicationContext().getEnvironment().getProperty("server.port");
        String serverUrl = String.format("http://%s:%s", "127.0.0.1", serverPort);
        log.info(AnsiOutput.toString(AnsiColor.BRIGHT_BLUE, "b pan server started at: ", serverUrl));
        if (checkShowServerDoc(applicationReadyEvent.getApplicationContext())) {
            log.info(AnsiOutput.toString(AnsiColor.BRIGHT_BLUE, "b pan server's doc started at:", serverUrl + "/doc.html"));
        }
        log.info(AnsiOutput.toString(AnsiColor.BRIGHT_YELLOW, "b pan server has started successfully!"));
    }

    /**
     * 校验是否开启了接口文档
     *
     * @param applicationContext context
     * @return true/false
     */
    private boolean checkShowServerDoc(ConfigurableApplicationContext applicationContext) {
        return applicationContext.getEnvironment().getProperty("swagger2.show", Boolean.class, true) && applicationContext.containsBean("swagger2Config");
    }

}

