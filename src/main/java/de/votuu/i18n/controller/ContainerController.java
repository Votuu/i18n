package de.votuu.i18n.controller;

import de.votuu.i18n.container.SimpleContainer;

import java.util.HashMap;
import java.util.Map;

public class ContainerController {

    private Map<String, SimpleContainer> cache;

    public ContainerController() {
        this.cache = new HashMap<>();
    }

    public SimpleContainer get(String key) {
        return cache.get(key.toLowerCase());
    }

    public void provide(String key, SimpleContainer container) {
        cache.put(key.toLowerCase(), container);
    }
}
