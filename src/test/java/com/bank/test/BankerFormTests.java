package com.bank.test;

import com.bank.Account;
import com.bank.BankerForm;
import org.junit.jupiter.api.Test;
import java.util.PriorityQueue;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankerFormTests {

    /*
    Allow the user to make a withdrawal from accounts,
        and automatically pick the account that pays the lowest interest rate to deduct the money withdrawn.
    Show a report of interest earned.

    Test created here because action (withdrawal) can be done on any of the accounts
        and the action is the same for any of them
     */

    /*
    Want to write given, when, then methods and then call the three together
     */

    /*
    Given two accounts and place them in priority queue
     */
    @Test
    public void withdraw_decreaseAccountWithLowestRateBy500() {
        givenAccountWithInterestPoint03PercentAndBalanceOf5000();
        givenAccountWithInterestTwoPercentAndBalanceOf4000();
        whenWithdraw500();
        thenAccount2BalanceDecreasesBy500();
    }

    @Test
    public void givenAccountWithInterestPoint03PercentAndBalanceOf5000() {
        Account testaccount1 = new Account();
        testaccount1.setInterest(.03);
        testaccount1.setBalance(5000);
        BankerForm.allAccounts.offer(testaccount1);
    }

    @Test
    public void givenAccountWithInterestTwoPercentAndBalanceOf4000() {
        Account testaccount2 = new Account();
        testaccount2.setInterest(2);
        testaccount2.setBalance(4000);
        BankerForm.allAccounts.offer(testaccount2);
    }

    /*
    When withdraw this amount of money
     */

    @Test
    public void whenWithdraw500() {
        BankerForm.withdraw(500.0);
    }

    /*
    Then account with the lowest interest will have money taken out of the account.
    Account with the lowest interest rate should be the first element in the priority queue
     */
    @Test
    public void thenAccount2BalanceDecreasesBy500() {
        //assertEquals("account2", accounts.peek());
        assertEquals(4500, BankerForm.allAccounts.peek().getBalance());
    }

}
