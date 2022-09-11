package de.votuu.i18n.container;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import de.votuu.i18n.I18n;
import de.votuu.i18n.database.MongoDB;
import de.votuu.i18n.exception.I18nException;
import org.bson.Document;

import javax.print.Doc;
import java.text.MessageFormat;

public class MongoContainer extends SimpleContainer {

    private MongoDB mongoDB;

    public MongoContainer(I18n i18n, String name) {
        super(i18n, name);

        this.mongoDB = i18n.mongoDB();
    }

    @Override
    public String get(String id, String key, Object... format) {
        Document document = findDocument(id, mongoDB.database().getCollection(name));

        if(document == null) {
            return i18n.config().getString(MessageFormat.format("no-message", key, i18n.idFromTrimmed(id)));
        }

        if(document.containsKey(key)) {
            return i18n.config().getString(MessageFormat.format("no-message", key, i18n.idFromTrimmed(id)));
        }

        return format(document.getString(key), format);
    }

    private Document findDocument(String id, MongoCollection<Document> collection) {
        for(Document document : collection.find(Filters.eq("id", i18n.idFromTrimmed(id)))) {
            return document;
        }

        return null;
    }
}
