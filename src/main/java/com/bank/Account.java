package com.bank;

public class Account implements Comparable<Account> {
    public double balance;
    public double interest;
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


    /**
     * calculates balance total, adding interest to the total balance
     */
    public void compute() {
        for (int i = 0; i < getPeriods(); i++) {
            setBalance(getBalance() + (getBalance() * (getInterest() / 100)));
        }
    }

    // Put this here, so I can set and get balance.
    // If you guys know how this can be put in BankerForm let me know
    public void withdraw(double amountWithdrawn) {
        double amountTaken = amountWithdrawn;
        setBalance(getBalance() - amountTaken);
    }

    @Override
    public String toString() {
        return " Balance " + getBalance() + " Interest " + getInterest() + " Periods " + getPeriods();
    }

    @Override
    public int compareTo(Account o) {
        int ourPriority = (int)getInterest();
        int theirPriority = (int)o.getInterest();
        return ourPriority - theirPriority;
    }
}
