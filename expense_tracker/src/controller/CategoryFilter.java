package controller;

import model.Transaction;
import controller.TransactionFilter;
import model.ExpenseTrackerModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Filters transactions based on a specific category.
 * Implements the TransactionFilter interface.
 */
public class CategoryFilter implements TransactionFilter {

    private final String targetCategory;

    public CategoryFilter(String targetCategory) {
        this.targetCategory = targetCategory;
    }

    /**
     * Validates the target category string.
     *
     * @return true if the category is valid (non-null, non-blank)
     */
    @Override
    public boolean isInputValid() {
        return InputValidation.isValidCategory(targetCategory);
    }

    /**
     * Filters the provided list of transactions to only include those
     * matching the target category.
     *
     * @param allTransactions the list of transactions to filter
     * @return a list of transactions in the specified category
     */
    @Override
    public List<Transaction> filter(List<Transaction> allTransactions) {
        List<Transaction> matchingTransactions = new ArrayList<>();
        for (Transaction txn : allTransactions) {
            if (txn.getCategory().equalsIgnoreCase(targetCategory)) {
                matchingTransactions.add(txn);
            }
        }
        return matchingTransactions;
    }
}