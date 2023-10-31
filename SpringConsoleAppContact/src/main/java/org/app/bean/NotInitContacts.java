package org.app.bean;

import java.util.HashMap;
import java.util.Map;


public class NotInitContacts implements Initializer{
    @Override
    public Map<String, Contact> loadContactsFromFile() {
        System.out.println("Active profile \"Not initializer contacts\"");
        return new HashMap<>();
    }
}
