package com.zavier.lenglish.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.zavier.lenglish.pojo.Users;
import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.concurrent.TimeUnit;

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
