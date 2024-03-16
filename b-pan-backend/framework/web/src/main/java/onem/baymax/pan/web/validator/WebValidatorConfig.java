package onem.baymax.pan.web.validator;

import lombok.extern.slf4j.Slf4j;
import onem.baymax.pan.core.constant.BPanConstant;
import org.hibernate.validator.HibernateValidator;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * 统一的参数校验器
 *
 * @author hujiabin wrote in 2024/3/13 20:46
 */
@SpringBootConfiguration
@Slf4j
public class WebValidatorConfig {

    private static final String FAIL_FAST_KEY = "hibernate.validator.fail_fast";

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
        postProcessor.setValidator(bPanValidator());
        log.info("The hibernate validator is loaded successfully!");
        return postProcessor;
    }

    /**
     * 构造项目的方法参数校验器
     *
     * @return Validator
     */
    private Validator bPanValidator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .addProperty(FAIL_FAST_KEY, BPanConstant.TRUE_STR)
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }
}
