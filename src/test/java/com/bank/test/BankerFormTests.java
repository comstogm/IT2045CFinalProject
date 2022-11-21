package com.bank.test;

import com.bank.Account;
import org.junit.jupiter.api.Test;

import java.util.PriorityQueue;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankerFormTests {

    private Account account;
    private Queue<Account> accounts = new PriorityQueue<>();

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
    private void givenAccountWithInterestPoint03PercentAndBalanceOf5000() {
        Account account1 = new Account();
        account.setInterest(.03);
        account.setBalance(5000);
        accounts.offer(account1)
    }

    @Test
    private void givenAccountWithInterestTwoPercentAndBalanceOf4000() {
        Account account2 = new Account();
        account.setInterest(2);
        account.setBalance(4000);
        accounts.offer(account2)
    }

    /*
    When withdraw this amount of money
     */

    @Test
    private void whenWithdraw500() {
        accounts.withdraw(500);
    }

    /*
    Then account with the lowest interest will have money taken out of the account.
    Account with the lowest interest rate should be the first element in the priority queue
     */

    private void thenAccount2BalanceDecreasesBy500() {
        //assertEquals("account2", accounts.peek());
        assertEquals(3500, accounts.peek().getBalance());
    }
}