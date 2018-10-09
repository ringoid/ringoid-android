package com.ringoid.view.presenter;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

public interface IPresenterItemImageLikeable {

    boolean isDialogHiddenShow();

    void onClickOK(boolean b);

    void onClickSettings(boolean b);

    boolean isUserNew();

    void showDialogNoPhoto();
}
