package org.app.bean;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Component
public class CreateNewContact {
    private final Contact contact;

    @Autowired
    public CreateNewContact(Contact contact) {
        this.contact = contact;
    }


    public Contact newContact() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input name contact");
        String name = nameOrSurname(scanner);

        System.out.println("Input surname contact");
        String surname = nameOrSurname(scanner);

        System.out.println("Input patronymic contact");
        String patronymic = patronymic(scanner);

        System.out.println("Input tell number in format \"+79521234567\"");
        String telNumber = telNumber(scanner);

        System.out.println("Input email in format \"test@test.ru\"");
        String email = email(scanner);

        System.out.println("Contact added successful.");
        return new Contact(name, surname, patronymic, telNumber, email);
    }

    private String nameOrSurname(Scanner scanner) {
        String text = scanner.nextLine();
        while (text.isEmpty()) {
            System.out.println("Error. Filed name or surname is empty.");
            text = scanner.nextLine();
        }
        return text;
    }

    private String patronymic(Scanner scanner) {
        String text = scanner.nextLine();
        return !text.isEmpty() ? text : null;
    }

    private String telNumber(Scanner scanner) {
        String text = scanner.nextLine().replaceFirst("^\\+?([78])", "+7");
        while (!checkTelNumberFormat(text)) {
            System.out.println("Error. Incorrect tel number format.");
            text = scanner.nextLine();
        }
        return text;
    }

    private String email(Scanner scanner) {
        String text = scanner.nextLine();
        while (!checkEmail(text)) {
            System.out.println("Error. Incorrect email format.");
            text = scanner.nextLine();
        }
        return text;
    }


    private boolean checkTelNumberFormat(String number) {
        Pattern pattern = Pattern.compile("(\\+7)(\\d{10})");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    private boolean checkEmail(String email) {
        Pattern pattern = Pattern.compile(".+@.+\\.\\w+");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
