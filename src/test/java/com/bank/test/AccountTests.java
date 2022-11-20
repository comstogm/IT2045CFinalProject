package com.bank.test;

import com.bank.CertificateOfDeposit;
import com.bank.Checking;
import com.bank.Savings;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTests {

    @Test
    public void computeChecking_validate10Periods5Interest() {
        Checking checking = new Checking();
        checking.setInterest(5);
        checking.setPeriods(10);
        checking.setBalance(10000);
        checking.compute();
        assertEquals(16226.0, checking.getBalance(), 1.0);
    }

    @Test
    public void computeSavings_validate10Periods5Interest() {
        Savings savings = new Savings();
        savings.setInterest(5);
        savings.setPeriods(10);
        savings.setBalance(10000);
        savings.compute();
        assertEquals(16288.0, savings.getBalance(), 1.0);
    }

    @Test
    public void computeCD_validate10Periods5Interest() {
        CertificateOfDeposit certificateOfDeposit = new CertificateOfDeposit();
        certificateOfDeposit.setInterest(5);
        certificateOfDeposit.setPeriods(10);
        certificateOfDeposit.setBalance(10000);
        certificateOfDeposit.compute();
        assertEquals(16288.0, certificateOfDeposit.getBalance(), 1.0);
    }

    @Test
    public void certificateOfDeposit_setAndValidateTerm() {
        CertificateOfDeposit certificateOfDeposit = new CertificateOfDeposit();
        final int MATURITY = 5;
        certificateOfDeposit.setMaturity(5);
        assertEquals(MATURITY, certificateOfDeposit.getMaturity());
    }

    /*
    Allow the user to make a withdrawal from accounts,
    and automatically pick the account that pays the lowest interest rate to deduct the money withdrawn.
    Show a report of interest earned.
     */

    /*
    Want to write given, when, then methods and then call the three together
     */

    /*
    Given two accounts...
     */

    /*
    When withdraw this amount of money
     */

    /*
    Then account with the lowest interest will have money taken out of the account
     */

}
