package com.vw.virtualwallet.services.helpers;

import java.util.regex.Pattern;

/**
 * ValidationHelper provides utility methods for validating various data formats.
 *
 * @author caito
 */
public class ValidationHelper {

    /**
     * Validates if the given email address is in a correct format.
     *
     * @param email the email address to validate
     * @return true if the email is valid, false otherwise
     */
    public static boolean validateEmail(String email){
        Pattern PATTERN = Pattern.compile("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        return PATTERN.matcher(email).matches();
    }

    /**
     * Validates if the given password meets the required criteria.
     * The password must contain at least one digit, one lowercase letter,
     * one uppercase letter, one special character, and be at least 8 characters long.
     *
     * @param password the password to validate
     * @return true if the password is valid, false otherwise
     */
    public static boolean validatePassword(String password){
        final String PASS_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        final Pattern PASS_PATTERN = Pattern.compile(PASS_REGEX);
        return PASS_PATTERN.matcher(password).matches();
    }
}
