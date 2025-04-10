package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
* The ExpenseTrackerModel class manages a list of transactions.
* It ensures encapsulation and immutability by restricting direct access
* and returning unmodifiable views of internal data.
*/
public class ExpenseTrackerModel {


   // Encapsulated list to store all transactions
   private final List<Transaction> transactionList;


   public ExpenseTrackerModel() {
       this.transactionList = new ArrayList<>();
   }


   /**
    * Adds a new transaction to the list.
    *
    * @param transaction the transaction to be added
    */
   public void addTransaction(Transaction transaction) {
       transactionList.add(transaction);
   }


   /**
    * Removes a transaction from the list.
    *
    * @param transaction the transaction to be removed
    */
   public void removeTransaction(Transaction transaction) {
       transactionList.remove(transaction);
   }


   /**
    * Returns an unmodifiable copy of the transaction list to preserve immutability.
    *
    * - Uses Collections.unmodifiableList(...) to explicitly make the returned list read-only.
    * - Uses constructor shorthand new ArrayList<>(transactionList) to make a shallow copy.
    *
    * @return a read-only list of transactions
    */
   public List<Transaction> getTransactions() {
       return Collections.unmodifiableList(new ArrayList<>(transactionList));
   }
}