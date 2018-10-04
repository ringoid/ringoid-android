/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.content.Context;

import com.ringoid.view.presenter.callback.IPresenterAdapterChatMessagesListener;

public interface IPresenterAdapterChatMessages {
    int getItemsNum();

    boolean isMessageSelf(int position);

    String getMessage(int position);

    void setListener(IPresenterAdapterChatMessagesListener messagesListener);

    void onLongClick(Context context, int adapterPosition);

    void onClick(Context context, int adapterPosition);
}
