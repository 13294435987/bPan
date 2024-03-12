package onem.baymax.pan.server;

import onem.baymax.pan.core.constant.BPanConstants;
import onem.baymax.pan.core.response.R;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 主启动类
 *
 * @author hujiabin wrote in 2024/3/12 22:20
 */
@SpringBootApplication(scanBasePackages = BPanConstants.BASE_COMPONENT_SCAN_PATH)
@ServletComponentScan(basePackages = BPanConstants.BASE_COMPONENT_SCAN_PATH)
@RestController
public class BPanServerLauncher {

    public static void main(String[] args) {
        SpringApplication.run(BPanServerLauncher.class);
    }

    @GetMapping("/hello/{name}")
    public R<String> hello(@PathVariable String name) {
        return R.success("hello" + name);
    }
}
