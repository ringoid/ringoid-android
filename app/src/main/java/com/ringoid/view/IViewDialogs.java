package com.ringoid.view;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.Context;

import com.ringoid.view.presenter.PresenterActivityMain;
import com.ringoid.view.ui.dialog.callback.IDialogChatComposeListener;
import com.ringoid.view.ui.dialog.callback.IDialogErrorAppVersionListener;

public interface IViewDialogs {
    void showDialogExplore();

    void set(Context context);

    void showDialogLikes();

    void showDialogLikeNoPhoto();

    void showDialogChatCompose(IDialogChatComposeListener listener);

    void showDialogMessage(int messageId);

    void showDialogErrorUnknown();

    void showDialogErrorAppVersion(IDialogErrorAppVersionListener listener);
}
