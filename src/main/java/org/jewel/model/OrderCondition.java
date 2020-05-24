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

    public OrderCondition changeConditionToNext(OrderCondition condition) {
        switch (condition) {
            case ADDED: {
                condition = MOLDED;
                break;
            }
            case MOLDED: {
                condition = FINISHED;
                break;
            }
            default: {
                condition = ADDED;
                break;
            }
        }
        return condition;
    }

    public OrderCondition changeConditionToPrevious(OrderCondition condition) {
        switch (condition) {
            case FINISHED: {
                condition = MOLDED;
                break;
            }
            case MOLDED: {
                condition = ADDED;
                break;
            }
            default: {
                condition = PROCESSING;
                break;
            }
        }
        return condition;
    }
}
