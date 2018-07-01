package ru.javawebinar.topjava.model;

import java.util.concurrent.atomic.AtomicInteger;

abstract public class AbstractMeal {
    private static AtomicInteger id;

    public AbstractMeal() {
        if (id == null) {
            id = new AtomicInteger(0);
        } else
            id.getAndIncrement();
    }

    public int getId() {
        return id.get();
    }
}
