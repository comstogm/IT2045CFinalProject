package com.bank;

public class Account {
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

    @Override
    public String toString() {
        return " Balance " + getBalance() + " Interest " + getInterest() + " Periods " + getPeriods();
    }


}
