package de.votuu.i18n.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.votuu.i18n.I18n;
import de.votuu.i18n.container.MongoContainer;
import de.votuu.i18n.database.collection.DataCollection;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MongoDB {

    private final I18n i18n;

    private MongoClient client;
    private MongoDatabase database;

    private DataCollection dataCollection;

    public MongoDB(I18n i18n, String connectionString) {
        this.i18n = i18n;

        Logger logger = Logger.getLogger("org.mongodb.driver");
        logger.setLevel(Level.SEVERE);

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .retryWrites(true)
                .retryReads(true)
                .build();

        this.client = MongoClients.create(settings);
        this.database = client().getDatabase("i18n");

        this.dataCollection = new DataCollection(i18n, this);
    }

    public void provide() {
        database.listCollectionNames().into(new ArrayList<>()).forEach(name -> {
            new MongoContainer(i18n, name);
        });
    }

    public MongoClient client() {
        return client;
    }

    public MongoDatabase database() {
        return database;
    }

    public DataCollection dataCollection() {
        return dataCollection;
    }
}
