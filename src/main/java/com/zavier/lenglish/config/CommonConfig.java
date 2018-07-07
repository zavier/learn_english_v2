package com.zavier.lenglish.config;

import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Configuration
public class CommonConfig {
    /**
     * Bean 间复制使用
     * @return
     */
    @Bean
    public Mapper mapper() {
        return DozerBeanMapperBuilder.buildDefault();
    }

    /**
     * Bean 属性校验用
     * @return
     */
    @Bean
    public Validator validator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator;
    }
}
