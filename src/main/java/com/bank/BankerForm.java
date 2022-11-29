package com.bank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Vector;


public class BankerForm {
    private JPanel pnlMain;
    private JPanel pnlButtonBar;
    private JButton btnSave;
    private JButton btnCalc;
    private JPanel pnlCenterMain;
    private JPanel pnlInnerNorth;
    private JPanel pnlInnerCenter;
    private JList lstAccounts;
    private JComboBox cmbAccountType;
    private JLabel lblBalance;
    private JLabel lblInterest;
    private JLabel lblPeriods;
    private JLabel lblAccount;
    private JTextField txtBalance;
    private JTextField txtInterest;
    private JTextField txtPeriods;
    private JLabel lblMaturity;
    private JTextField txtMaturity;
    private JLabel lblWithdrawal;
    private JTextField txtWithdrawal;
    private JButton btnWithdraw;
    private JLabel lbAccountNumber;
    private JTextField txtAccountNumber;
    private static final Logger logger = LogManager.getLogger("accountForm");
    final String FILE = "accounts.json";
    public static Queue<Account> allAccounts = new PriorityQueue<>();


    public BankerForm() {
        
        initializeAccountTypeComboBox();

        JsonReader.readAccounts(FILE);

        /*
          JsonReader.fetchAccounts adds the returned vector to the allAccounts priorityQueue
         */
        allAccounts.addAll(JsonReader.fetchAccounts());

        /*
           The next two lines set the vector that will be used.
           Updated the code to use a vector so that the UI would show an ordered list
            based off of interest rates
         */
        Vector<Account> readAccounts = JsonReader.fetchAccounts();
        lstAccounts.setListData(readAccounts);

        /*
          Listens for user to click save button
          Reads data user entered into form and calls methods from Banker to create a new account
          if certificate of deposit account type, enables maturity field
         */
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String strBalance = txtBalance.getText();
                double balance = Double.parseDouble(strBalance);

                String strInterest = txtInterest.getText();
                double interest = Double.parseDouble(strInterest);

                String strPeriods = txtPeriods.getText();
                int periods = Integer.parseInt(strPeriods);

                String strTotalInterest = "0";
                double totalInterest = Double.parseDouble(strTotalInterest);

                String type = cmbAccountType.getSelectedItem().toString();
                Account account = null;
                try {
                    account = accountFactory.createAccountCommand(type.toString());
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                    throw new RuntimeException(ex);
                }

                account.setBalance(balance);
                account.setInterest(interest);
                account.setPeriods(periods);
                account.setTotalInterest(totalInterest);

                if (cmbAccountType.getSelectedItem().toString().equals(Banker.CERTIFICATEOFDEPOSIT)) {
                    if (account instanceof CertificateOfDeposit) {
                        CertificateOfDeposit certificateOfDeposit = (CertificateOfDeposit) account;

                        String strMaturity = txtMaturity.getText();
                        int maturity = Integer.parseInt(strMaturity);

                        certificateOfDeposit.setMaturity(maturity);
                    }
                }
                allAccounts.add(account);
                readAccounts.add(account);
                readAccounts.sort(Account::compareTo);
                lstAccounts.updateUI();
                //lstAccounts.setListData(allAccounts.toArray()); //resetting ListData to show new account
            }
        });

        /*
          Listens for user pressing calculate button
          loops through each object created and updates the form
         */
        btnCalc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allAccounts.stream().forEach(account -> {account.compute();});
                //lstAccounts.updateUI();
                lstAccounts.setListData(allAccounts.toArray());

            }
        });

        /*
          When changing the combobox to Certificate of Deposit, enables the maturity field
         */
        cmbAccountType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cmbAccountType.getSelectedItem().toString().equals(Banker.CERTIFICATEOFDEPOSIT)) {
                    lblMaturity.setEnabled(true);
                    txtMaturity.setEnabled(true);
                } else {
                    lblMaturity.setEnabled(false);
                    txtMaturity.setEnabled(false);
                }

            }
        });
        btnWithdraw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String strWithdraw = txtWithdrawal.getText();
                double withdraw = Double.parseDouble(strWithdraw);
                withdraw(withdraw);
                lstAccounts.setListData(allAccounts.toArray());
            }
        });
    }
    public static void withdraw(double withdraw) {
        Account withdrawAccount = allAccounts.peek();

        double currentBalance = withdrawAccount.getBalance();
        boolean done = true;
        do {
            if (withdraw < currentBalance){
                withdrawAccount.setBalance(currentBalance - withdraw);
                done = true;
            }
            else if (withdraw == currentBalance) {
                Account removeAccount = allAccounts.poll();
                done = true;
            }
            else if (withdraw > currentBalance) {
                withdraw = withdraw - currentBalance;
                Account removeAccount = allAccounts.poll();
                withdrawAccount = allAccounts.peek();
                currentBalance = withdrawAccount.getBalance();
                done = false;
            }
        }
        while (!done);
        //lstAccounts.updateUI();


    }


    /*
     * populates the combobox with options for account type
     */
    private void initializeAccountTypeComboBox() {
        DefaultComboBoxModel<String> accountTypesModel = new DefaultComboBoxModel<>();
        accountTypesModel.addElement(Banker.SAVINGS);
        accountTypesModel.addElement(Banker.CHECKING);
        accountTypesModel.addElement(Banker.CERTIFICATEOFDEPOSIT);
        cmbAccountType.setModel(accountTypesModel);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("BankerForm");
        frame.setContentPane(new BankerForm().pnlMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
