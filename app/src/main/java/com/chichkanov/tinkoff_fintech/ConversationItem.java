package com.chichkanov.tinkoff_fintech;

/**
 * Created by chichkanov on 21.03.17.
 */

public class ConversationItem {

    public static final int MESSAGE_YOU = 0;
    public static final int MESSAGE_MATE = 1;

    private int type;
    private String yourMessage;
    private String mateMessage;

    public ConversationItem(String yourMessage, String mateMessage, int type) {
        this.yourMessage = yourMessage;
        this.mateMessage = mateMessage;
        this.type = type;
    }

    public String getYourMessage() {
        return yourMessage;
    }

    public String getMateMessage() {
        return mateMessage;
    }

    public int getType() {
        return type;
    }
}
