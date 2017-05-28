package com.chichkanov.tinkoff_fintech.firebase;

/**
 * Created by chichkanov on 28.05.17.
 */

interface OnTransactionComplete<T> {
    void onCommit(T result);

    void onAbort(Exception e);
}
