package com.bank;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class JsonReader {
    private static Vector<Account> readAccounts = new Vector<>();

    public static void readAccounts() {
        //Using org.json.simple.parser to read from the JSON file
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader("accounts.json"))
        {
            Object obj = parser.parse(reader);

            JSONArray accountList = (JSONArray) obj;

            accountList.forEach( newReadAccount -> parseAccountObject((JSONObject) newReadAccount));


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void parseAccountObject (JSONObject newReadAccount) {
        //gets the object from the JSON file and converts to correct format
        JSONObject jsonAccount = (JSONObject) newReadAccount.get("properties");

        String accountType = (String) newReadAccount.get("type");
        if (accountType.equals("CertificateOfDeposit")) {
            accountType = "Certificate of Deposit";
        }
        Double balance = (Double) jsonAccount.get("balance");
        Double interest = (Double) jsonAccount.get("interest");
        Long lPeriods = (long) jsonAccount.get("periods");
        Integer periods = lPeriods.intValue();
        Integer maturity = null;
        if (accountType.equals("Certificate of Deposit")) {
            Long lMaturity = (long) jsonAccount.get("maturity");
            maturity = lMaturity.intValue();
        }

        //Creates a new object of type specified in Json file and assigns values to it
        Account account = Banker.getInstance().createAccount(accountType);
        account.setBalance(balance);
        account.setInterest(interest);
        account.setPeriods(periods);

        if (account instanceof CertificateOfDeposit) {
            ((CertificateOfDeposit) account).setMaturity(maturity);
        }


        //adds new object to the readAccounts vector
        readAccounts.add(account);
    }

    /*
    returns the entire vector created from the Json file.
    Could be a better way to give this to the BankerForm
     */
    public static Vector<Account> fetchAccounts() {
        return readAccounts;
    }
}
