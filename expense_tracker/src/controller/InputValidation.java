package controller;

import java.util.Arrays;

/**
 * Utility class for validating user input related to transaction details.
 * It ensures valid amount and category inputs for both entry and filtering.
 */
public class InputValidation {

    /**
     * Validates whether the given transaction amount is within a reasonable range.
     *
     * @param amount the amount to validate
     * @return true if valid; false otherwise
     */
    public static boolean isValidAmount(double amount) {
        // Amount should be greater than 0 and less than 1000
        return amount > 0 && amount < 1000;
    }

    /**
     * Validates the provided category string.
     *
     * @param category the category input to validate
     * @return true if it's a valid category; false otherwise
     */
    public static boolean isValidCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            return false;
        }

        // Ensure category contains only alphabetic characters
        if (!category.matches("[a-zA-Z]+")) {
            return false;
        }

        // List of accepted categories
        String[] allowedCategories = {"food", "travel", "bills", "entertainment", "other"};

        // Validate against the allowed list
        return Arrays.asList(allowedCategories).contains(category.toLowerCase());
    }
}