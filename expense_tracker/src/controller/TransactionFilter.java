package controller;

import model.Transaction;
import java.util.List;

/**
 * Interface for filtering transactions based on specific criteria.
 * Designed for reuse in various filtering scenarios across the application.
 */
public interface TransactionFilter {

    /**
     * Applies a filter to the given list of transactions.
     *
     * @param allTransactions the list of transactions to filter
     * @return a new list containing only the transactions that match the filter criteria
     */
    List<Transaction> filter(List<Transaction> allTransactions);

    /**
     * Validates the input criteria used for filtering.
     *
     * @return true if the input is valid, false otherwise
     */
    boolean isInputValid();
}