package com.chichkanov.tinkoff_fintech;

public class ConversationItem {

    private int type;
    private String message;
    private String date;

    public ConversationItem(String message, String date, int type) {
        this.type = type;
        this.message = message;
        this.date = date;
    }

    public ConversationItem(String message, int type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public int getType() {
        return type;
    }

    public String getDate() {
        return date;
    }
}
