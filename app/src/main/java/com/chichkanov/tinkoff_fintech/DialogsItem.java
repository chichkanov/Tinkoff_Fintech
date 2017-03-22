package com.chichkanov.tinkoff_fintech;

/**
 * Created by chichkanov on 21.03.17.
 */

public class DialogsItem {

    private String title;
    private String desc;

    public DialogsItem(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }
}
