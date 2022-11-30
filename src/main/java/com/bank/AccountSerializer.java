package com.bank;

import com.google.gson.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;

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
}
