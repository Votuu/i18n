package de.votuu.i18n.database.collection;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import de.votuu.i18n.I18n;
import de.votuu.i18n.database.MongoDB;
import org.bson.Document;

public class DataCollection {

    private final I18n i18n;

    private final String collectionName;
    private final MongoDB mongoDB;

    public DataCollection(I18n i18n, MongoDB mongoDB) {
        this.i18n = i18n;

        this.collectionName = "data";
        this.mongoDB = mongoDB;
    }

    public void provide() {
        MongoCollection<Document> collection = mongoDB.database().getCollection(collectionName);

        for(Document document : collection.find()) {
            if(document == null) {
                continue;
            }

            if(!document.containsKey("id")) {
                continue;
            }

            if(!document.containsKey("name")) {
                continue;
            }

            i18n.data().provideLang(document.getString("id"));
        }
    }

    public Document getDocument(String filterKey, Object filterValue) {
        MongoCollection<Document> collection = mongoDB.database().getCollection(collectionName);

        for(Document document : collection.find(Filters.eq(filterKey, filterValue))) {
            if(document == null) {
                continue;
            }

            return document;
        }
        return null;
    }
}
