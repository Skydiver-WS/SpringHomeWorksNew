package org.app.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.FileCopyUtils;

@Component
public class ContactInitializer {
    private final Map<String, Contact> contacts = new HashMap<>();
    @Value("classpath:default-contacts.txt")
    private Resource contactsFile;

    public Map<String, Contact> loadContactsFromFile() throws IOException {
        byte[] bytes = FileCopyUtils.copyToByteArray(contactsFile.getInputStream());
        String[] contacts = new String(bytes, StandardCharsets.UTF_8).split("\n");
        Arrays.stream(contacts).map(c -> {
            String [] split = c.split(";");
            String[] name = split[0].split("\\s+");
            return new Contact(name[0], name[1], name.length > 2 ? name[2] : null, split[1], split[2]);
        }).forEach(contact -> this.contacts.put(contact.getTellNumber(), contact));
        return this.contacts;
    }
}
