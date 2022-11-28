package com.bank;

import javax.swing.*;
import java.util.ArrayList;

public class Banker {
    public static final String SAVINGS = "Savings";
    public static final String CHECKING = "Checking";
    public static final String CERTIFICATEOFDEPOSIT = "Certificate of Deposit";
    private static ArrayList<Account> allAccounts = new ArrayList<>();
    private static Banker banker = null;

    private Banker() {

    }
    public static void main(String[] args) {
        banker.getInstance();
        banker.promptUser();
        banker.displayBalance();
        results();
    }

    public static Banker getInstance() {
        if (banker == null) {
            banker = new Banker();
        }
        return banker;
    }

    /**
     * asks user if they wish to see all accounts
     * display balance, interest and periods of all accounts if yes
     * "Add something extra" portion of assignment
     */
    private static void results() {
        int seeBalances = JOptionPane.NO_OPTION;
        seeBalances = JOptionPane.showConfirmDialog(null,
                "Do you want to see all accounts?", "Totals",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (seeBalances == JOptionPane.YES_OPTION) {
            for (Account a : allAccounts) {
                {
                    JOptionPane.showMessageDialog(null, "Balance: " + a.balance + "\nInterest: "
                            + a.interest + "\nTerms: " + a.periods + "\n");
                }
            }
        }
    }

    /**
     * prompts user to create a certain account type
     *  an account is then created with the input using the accountFactory class
     * prompts user to fill in information regarding account
     *  Use settrs to set attribute values
     * if certificate of deposit account type, prompts for maturity
     */
    public void promptUser() {
        int goAgain = JOptionPane.NO_OPTION;
        do {
            String[] availableAccounts = {SAVINGS, CHECKING, CERTIFICATEOFDEPOSIT};
            Object accountType = JOptionPane.showInputDialog(null,
                    "Choose an Account to create", "Choose an Account",
                    JOptionPane.QUESTION_MESSAGE, null, availableAccounts, SAVINGS);
            Account account = com.bank.accountFactory.createAccountCommand(accountType.toString());

            String strBalance = JOptionPane.showInputDialog("Enter opening balance");
            double balance = Double.parseDouble(strBalance);
            account.setBalance(balance);

            String strInterest = JOptionPane.showInputDialog("Enter interest");
            double interest = Double.parseDouble(strInterest);
            account.setInterest(interest);

            String strPeriods = JOptionPane.showInputDialog("Enter number of periods");
            int periods = Integer.parseInt(strPeriods);
            account.setPeriods(periods);

            if (account instanceof CertificateOfDeposit) {
                String strMaturity = JOptionPane.showInputDialog("Length of CD");
                int maturity = Integer.parseInt(strMaturity);
                ((CertificateOfDeposit) account).setMaturity(maturity);
            }

            allAccounts.add(account);

            goAgain = JOptionPane.showConfirmDialog(null,
                    "Do you want to create another account?", "Go Again?",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        } while (goAgain == JOptionPane.YES_OPTION);
    }

    /**
     * displays the current and then new balance of all accounts after calling calculate()
     */
    private void displayBalance() {
        for (Account a: allAccounts) {
            JOptionPane.showMessageDialog(null, "Current Balance: " + a.balance);
            a.compute();
            JOptionPane.showMessageDialog(null, "New Balance: " + a.balance);
        }
    }

    /**
     * Old way of creating an account before setting up a factory method
     * @param selectedAccount type of account selected by user in promptUser()
     * @return returns the newly created account of specified type
     */
    /*
    public Account createAccount(final Object selectedAccount) {
        Account account = new Account();
        if (selectedAccount.toString().equals(SAVINGS)) {
            account = new Savings();
        } else if (selectedAccount.toString().equals(CHECKING)) {
            account = new Checking();
        } else if (selectedAccount.toString().equals(CERTIFICATEOFDEPOSIT)) {
            account = new CertificateOfDeposit();
        }
        return account;
    }
     */
}
