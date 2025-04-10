package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The ExpenseTrackerModel class encapsulates transaction data
 * and provides controlled access and modifications.
 */
public class ExpenseTrackerModel {

    // List to store all transactions - encapsulated to prevent external modification
    private final List<Transaction> transactionList;

    public ExpenseTrackerModel() {
        this.transactionList = new ArrayList<>();
    }

    /**
     * Adds a new transaction to the model.
     *
     * @param transaction the transaction to be added
     */
    public void addTransaction(Transaction transaction) {
        transactionList.add(transaction);
    }

    /**
     * Removes an existing transaction from the model.
     *
     * @param transaction the transaction to be removed
     */
    public void removeTransaction(Transaction transaction) {
        transactionList.remove(transaction);
    }

    /**
     * Returns an unmodifiable shallow copy of the transactions list.
     * 
     * Uses Collections.unmodifiableList(...) to explicitly make the returned list read-only.
     * Uses constructor shorthand new ArrayList<>(...) to make a shallow copy.
     *
     * @return an unmodifiable list of transactions
     */
    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(new ArrayList<>(transactionList));
    }
}