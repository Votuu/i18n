package de.votuu.i18n.container;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import de.votuu.i18n.I18n;
import de.votuu.i18n.exception.I18nException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.MessageFormat;

public class JsonContainer extends SimpleContainer {

    public JsonContainer(I18n i18n, String name) {
        super(i18n, name);
    }

    @Override
    public String get(String id, String key, Object... format) {
        File file = new File(i18n.idFromTrimmed(id) + ".json");

        if(!file.exists()) {
            return i18n.config().getString(MessageFormat.format("no-message", key, i18n.idFromTrimmed(id)));
        }

        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        JsonElement element = JsonParser.parseReader(reader);
        JsonObject object = element.getAsJsonObject();

        return format(object.get(key).getAsString(), format);
    }
}
