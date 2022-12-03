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


    @Override
    public JsonElement serialize(Account account, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(account.getClass().getName()));
        result.add("properties", jsonSerializationContext.serialize(account, account.getClass()));
        return result;
    }

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
