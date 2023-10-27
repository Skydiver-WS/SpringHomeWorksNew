package org.app.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Component
@Getter
@Setter
public class Contact {
    private String name;
    private String surname;
    private String patronymic;
    private String tellNumber;
    private String email;

    @Override
    public String toString() {
        return patronymic == null ? name + " " + surname + ";"
                + tellNumber + ";"
                + email
                : name + " " + surname + " " + patronymic + ";"
                + tellNumber + ";"
                + email;
    }

}
