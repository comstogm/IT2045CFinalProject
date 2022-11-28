package com.bank;

public class accountFactory {

    /**
     * Factory design pattern to create account objects
     * @param //Parameter to pass account type into factory command
     * @return
     * @throws Exception
     */
    public static Account createAccountCommand(String name) throws Exception {
        Account account = Class.forName("com.bank." + name).asSubclass(Account.class).getDeclaredConstructor().newInstance();
        return account;
    }
}