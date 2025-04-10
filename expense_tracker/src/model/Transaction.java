package model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Immutable representation of a financial transaction.
 * Each transaction includes an amount, a category, and a timestamp.
 */
public class Transaction {

    // Immutable transaction fields
    private final double transactionAmount;
    private final String transactionCategory;
    private final String transactionTimestamp;

    /**
     * Constructs a new Transaction object with the specified amount and category.
     * A timestamp is generated at the time of creation.
     *
     * @param transactionAmount   the amount of the transaction
     * @param transactionCategory the category of the transaction
     */
    public Transaction(double transactionAmount, String transactionCategory) {
        this.transactionAmount = transactionAmount;
        this.transactionCategory = transactionCategory;
        this.transactionTimestamp = generateCurrentTimestamp();
    }

    public double getAmount() {
        return transactionAmount;
    }

    public String getCategory() {
        return transactionCategory;
    }

    public String getTimestamp() {
        return transactionTimestamp;
    }

    /**
     * Generates the current timestamp in "dd-MM-yyyy HH:mm" format.
     *
     * @return formatted timestamp string
     */
    private String generateCurrentTimestamp() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return formatter.format(new Date());
    }
}