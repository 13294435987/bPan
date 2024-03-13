package onem.baymax.pan.server;

import io.swagger.annotations.Api;
import onem.baymax.pan.core.constant.BPanConstants;
import onem.baymax.pan.core.response.R;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * 主启动类
 *
 * @author hujiabin wrote in 2024/3/12 22:20
 */
@SpringBootApplication(scanBasePackages = BPanConstants.BASE_COMPONENT_SCAN_PATH)
@ServletComponentScan(basePackages = BPanConstants.BASE_COMPONENT_SCAN_PATH)
@RestController
@Api("test-api-hello")
@Validated
public class BPanServerLauncher {

    public static void main(String[] args) {
        SpringApplication.run(BPanServerLauncher.class);
    }

    @GetMapping("/hello")
    public R<String> hello(@NotBlank(message = "name不能为空") String name) {
        return R.success("hello" + name);
    }
}
