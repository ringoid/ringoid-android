package com.ringoid.view.presenter;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

public interface IPresenterItemImageLikeable {
    boolean isHiddenMode();

    boolean isDialogHiddenShow();

    void onClickOK(boolean b);

    void onClickPrivacy(boolean b);

    boolean isPhotoSelfExist();

    void showDialogNoPhoto();
}
