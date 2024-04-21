package com.algomart.kibouregistry.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberValidator {

    // Regular expression pattern for validating phone numbers
    private static final String PHONE_NUMBER_PATTERN = "^\\+(?:[0-9] ?){6,14}[0-9]$";

    // Compile the regular expression pattern
    private static final Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);

    /**
     * Validate the given phone number using regular expression.
     *
     * @param phoneNumber The phone number to validate
     * @return true if the phone number is valid, false otherwise
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}
