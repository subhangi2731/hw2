package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import controller.InputValidation;
import model.Transaction;

import java.awt.*;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExpenseTrackerView extends JFrame {

  private JTable transactionTable;
  private JButton addTransactionButton;
  private JButton filterByCategoryButton;
  private JButton filterByAmountButton;
  private JFormattedTextField amountInputField;
  private JTextField categoryInputField;
  private JFormattedTextField amountFilterInputField;
  private JTextField categoryFilterInputField;
  private DefaultTableModel tableModel;

  public ExpenseTrackerView() {
    setTitle("Expense Tracker");
    setSize(800, 300);

    String[] tableColumns = {"Serial", "Amount", "Category", "Date"};
    tableModel = new DefaultTableModel(tableColumns, 0);

    addTransactionButton = new JButton("Add Transaction");
    filterByAmountButton = new JButton("Filter by Amount");
    filterByCategoryButton = new JButton("Filter by Category");

    JLabel amountLabel = new JLabel("Amount:");
    NumberFormat numberFormat = NumberFormat.getNumberInstance();

    amountInputField = new JFormattedTextField(numberFormat);
    amountInputField.setColumns(10);

    JLabel categoryLabel = new JLabel("Category:");
    categoryInputField = new JTextField(10);

    transactionTable = new JTable(tableModel);

    // Input Section
    JPanel inputPanel = new JPanel();
    inputPanel.add(amountLabel);
    inputPanel.add(amountInputField);
    inputPanel.add(categoryLabel);
    inputPanel.add(categoryInputField);
    inputPanel.add(addTransactionButton);

    // Filter Section
    JLabel amountFilterLabel = new JLabel("Amount Filter:");
    amountFilterInputField = new JFormattedTextField(numberFormat);
    amountFilterInputField.setColumns(10);

    JLabel categoryFilterLabel = new JLabel("Category Filter:");
    categoryFilterInputField = new JTextField(10);

    JPanel filterPanel = new JPanel();
    filterPanel.add(amountFilterLabel);
    filterPanel.add(amountFilterInputField);
    filterPanel.add(filterByAmountButton);
    filterPanel.add(categoryFilterLabel);
    filterPanel.add(categoryFilterInputField);
    filterPanel.add(filterByCategoryButton);

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(addTransactionButton);

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(inputPanel);
    mainPanel.add(filterPanel);

    add(mainPanel, BorderLayout.NORTH);
    add(new JScrollPane(transactionTable), BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  public void refreshTable(List<Transaction> transactions) {
    tableModel.setRowCount(0);
    int rowCount = 0;
    double totalAmount = 0;

    for (Transaction t : transactions) {
      totalAmount += t.getAmount();
    }

    for (Transaction t : transactions) {
      tableModel.addRow(new Object[]{++rowCount, t.getAmount(), t.getCategory(), t.getTimestamp()});
    }

    tableModel.addRow(new Object[]{"Total", null, null, totalAmount});
    transactionTable.updateUI();
    resetRowHighlighting();
  }

  public JButton getAddTransactionBtn() {
    return addTransactionButton;
  }

  public JButton getFilterByAmountButton() {
    return filterByAmountButton;
  }

  public JButton getFilterByCategoryButton() {
    return filterByCategoryButton;
  }

  public DefaultTableModel getTableModel() {
    return tableModel;
  }

  public JTable getTransactionTable() {
    return transactionTable;
  }

  public double getEnteredAmount() {
    String text = amountInputField.getText();
    return text.isEmpty() ? 0 : Double.parseDouble(text);
  }

  public String getEnteredCategory() {
    return categoryInputField.getText();
  }

  public double getAmountField() {
    String text = amountFilterInputField.getText();
    return text.isEmpty() ? 0 : Double.parseDouble(text);
  }

  public String getCategoryField() {
    return categoryFilterInputField.getText();
  }

  public void highlightTable(List<Transaction> highlightedTransactions) {
    Set<Integer> highlightRows = findMatchingRowIndices(highlightedTransactions);

    transactionTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        cell.setBackground(highlightRows.contains(row) ? Color.GREEN : table.getBackground());
        return cell;
      }
    });

    transactionTable.repaint();
  }

  public void resetRowHighlighting() {
    transactionTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      }
    });
    transactionTable.repaint();
  }

  private Set<Integer> findMatchingRowIndices(List<Transaction> filteredTransactions) {
    Set<Integer> matchingIndices = new HashSet<>();

    for (Transaction transaction : filteredTransactions) {
      for (int row = 0; row < tableModel.getRowCount() - 1; row++) {
        if (transaction.getAmount() == (double) tableModel.getValueAt(row, 1)
            && transaction.getCategory().equals(tableModel.getValueAt(row, 2))
            && transaction.getTimestamp().equals(tableModel.getValueAt(row, 3))) {
          matchingIndices.add(row);
        }
      }
    }

    return matchingIndices;
  }
}