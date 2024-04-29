package ru.practicum.ewm.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MinAvailableDateValidator implements ConstraintValidator<MinAvailableDate, String> {
    private LocalDateTime minimumDate;

    @Override
    public void initialize(MinAvailableDate constraintAnnotation) {
        minimumDate = LocalDateTime.now().plusHours(constraintAnnotation.value());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return true;
        LocalDateTime eventDate = LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return !eventDate.isBefore(minimumDate);
    }
}
