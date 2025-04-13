// package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controller.AmountFilter;
import controller.CategoryFilter;
import controller.ExpenseTrackerController;
import model.ExpenseTrackerModel;
import model.Transaction;
import view.ExpenseTrackerView;

public class TestExample {

    private ExpenseTrackerModel model;
    private ExpenseTrackerView view;
    private ExpenseTrackerController controller;

    @Before
    public void setup() {
        model = new ExpenseTrackerModel();
        view = new ExpenseTrackerView(); // Or DummyView if using mocks
        controller = new ExpenseTrackerController(model, view);
    }

    private double getTotalCost() {
        return model.getTransactions().stream()
                    .mapToDouble(Transaction::getAmount)
                    .sum();
    }

    @Test
    public void testAddTransaction_ValidData() {
        /*
         * Title: Add Transaction (Valid)
         * Description: Add a transaction with valid amount and category.
         * Steps:
         *   - Add a transaction with amount = 50.00, category = "food"
         * Expected:
         *   - One transaction is added
         *   - Total cost reflects 50.00
         */
        assertEquals(0, model.getTransactions().size());

        boolean success = controller.addTransaction(50.00, "food");

        assertTrue("Transaction should be added successfully", success);
        assertEquals("Transaction list size should be 1", 1, model.getTransactions().size());

        Transaction added = model.getTransactions().get(0);
        assertEquals("Amount should be 50.00", 50.00, added.getAmount(), 0.001);
        assertEquals("Category should be 'food'", "food", added.getCategory());

        assertEquals("Total cost should be 50.00", 50.00, getTotalCost(), 0.001);
    }

    @Test
    public void testAddTransaction_InvalidInput() {
        /*
         * Title: Add Transaction (Invalid)
         * Description: Add a transaction with invalid amount and category.
         * Steps:
         *   - Try adding amount = -10, category = "123@"
         * Expected:
         *   - Transaction is not added
         *   - Total cost remains unchanged
         */
        boolean success = controller.addTransaction(-10.00, "123@");
        assertFalse("Invalid transaction should be rejected", success);

        assertEquals("No transaction should be added", 0, model.getTransactions().size());
        assertEquals("Total cost should remain 0.00", 0.00, getTotalCost(), 0.001);
    }

    @Test
    public void testFilterTransactionsByAmount() {
        /*
         * Title: Filter by Amount
         * Description: Add multiple transactions and filter by amount.
         * Steps:
         *   - Add three transactions: 50 (food), 100 (bills), 50 (travel)
         *   - Apply filter for amount = 50
         * Expected:
         *   - Two transactions with amount 50 returned
         */
        model.addTransaction(new Transaction(50.00, "food"));
        model.addTransaction(new Transaction(100.00, "bills"));
        model.addTransaction(new Transaction(50.00, "travel"));

        AmountFilter amountFilter = new AmountFilter(50.00);
        assertTrue("Amount filter input should be valid", amountFilter.isInputValid());

        List<Transaction> filtered = amountFilter.filter(model.getTransactions());

        assertEquals("Should return two transactions with amount 50", 2, filtered.size());

        for (Transaction txn : filtered) {
            assertEquals("Each filtered transaction should have amount 50", 50.00, txn.getAmount(), 0.001);
        }
    }

    @Test
    public void testFilterTransactionsByCategory() {
        /*
         * Title: Filter by Category
         * Description: Add multiple transactions and filter by category.
         * Steps:
         *   - Add three transactions: (food), (travel), (food)
         *   - Apply filter for category = "food"
         * Expected:
         *   - Two transactions with category "food" returned
         */
        model.addTransaction(new Transaction(30.00, "food"));
        model.addTransaction(new Transaction(70.00, "travel"));
        model.addTransaction(new Transaction(90.00, "food"));

        CategoryFilter categoryFilter = new CategoryFilter("food");
        assertTrue("Category filter input should be valid", categoryFilter.isInputValid());

        List<Transaction> filtered = categoryFilter.filter(model.getTransactions());

        assertEquals("Should return two transactions with category 'food'", 2, filtered.size());

        for (Transaction txn : filtered) {
            assertEquals("Each filtered transaction should have category 'food'", "food", txn.getCategory());
        }
    }
}