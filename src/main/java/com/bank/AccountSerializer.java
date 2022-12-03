package com.bank;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;

public class AccountSerializer implements JsonSerializer<Account>, JsonDeserializer<Account> {

    private static final Logger logger = LogManager.getLogger("accountSerializer");

    /**
     *
     * @param account object
     * @param type Read in object's account type
     * @param jsonSerializationContext GSON library
     *
     */
    @Override
    public JsonElement serialize(Account account, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(account.getClass().getName()));
        result.add("properties", jsonSerializationContext.serialize(account, account.getClass()));
        return result;
    }

    /**
     *
     * @param jsonElement json object being read in
     * @param type Json type property that is read and used to create that specific account type
     * @param jsonDeserializationContext GSON library
     *
     */
    @Override
    public Account deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String accountType = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");
        try {
            return jsonDeserializationContext.deserialize(element, Class.forName("com.bank." + accountType));
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
            throw new JsonParseException(e);
        }
    }

    /**
     * Uses custom deserialize to read in json file to create account objects
     * @param inputFile account specific json file to read in and create objects from
     * @return jsonInAccounts vector that contains account objects created
     */
    public static Vector<Account> jsonReader(String inputFile) {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(inputFile));
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Account.class, new AccountSerializer());
            Gson gson = gsonBuilder.create();
            Vector<Account> jsonInAccounts = gson.fromJson(reader, new TypeToken<Vector<Account>>(){}.getType());
            reader.close();
            return jsonInAccounts;
        } catch (IOException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
}
