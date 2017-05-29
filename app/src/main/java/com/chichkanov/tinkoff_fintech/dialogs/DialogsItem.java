package com.chichkanov.tinkoff_fintech.dialogs;

public class DialogsItem {

    private String title;
    private String desc;
    private String date;

    public DialogsItem() {
        //for firebase
    }

    public DialogsItem(String title, String desc, String date) {
        this.title = title;
        this.desc = desc;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getDate() {
        return date;
    }
}
