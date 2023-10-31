package org.app.bean;


import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class ListContact {
    private final Initializer initializer;
    private final CreateNewContact newContact;
    private final Map<String, Contact> contacts = new HashMap<>();

    @Autowired
    public ListContact(CreateNewContact newContact, Initializer initializer) {
        this.newContact = newContact;
        this.initializer = initializer;
    }

    @SneakyThrows
    public void listContact() {
        contacts.putAll(initializer.loadContactsFromFile());
        System.out.println("""
                List commands in program:
                \tadd - add new contact;
                \tlist - list contacts;
                \tdelete - delete contact;
                \tq - exit the program""");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            switch (scanner.nextLine().trim()) {
                case "add":
                    Contact contact = newContact.newContact();
                    contacts.put(contact.getTellNumber(), contact);
                    break;
                case "list":
                    if(contacts.isEmpty()){
                        System.out.println("List contacts is Empty");
                    }else {
                        contacts.keySet().stream().toList().stream().map(contacts::get).forEach(System.out::println);
                    }
                    break;
                case "delete":
                    System.out.println("Input tell number or email.");
                    String dataDelete = scanner.nextLine().trim();
                    Pattern pattern = Pattern.compile(".+@.+\\.\\w+");
                    Matcher matcher = pattern.matcher(dataDelete);
                    if (matcher.matches()) {
                        contacts.entrySet().removeIf(entry -> {
                            Contact contact1 = entry.getValue();
                            return dataDelete.equals(contact1.getEmail());
                        });
                    } else {
                        contacts.remove(dataDelete);
                    }
                    String result = contacts.containsKey(dataDelete) || contacts.values().stream().allMatch(c -> c.getEmail().equals(dataDelete)) ?
                            "Contact " + dataDelete + " delete failed" : "Contact " + dataDelete + " delete successful";
                    System.out.println(result);
                    break;
                case "q":
                    return;
                default:
                    System.out.println("Command not found.");
                    break;
            }
        }

    }

}
