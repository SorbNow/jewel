package org.jewel.model;

public enum OrderCondition {
    PROCESSING("На рассмотрении"),
    ADDED("Добавлен"),
    IN_WORK("В работе"),
    MOLDED("Отлит"),
    FINISHED("Выполнен"),
    CANCELED("Отменен");

    private String title;

    OrderCondition(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
