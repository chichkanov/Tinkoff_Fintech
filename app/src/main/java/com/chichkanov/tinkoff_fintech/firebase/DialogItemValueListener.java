package com.chichkanov.tinkoff_fintech.firebase;

import com.chichkanov.tinkoff_fintech.models.DialogsItem;

import java.util.List;

interface DialogItemValueListener {
    void onValue(List<DialogsItem> items);
}
