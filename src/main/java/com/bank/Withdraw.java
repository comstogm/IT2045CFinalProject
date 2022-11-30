package com.bank;

public class Withdraw {
    public static void withdraw(double withdraw) {
        Account withdrawAccount = BankerForm.allAccounts.peek();

        double currentBalance = withdrawAccount.getBalance();
        boolean done = true;
        do {
            if (withdraw < currentBalance){
                withdrawAccount.setBalance(currentBalance - withdraw);
                done = true;
            }
            else if (withdraw == currentBalance) {
                Account removeAccount = BankerForm.allAccounts.poll();
                done = true;
            }
            else if (withdraw > currentBalance) {
                withdraw = withdraw - currentBalance;
                Account removeAccount = BankerForm.allAccounts.poll();
                withdrawAccount = BankerForm.allAccounts.peek();
                currentBalance = withdrawAccount.getBalance();
                done = false;
            }
        }
        while (!done);
    }
}