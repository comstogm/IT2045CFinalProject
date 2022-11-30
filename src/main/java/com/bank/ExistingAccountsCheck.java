package com.bank;

import java.util.HashSet;
import java.util.Set;

public class ExistingAccountsCheck {

    private static ExistingAccountsCheck existingAccountsCheck = null;

    public static ExistingAccountsCheck getInstance() {
        if (existingAccountsCheck == null) {
            existingAccountsCheck = new ExistingAccountsCheck();
        }
        return existingAccountsCheck;
    }

    private Set<Integer> existingAccounts = new HashSet<>();

    private ExistingAccountsCheck() {
        BankerForm.allAccounts.stream().forEach(account -> {existingAccounts.add(account.getAccountNumber());});
    }

    public Boolean accountCheck(Integer input) {

        Boolean result = true;

        for (int accountnum : existingAccounts) {
            if (accountnum == input) {
                result = false;
                break;
            }
        } return result;
    }
}
