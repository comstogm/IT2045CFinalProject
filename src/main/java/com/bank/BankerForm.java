package com.bank;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

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
    public static Queue<Account> allAccounts = new PriorityQueue<>();
    public static Set<Integer> accountNumbers = new HashSet<>();
    final static String FILE = "accounts.json";

    public BankerForm() {
        
        initializeAccountTypeComboBox();

        /*
          JsonReader.fetchAccounts adds the returned vector to the allAccounts priorityQueue
         */
        //allAccounts.addAll(JsonReader.fetchAccounts());

        //Using GSON lib to read in json file as account objects
        jsonReader();

        /*
           The next two lines set the vector that will be used.
           Updated the code to use a vector so that the UI would show an ordered list
            based off of interest rates
         */
        lstAccounts.setListData((allAccounts.toArray()));

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

                String strAccountNumber = txtAccountNumber.getText();
                int accountNumber = Integer.parseInt(strAccountNumber);
                accountNumbers.add(accountNumber);
                //Test to verify that there are no duplicates.
                //Even though it does not allow duplicate, it will still create an object
                //Maybe next step is to pass the account number to a try catch block
                // If finds it does not have a duplicate, return a true
                // and continue building object. If not, maybe throw an exceptions, clear fields, start again.
                accountNumbers.stream().forEach(Integer -> System.out.println(Integer));

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
                account.setAccountNumber(accountNumber);
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
                lstAccounts.setListData((allAccounts.toArray()));
                clearFields();
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
                clearFields();
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

    private void clearFields() {
        txtAccountNumber.setText("");
        txtBalance.setText("");
        txtMaturity.setText("");
        txtInterest.setText("");
        txtWithdrawal.setText("");
        txtPeriods.setText("");
    }

    private void jsonReader() {
        try {
            Reader reader = Files.newBufferedReader(Paths.get("accounts.json"));
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Account.class, new AccountSerializer());
            Gson gson = gsonBuilder.create();
            Vector<Account> jsonInAccounts = gson.fromJson(reader, new TypeToken<Vector<Account>>(){}.getType());
            allAccounts.addAll(jsonInAccounts);
            reader.close();
        } catch (IOException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("BankerForm");
        frame.setContentPane(new BankerForm().pnlMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
