package org.app.bean;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.FileCopyUtils;


public class ContactInitializer implements Initializer {
    private final Map<String, Contact> contacts = new HashMap<>();
    @Value("classpath:default-contacts.txt")
    private Resource contactsFile;

    @SneakyThrows
    @Override
    public Map<String, Contact> loadContactsFromFile() {
        System.out.println("Active profile \"Initializer contacts\"");
        byte[] bytes = FileCopyUtils.copyToByteArray(contactsFile.getInputStream());
        String[] contacts = new String(bytes, StandardCharsets.UTF_8).split("\n");
        Arrays.stream(contacts).map(c -> {
            String[] split = c.split(";");
            String[] name = split[0].split("\\s+");
            return new Contact(name[0], name[1], name.length > 2 ? name[2] : null, split[1], split[2]);
        }).forEach(contact -> this.contacts.put(contact.getTellNumber(), contact));
        return this.contacts;
    }
}
