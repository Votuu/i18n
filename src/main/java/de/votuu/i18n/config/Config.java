package de.votuu.i18n.config;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import de.votuu.i18n.exception.I18nException;

import java.io.*;

public class Config {

    private JsonElement element;
    private JsonObject object;

    public Config() throws IOException {
        File file = new File("./i18n.json");

        if(!file.exists()) {
            if(!file.createNewFile()) {
                throw new I18nException("cant create config file");
            }
        }

        JsonReader reader = new JsonReader(new FileReader(file));

        this.element = JsonParser.parseReader(reader);
        this.object = this.element.getAsJsonObject();
    }

    public JsonElement get(String key, String path) {
        return object.getAsJsonObject(path).get(key);
    }
    public JsonElement get(String key) {
        return object.get(key);
    }

    public String getString(String key, String path) {
        return get(key, path).getAsString();
    }
    public String getString(String key) {
        return get(key).getAsString();
    }

    public Integer getInteger(String key, String path) {
        return get(key, path).getAsInt();
    }
    public Integer getInteger(String key) {
        return get(key).getAsInt();
    }

    public Boolean getBoolean(String key, String path) {
        return get(key, path).getAsBoolean();
    }
    public Boolean getBoolean(String key) {
        return get(key).getAsBoolean();
    }

    public JsonElement element() {
        return element;
    }

    public JsonObject object() {
        return object;
    }
}
