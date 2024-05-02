package ru.practicum.ewm.validation;

import javax.validation.Constraint;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinAvailableDateValidator.class)

public @interface MinAvailableDate {
    String message() default "Дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};

    int value() default 2;
}
