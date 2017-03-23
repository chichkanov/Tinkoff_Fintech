package com.chichkanov.tinkoff_fintech;

/**
 * Created by chichkanov on 21.03.17.
 */

public class ConversationItem {

    private int type;
    private String message;

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
}
