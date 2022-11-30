package com.bank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JLabel lblTotalInterest;
    private JLabel txtTotalInterest;
    private static final Logger logger = LogManager.getLogger("accountForm");
    public static Queue<Account> allAccounts = new PriorityQueue<>();
    public static Set<Integer> accountNumbers = new HashSet<>();
    static double displayTotalInterest = 0.0;

    public BankerForm() {

        initializeAccountTypeComboBox();

        try {
            //Using GSON lib to read in json file as account objects
            allAccounts.addAll(AccountSerializer.jsonReader());
            //Add all imported account numbers to accountNumbers
            allAccounts.forEach(account -> {
                accountNumbers.add(account.getAccountNumber());
            });
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
        }

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

                double totalInterest = 0.0;

                String type = cmbAccountType.getSelectedItem().toString();

                String strAccountNumber = txtAccountNumber.getText();
                int accountNumber = Integer.parseInt(strAccountNumber);

                if (accountNumbers.contains(accountNumber)) {
                    JOptionPane.showMessageDialog(null,"Account number already exists." +
                            " Clearing field");
                    txtAccountNumber.setText("");
                }
                else {
                    try {
                        accountNumbers.add(accountNumber);
                        Account account = accountFactory.createAccountCommand(type);
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

                    } catch (Exception ex) {
                        logger.error(ex.getMessage());
                        throw new RuntimeException(ex);
                    }
                }

//                Boolean checkAccountNumber = ExistingAccountsCheck.getInstance().accountCheck(accountNumber);
//
//                if (!checkAccountNumber) {
//                    JOptionPane.showMessageDialog(null,"Account number already exists. Clearing" +
//                            " field");
//                    txtAccountNumber.setText("");
//                } else {
//
//                    Account account = null;
//                    try {
//                        account = accountFactory.createAccountCommand(type.toString());
//                    } catch (Exception ex) {
//                        logger.error(ex.getMessage());
//                        throw new RuntimeException(ex);
//                    }
//
//                    account.setBalance(balance);
//                    account.setInterest(interest);
//                    account.setPeriods(periods);
//                    account.setAccountNumber(accountNumber);
//                    account.setTotalInterest(totalInterest);
//
//                    if (cmbAccountType.getSelectedItem().toString().equals(Banker.CERTIFICATEOFDEPOSIT)) {
//                        if (account instanceof CertificateOfDeposit) {
//                            CertificateOfDeposit certificateOfDeposit = (CertificateOfDeposit) account;
//
//                            String strMaturity = txtMaturity.getText();
//                            int maturity = Integer.parseInt(strMaturity);
//
//                            certificateOfDeposit.setMaturity(maturity);
//                        }
//                    }
//                    allAccounts.add(account);
//                    lstAccounts.setListData((allAccounts.toArray()));
//                    clearFields();
//                }
            }
        });

        /*
          Listens for user pressing calculate button
          loops through each object created and updates the form
         */
        btnCalc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allAccounts.forEach(account -> {account.compute();});
                lstAccounts.setListData(allAccounts.toArray());
                allAccounts.forEach(account -> {displayTotalInterest = displayTotalInterest + account.getTotalInterest();});
                txtTotalInterest.setText(String.valueOf(displayTotalInterest));
            }
        });

        /*
          When changing the combo box to Certificate of Deposit, enables the maturity field
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
                Withdraw.withdraw(withdraw);
                lstAccounts.setListData(allAccounts.toArray());
                clearFields();
            }
        });
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

    public static void main(String[] args) {
        JFrame frame = new JFrame("BankerForm");
        frame.setContentPane(new BankerForm().pnlMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
