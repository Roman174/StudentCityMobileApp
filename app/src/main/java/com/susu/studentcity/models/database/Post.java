package com.susu.studentcity.models.database;

import java.io.Serializable;

public class Post implements Serializable {

    public static final String MAIN_HOSTEL_MANAGER_POST  = "";
    public static final String MAIN_STUDENT_MANAGER_POST = "";
    public static final String MAIN_CULTURE_MANAGER_POST = "";
    public static final String MAIN_SPORT_MANAGER_POST   = "";

    public static final String HOSTEL_MANAGER_POST  = "Заведующий";
    public static final String STUDENT_MANAGER_POST = "Председатель";
    public static final String CULTURE_MANAGER_POST = "Культорг";
    public static final String SPORT_MANAGER_POST   = "Спорторг";

    private int id;
    private String title;

    public String getTitle() {
        return title;
    }
}
