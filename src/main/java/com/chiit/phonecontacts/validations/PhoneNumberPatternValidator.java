package com.chiit.phonecontacts.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.regex.Pattern;

public class PhoneNumberPatternValidator implements ConstraintValidator<PhoneNumberPattern, Set<String>> {
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("(\\+380\\d{9})");

    @Override
    public boolean isValid(Set<String> phoneNumbers, ConstraintValidatorContext context) {

        if (phoneNumbers == null || phoneNumbers.isEmpty()) {
            return false;
        }

        for (String phoneNumber : phoneNumbers) {
            if (!PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches()) {
                return false;
            }
        }
        return true;
    }
}
