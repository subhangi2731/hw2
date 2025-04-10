package controller;

import model.Transaction;
import controller.TransactionFilter;
import model.ExpenseTrackerModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Filters transactions based on a specific amount.
 * Implements the TransactionFilter interface.
 */
public class AmountFilter implements TransactionFilter {

    private final double targetAmount;

    public AmountFilter(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    /**
     * Validates the target amount for filtering.
     *
     * @return true if the amount is non-negative and valid
     */
    @Override
    public boolean isInputValid() {
        return InputValidation.isValidAmount(targetAmount);
    }

    /**
     * Filters the given list of transactions to match the target amount.
     *
     * @param allTransactions the list of transactions to filter
     * @return a list of transactions that match the specified amount
     */
    @Override
    public List<Transaction> filter(List<Transaction> allTransactions) {
        List<Transaction> matchingTransactions = new ArrayList<>();
        for (Transaction txn : allTransactions) {
            if (Double.compare(txn.getAmount(), targetAmount) == 0) {
                matchingTransactions.add(txn);
            }
        }
        return matchingTransactions;
    }
}