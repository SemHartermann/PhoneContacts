package com.chiit.phonecontacts.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.regex.Pattern;

public class EmailPatternValidator implements ConstraintValidator<EmailPattern, Set<String>> {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("(.+)@(\\S+\\.\\w{2,10})");

    @Override
    public boolean isValid(Set<String> emails, ConstraintValidatorContext context) {

        if (emails == null || emails.isEmpty()) {
            return false;
        }

        for (String email : emails) {
            if (!EMAIL_PATTERN.matcher(email).matches()) {
                return false;
            }
        }
        return true;
    }
}