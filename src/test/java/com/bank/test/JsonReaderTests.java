package com.bank.test;

import com.bank.JsonReader;
import org.junit.jupiter.api.Test;

import static com.bank.JsonReader.parseAccountObject;
import static com.bank.JsonReader.readAccounts;

public class JsonReaderTests {

    final String FILE = "testAccounts.json";

    //Given json test file, read file
    private void givenJsonTestAccountFile() {
        readAccounts(FILE);
    }

    @Test
    private void whenCreateAccountFromJsonTestAccount() {
        //parseAccountObject();
    }

    @Test
    private void thenJsonTestAccountKeyValuePairsShouldBeAccountProperties() {

    }

}
