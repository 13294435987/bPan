package onem.baymax.pan.server;

import onem.baymax.pan.core.constant.BPanConstant;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 主启动类
 *
 * @author hujiabin wrote in 2024/3/12 22:20
 */
@SpringBootApplication(scanBasePackages = BPanConstant.BASE_COMPONENT_SCAN_PATH)
@ServletComponentScan(basePackages = BPanConstant.BASE_COMPONENT_SCAN_PATH)
@EnableTransactionManagement
@MapperScan(basePackages = BPanConstant.BASE_COMPONENT_SCAN_PATH + ".server.module.**.mapper")
@EnableAsync
public class BPanServerLauncher {

    public static void main(String[] args) {
        SpringApplication.run(BPanServerLauncher.class);
    }

}
