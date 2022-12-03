package com.bank.test;

import com.bank.AccountSerializer;

import org.junit.jupiter.api.Test;

import static com.bank.AccountSerializer.jsonReader;
import static com.bank.BankerForm.allAccounts;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonReaderTests {

    private static final String FILE = "testAccounts.json";

    @Test
    public void create_ObjectFromtestAccountsjsonFile()  {
        givenJsonTestAccountFileAndWhenCreateAccountFromJsonTestAccount();
        thenJsonTestAccountKeyValuePairsShouldBeAccountProperties();
    }

    //Given json test file, read file
    private void givenJsonTestAccountFileAndWhenCreateAccountFromJsonTestAccount() {
        allAccounts.addAll(AccountSerializer.jsonReader(FILE));
    }



    private void thenJsonTestAccountKeyValuePairsShouldBeAccountProperties() {
        assertEquals("com.bank.Checking", allAccounts.peek().getClass().getName());
        assertEquals(5.0, allAccounts.peek().getInterest());
        assertEquals(10000.0, allAccounts.peek().getBalance());
        assertEquals(10, allAccounts.peek().getPeriods());
        assertEquals(123456, allAccounts.peek().getAccountNumber());
    }

}
