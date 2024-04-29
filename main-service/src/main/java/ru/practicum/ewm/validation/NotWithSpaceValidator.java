package ru.practicum.ewm.validation;

import ru.practicum.ewm.exception.BadRequestException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotWithSpaceValidator implements ConstraintValidator<NotWithSpace, String> {

    @Override
    public void initialize(NotWithSpace constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String message = context.getDefaultConstraintMessageTemplate();
        if (value != null && !(value.isEmpty() || value.contains(" ")))
            return true;
        //else throw new BadRequestException("Ошибка: Строка не должна пустой и не должна содержать пробелы");
        else throw new BadRequestException(message);
    }
}


