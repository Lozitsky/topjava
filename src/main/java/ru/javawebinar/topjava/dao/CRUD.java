package ru.javawebinar.topjava.dao;

public enum CRUD {
    C("create"),
    R("read"),
    U("update"),
    D("delete"),
    Id("mealId"),
    DT("dateTime"),
    DC("description"),
    CR("calories");

    private String description;

    private CRUD(String description){
        this.description = description;
    }

    public String action() {
        return description;
    }
}
