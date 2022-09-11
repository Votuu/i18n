package de.votuu.i18n;

import de.votuu.i18n.config.Config;
import de.votuu.i18n.container.JsonContainer;
import de.votuu.i18n.container.SimpleContainer;
import de.votuu.i18n.controller.ContainerController;
import de.votuu.i18n.data.Data;
import de.votuu.i18n.database.MongoDB;
import de.votuu.i18n.logger.I18nLogger;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class I18n {

    private static I18n i18n;

    private Config config;
    private Data data;
    private ContainerController containerController;

    private I18nLogger i18nLogger;

    private MongoDB mongoDB;

    public I18n() throws IOException {
        i18n = this;

        this.config = new Config();
        this.data = new Data(this);
        this.containerController = new ContainerController();

        this.i18nLogger = new I18nLogger();

        if(config.getBoolean("mongo", "settings")) {
            this.mongoDB = new MongoDB(this, config.getString("mongo-connection-string"));
            this.mongoDB.dataCollection().provide();
            this.mongoDB.provide();
        }else {
            File dir = new File("./i18n/");
            if(!dir.exists()) {
                dir.mkdirs();
            }

            for(File f : Objects.requireNonNull(dir.listFiles())) {
                if(f.isDirectory() && !f.isHidden()) {
                    new JsonContainer(this, f.getName());
                }
            }
        }
    }

    public String idFromTrimmed(String trimmed) {
        String[] args = trimmed.split("_");

        if(args.length != 2) {
            throw new NullPointerException(trimmed + " has the false id format. Use kk_VV");
        }

        return args[0].toLowerCase() + "_" + args[1].toUpperCase();
    }

    public Config config() {
        return config;
    }

    public Data data() {
        return data;
    }

    public ContainerController containerController() {
        return containerController;
    }

    public MongoDB mongoDB() {
        return mongoDB;
    }
    public boolean useMongoDB() {
        return mongoDB != null;
    }

    public static I18n i18n() {
        return i18n;
    }

    public static SimpleContainer get(String name) {
        return i18n().containerController().get(name);
    }
}
