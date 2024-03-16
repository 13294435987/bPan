package onem.baymax.pan.server;

import io.swagger.annotations.Api;
import onem.baymax.pan.core.constant.BPanConstant;
import onem.baymax.pan.core.response.R;
import onem.baymax.pan.server.module.file.mapper.BPanFileMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 主启动类
 *
 * @author hujiabin wrote in 2024/3/12 22:20
 */
@SpringBootApplication(scanBasePackages = BPanConstant.BASE_COMPONENT_SCAN_PATH)
@ServletComponentScan(basePackages = BPanConstant.BASE_COMPONENT_SCAN_PATH)
@RestController
@Api("test-api-hello")
@Validated
@MapperScan(basePackages = BPanConstant.BASE_COMPONENT_SCAN_PATH + ".server.module.**.mapper")
public class BPanServerLauncher {

    @Resource
    private BPanFileMapper bPanFileMapper;

    public static void main(String[] args) {
        SpringApplication.run(BPanServerLauncher.class);
    }

    @GetMapping("/hello")
    public R<String> hello() {
        return R.success("hello" + bPanFileMapper.selectById("1"));
    }
}
