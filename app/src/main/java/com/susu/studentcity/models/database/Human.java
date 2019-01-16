package com.susu.studentcity.models.database;

import java.io.Serializable;

public class Human implements Serializable {
    private int id;
    private String surname;
    private String firstname;
    private String patronymic;

    public String getSurname() {
        return surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getPatronymic() {
        return patronymic;
    }
}
