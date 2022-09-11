package de.votuu.i18n.data;

import de.votuu.i18n.I18n;
import de.votuu.i18n.exception.I18nException;

import java.util.ArrayList;
import java.util.List;

public class Data {

    private final I18n i18n;

    private List<String> providedLang;

    public Data(I18n i18n) {
        this.i18n = i18n;

        this.providedLang = new ArrayList<>();
    }

    public boolean isProvided(String id) {
        return providedLang.contains(i18n.idFromTrimmed(id));
    }

    public void provideLang(String id) {
        id = i18n.idFromTrimmed(id);

        if(isProvided(id)) {
            throw new I18nException("'" + id + "' is already provided");
        }

        providedLang.add(id);
    }

    public List<String> providedLang() {
        return providedLang;
    }
}
