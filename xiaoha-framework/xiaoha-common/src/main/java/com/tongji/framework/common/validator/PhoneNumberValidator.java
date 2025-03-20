package com.tongji.framework.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author tongji
 * @time 2025/3/20 13:34
 * @description
 */
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    @Override
    public void initialize(PhoneNumber constraintAnnotation) {
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        // 校验逻辑：正则表达式判断手机号是否为 11 位数字
        return phoneNumber != null && phoneNumber.matches("\\d{11}");
    }
}
