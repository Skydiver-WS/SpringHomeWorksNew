package org.app.bean;

import java.util.Map;

public interface Initializer {
    Map<String, Contact> loadContactsFromFile();
}
