package controller;

import view.ExpenseTrackerView;
import model.ExpenseTrackerModel;
import model.Transaction;

import java.util.List;

/**
 * Controller class for managing the interactions between
 * the Expense Tracker's model and view components.
 */
public class ExpenseTrackerController {

    private final ExpenseTrackerModel trackerModel;
    private final ExpenseTrackerView trackerView;
    private TransactionFilter currentFilter;

    public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
        this.trackerModel = model;
        this.trackerView = view;
        // Set up view event handlers here if needed
    }

    /**
     * Refreshes the transaction table in the view with the current model data.
     */
    public void refreshView() {
        List<Transaction> allTransactions = trackerModel.getTransactions();
        trackerView.refreshTable(allTransactions);
    }

    /**
     * Highlights the specified transactions in the view.
     *
     * @param filteredTransactions the list of filtered transactions to highlight
     */
    public void highlightFilteredTransactions(List<Transaction> filteredTransactions) {
        trackerView.highlightTable(filteredTransactions);
    }

    /**
     * Adds a new transaction to the tracker if input is valid.
     *
     * @param amount   the transaction amount
     * @param category the transaction category
     * @return true if the transaction was added successfully, false otherwise
     */
    public boolean addTransaction(double amount, String category) {
        if (!InputValidation.isValidAmount(amount) || !InputValidation.isValidCategory(category)) {
            return false;
        }

        Transaction newTransaction = new Transaction(amount, category);
        trackerModel.addTransaction(newTransaction);

        trackerView.getTableModel().addRow(new Object[]{
            newTransaction.getAmount(),
            newTransaction.getCategory(),
            newTransaction.getTimestamp()
        });

        refreshView();
        return true;
    }

    /**
     * Sets the strategy for filtering transactions.
     *
     * @param filterStrategy the selected transaction filter
     */
    public void setFilterStrategy(TransactionFilter filterStrategy) {
        this.currentFilter = filterStrategy;
    }

    /**
     * Applies the current filter strategy and highlights the results.
     *
     * @return true if the filter is valid and applied, false otherwise
     */
    public boolean applyFilter() {
        List<Transaction> allTransactions = trackerModel.getTransactions();

        if (currentFilter == null || !currentFilter.isInputValid()) {
            return false;
        }

        List<Transaction> filteredResults = currentFilter.filter(allTransactions);
        highlightFilteredTransactions(filteredResults);
        return true;
    }

    /**
     * Removes a transaction that matches the specified details.
     *
     * @param amount    transaction amount
     * @param category  transaction category
     * @param timestamp transaction timestamp (String)
     */
    public void removeTransaction(double amount, String category, String timestamp) {
        List<Transaction> allTransactions = trackerModel.getTransactions();

        for (Transaction txn : allTransactions) {
            if (txn.getAmount() == amount &&
                txn.getCategory().equals(category) &&
                txn.getTimestamp().equals(timestamp)) {
                trackerModel.removeTransaction(txn);
                break;
            }
        }

        refreshView();
    }
}