package org.app.bean;

import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


@Component
public class ListContact {
    private final ContactInitializer contactInitializer;
    private final CreateNewContact contact;
    private final Map<String, Contact> contacts = new HashMap<>();

    @Autowired
    public ListContact(CreateNewContact contact, ContactInitializer contactInitializer) {
        this.contact = contact;
        this.contactInitializer = contactInitializer;

    }

    @SneakyThrows
    public void listContact() {
        contacts.putAll(contactInitializer.loadContactsFromFile());
        System.out.println("Test");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            switch (scanner.nextLine()) {
                case "add":
                    contact.newContact();
                    contacts.put(contact.getContact().getTellNumber(), contact.getContact());
                    break;
                case "list":
                    contacts.keySet().stream().toList().stream().map(contacts::get).forEach(System.out::println);
                    break;
                case "delete":
                    break;
                case "q":
                    return;
            }
        }

    }

}
