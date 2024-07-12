package top.zekee.acmerbackend.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import top.zekee.acmerbackend.anno.EnumValue;
import top.zekee.acmerbackend.exceptions.EnumTypeException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

@Slf4j
public class EnumValueValidator implements ConstraintValidator<EnumValue, Object> {

    private Class<? extends Enum<?>> enumClass;
    private String enumMethod;

    @Override
    public void initialize(EnumValue enumValue) {
        enumMethod = enumValue.enumMethod();
        enumClass = enumValue.enumClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return Boolean.TRUE;
        }

        if (enumClass == null || enumMethod == null) {
            return Boolean.TRUE;
        }

        Class<?> valueClass = value.getClass();

        try {
            Method method = enumClass.getMethod(enumMethod, valueClass);
            if (!Boolean.TYPE.equals(method.getReturnType()) && !Boolean.class.equals(method.getReturnType())) {
                throw new RuntimeException("The return type of the method must be boolean");
            }

            if(!Modifier.isStatic(method.getModifiers())) {
                throw new RuntimeException("The method must be static");
            }

            Boolean result = (Boolean)method.invoke(null, value);
            if (result == null || !result) {
                throw new EnumTypeException("枚举类型不存在");
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}