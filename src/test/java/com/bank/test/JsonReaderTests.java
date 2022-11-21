package com.bank.test;

import com.bank.JsonReader;
import org.junit.jupiter.api.Test;

import static com.bank.JsonReader.parseAccountObject;
import static com.bank.JsonReader.readAccounts;

public class JsonReaderTests {

    //Given json test file, read file
    @Test
    private void givenJsonTestAccountFile() {
        readAccounts();
    }

    @Test
    private void whenCreateAccountFromJsonTestAccount() {
        parseAccountObject();
    }

    @Test
    private void thenJsonTestAccountKeyValuePairsShouldBeAccountProperties() {

    }

}
