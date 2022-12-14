package com.bank;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Account implements Comparable<Account>, Serializable {
    public double balance;
    public double interest;
    public double totalInterest;
    public int periods;
    public int accountNumber;



    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getInterest() {
        return interest;
    }
    public void setInterest(double interest) {
        this.interest = interest;
    }

    public int getPeriods() {
        return periods;
    }
    public void setPeriods(int periods) {
        this.periods = periods;
    }
    public int getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }
    public double getTotalInterest() {return totalInterest;}
    public void setTotalInterest(double totalInterest) {this.totalInterest = totalInterest;}


    public static DecimalFormat df = new DecimalFormat("###.##");
    /**
     * calculates balance total, adding interest to the total balance
     */
    public void compute() {
        for (int i = 0; i < getPeriods(); i++) {
            double addedBalance = getBalance() * (getInterest() / 100);
            setBalance(getBalance() + addedBalance);
            setTotalInterest(getTotalInterest() + addedBalance);
        }
    }

    @Override
    public String toString() {
        return " Account Number " + df.format(getAccountNumber()) + " Balance " + df.format(getBalance()) +
                " Interest " + df.format(getInterest()) + " Periods " + df.format(getPeriods()) +
                " Total Interest " + df.format(getTotalInterest());
    }

    /*
    compareTo used to sort priority queue objects by interest rate
     */
    @Override
    public int compareTo(Account o) {
        int ourPriority = (int)getInterest();
        int theirPriority = (int)o.getInterest();
        return ourPriority - theirPriority;
    }
}
