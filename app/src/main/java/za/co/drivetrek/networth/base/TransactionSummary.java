package za.co.drivetrek.networth.base;

import za.co.drivetrek.networth.storage.entity.Transaction;

import java.util.List;

public class TransactionSummary {
    private Double income;
    private Double expenses;
    private Double balance;
    public static String DEBIT = "DR";
    public static String CREDIT = "CR";
    public static String NONE = "NONE";

    public TransactionSummary(List<Transaction> transactions) {
        if (!transactions.isEmpty()){
            income = transactions.stream()
                    .filter(t -> t.getType().equals(DEBIT))
                    .mapToDouble(Transaction::getAmount)
                    .sum();

            expenses = transactions.stream()
                    .filter(t -> t.getType().equals(CREDIT))
                    .mapToDouble(Transaction::getAmount)
                    .sum();

            balance = income - expenses;
        }
        else {
            income = (double) 0;
            expenses = income;
            balance = income;
        }
    }

    public Double getIncome() {
        return income;
    }

    public Double getExpenses() {
        return expenses;
    }

    public Double getBalance() {
        return balance;
    }
}
