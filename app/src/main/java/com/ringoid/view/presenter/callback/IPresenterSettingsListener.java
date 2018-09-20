package com.ringoid.view.presenter.callback;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

public interface IPresenterSettingsListener {
    void setPrivacyPhotos(int type);

    void setPrivacyDistance(int distanceId);

    void setPrivacyPhoneNum(int itemsNum);

    void showDialogPrivacyPhotos(int selected);

    void setPrivacyLikes(int selected);
}
