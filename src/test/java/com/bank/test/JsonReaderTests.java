package com.bank.test;

import com.bank.JsonReader;
import org.junit.jupiter.api.Test;

import static com.bank.JsonReader.parseAccountObject;
import static com.bank.JsonReader.readAccounts;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonReaderTests {

    final String FILE = "testAccounts.json";

    @Test
    public void create_ObjectFromtestAccountsjsonFile() {
       givenJsonTestAccountFileAndWhenCreateAccountFromJsonTestAccount();
       thenJsonTestAccountKeyValuePairsShouldBeAccountProperties();
    }

    //Given json test file, read file
    private void givenJsonTestAccountFileAndWhenCreateAccountFromJsonTestAccount() {
        readAccounts(FILE);
    }

    private void thenJsonTestAccountKeyValuePairsShouldBeAccountProperties() {
        assertEquals("Checking", readAccounts.get(0).getClass());
        assertEquals(5.0, readAccounts.get(0).getInterest());
        assertEquals(10000.0, readAccounts.get(0).getBalance());
        assertEquals(10, readAccounts.get(0).getPeriods());
        assertEquals(123455, readAccounts.get(0).getAccountNumber());
    }

}
