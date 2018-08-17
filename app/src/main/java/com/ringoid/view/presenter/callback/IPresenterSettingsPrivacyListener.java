/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter.callback;

public interface IPresenterSettingsPrivacyListener {
    void setPrivacyPhotos(int type);

    void setPrivacyDistance(int distanceType);

    void setPrivacyMessagesFirst(int i);

    void setPrivacyPhoneNum(int itemsNum);
}
