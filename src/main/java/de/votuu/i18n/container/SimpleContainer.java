package de.votuu.i18n.container;

import de.votuu.i18n.I18n;

import java.text.MessageFormat;

public abstract class SimpleContainer {

    protected final I18n i18n;
    protected final String name;

    public SimpleContainer(I18n i18n, String name) {
        this.i18n = i18n;
        this.name = name;

        i18n.containerController().provide(name, this);
    }

    public abstract String get(String id, String key, Object... format);
    public String format(String message, Object... format) {
        return MessageFormat.format(message, format);
    }
}
