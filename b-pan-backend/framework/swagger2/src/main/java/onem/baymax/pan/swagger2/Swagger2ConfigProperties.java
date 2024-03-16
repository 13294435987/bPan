package onem.baymax.pan.swagger2;

import lombok.Data;
import onem.baymax.pan.core.constant.BPanConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * swagger2配置属性实体
 *
 * @author hujiabin wrote in 2024/3/12 23:03
 */
@Data
@Component
@ConfigurationProperties(prefix = "swagger2")
public class Swagger2ConfigProperties {

    private boolean show = true;

    private String groupName = "b-pan";

    private String basePackage = BPanConstant.BASE_COMPONENT_SCAN_PATH;

    private String title = "b-pan-server";

    private String description = "b-pan-server";

    private String termsOfServiceUrl = "http://127.0.0.1:${server.port}";

    private String contactName = "baymax";

    private String contactUrl = "none";

    private String contactEmail = "none";

    private String version = "1.0";

}

