package com.zavier.lenglish.common.util;

import com.zavier.lenglish.common.BusinessProcessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Component
@Slf4j
public class ValidatorUtil {
    @Autowired
    private Validator validator;

    public <T> void validate(T t) {
        Set<ConstraintViolation<T>> validate = validator.validate(t);
        if (CollectionUtils.isNotEmpty(validate)) {
            int size = validate.size();
            if (size != 0) {
                for (ConstraintViolation<T> cv : validate) {
                    log.error("参数错误 {} {}", cv.getPropertyPath(), cv.getMessage());
                    throw new BusinessProcessException("参数错误: " + cv.getPropertyPath() + cv.getMessage());
                }
            }
        }
    }
}
