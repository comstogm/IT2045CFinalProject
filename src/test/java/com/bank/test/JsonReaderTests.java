package com.bank.test;

import com.bank.Account;
import com.bank.AccountSerializer;

import org.junit.jupiter.api.Test;


import java.nio.file.Paths;

import static com.bank.AccountSerializer.jsonReader;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonReaderTests {

    final String FILE = "testAccounts.json";

    @Test
    public void create_ObjectFromtestAccountsjsonFile()  {
       givenJsonTestAccountFileAndWhenCreateAccountFromJsonTestAccount();
       thenJsonTestAccountKeyValuePairsShouldBeAccountProperties();
    }

    //Given json test file, read file
    private void givenJsonTestAccountFileAndWhenCreateAccountFromJsonTestAccount() {
        jsonReader();
   }

    private void thenJsonTestAccountKeyValuePairsShouldBeAccountProperties() {
        assertEquals("com.bank.Checking", jsonReader().get(0).getClass().getName());
       assertEquals(5.0, jsonReader().get(0).getInterest());
        assertEquals(10000.0, jsonReader().get(0).getBalance());
        assertEquals(10, jsonReader().get(0).getPeriods());
        assertEquals(123456, jsonReader().get(0).getAccountNumber());
    }

}
