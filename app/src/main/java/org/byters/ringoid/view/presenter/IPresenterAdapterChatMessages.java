package org.byters.ringoid.view.presenter;

import org.byters.ringoid.view.presenter.callback.IPresenterAdapterChatMessagesListener;

public interface IPresenterAdapterChatMessages {
    int getItemsNum();

    boolean isMessageSelf(int position);

    String getUrl(int position);

    String getMessage(int position);

    void setListener(IPresenterAdapterChatMessagesListener messagesListener);

    boolean isMessageText(int position);

    boolean isMessageSmile(int position);

    int getMessageSmile(int position);
}
