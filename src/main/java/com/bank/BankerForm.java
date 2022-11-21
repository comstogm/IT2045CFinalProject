package com.bank;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private Vector<Account> allAccounts = new Vector<>();

    public BankerForm() {
        
        initializeAccountTypeComboBox();

        JsonReader.readAccounts();

        /**
         * Creates a new vector from the vector provided by JsonReader.fetchAccounts
         * Then adds that vector to the allAccounts vector that is modified by this form
         */
        Vector<Account> readAccounts = JsonReader.fetchAccounts();
        allAccounts.addAll(readAccounts);

        lstAccounts.setListData(allAccounts);

        /**
         * Listens for user to click save button
         * Reads data user entered into form and calls methods from Banker to create a new account
         * if certificate of deposit account type, enables maturity field
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

                String type = cmbAccountType.getSelectedItem().toString();
                Account account = Banker.getInstance().createAccount(type);

                account.setBalance(balance);
                account.setInterest(interest);
                account.setPeriods(periods);

                if (cmbAccountType.getSelectedItem().toString().equals(Banker.CERTIFICATEOFDEPOSIT)) {
                    if (account instanceof CertificateOfDeposit) {
                        CertificateOfDeposit certificateOfDeposit = (CertificateOfDeposit) account;

                        String strMaturity = txtMaturity.getText();
                        int maturity = Integer.parseInt(strMaturity);

                        certificateOfDeposit.setMaturity(maturity);
                    }
                }
                allAccounts.add(account);
                lstAccounts.updateUI();
            }
        });

        /**
         * Listens for user pressing calculate button
         * loops through each object created and updates the form
         */
        btnCalc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allAccounts.stream().forEach(account -> {account.compute();});
                lstAccounts.updateUI();

            }
        });

        /**
         * When changing the combobox to Certificate of Deposit, enables the maturity field
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
                
            }
        });
    }

    /**
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
