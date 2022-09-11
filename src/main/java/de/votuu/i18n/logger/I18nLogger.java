package de.votuu.i18n.logger;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class I18nLogger {

    private Logger i18n;
    private Logger second;

    private File file;

    public I18nLogger() {
        this.i18n = Logger.getLogger("votuu:i18n");

        //handleFile();
    }

    public void log(String message) {
        i18n.log(Level.INFO, "(" + date() + ") [i18n] " + message);

        if(second != null) {
            second.log(Level.INFO, "[i18n] " + message);
        }
    }

    public void log(String pre, String message) {
        log(pre + ": " + message);
    }

    public void setLogger() {

    }

    private String date() {
        var currentTime = LocalDateTime.now();

        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return formatter.format(currentTime);
    }

    public void handleFile() {
        File dir = new File("./i18n-log/");

        if(!dir.exists()) {
            dir.mkdirs();
        }

        file = new File(dir, "latest.log");

        if(!file.exists()) {
            file.delete();
        }

        try {
            TimeUnit.SECONDS.sleep(5);
            file.createNewFile();

            FileHandler fileHandler = new FileHandler(file.getPath());
            i18n.addHandler(fileHandler);

            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
