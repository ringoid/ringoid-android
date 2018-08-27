/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.view.presenter.callback.IPresenterAdapterBlacklistPhonesListener;

public interface IPresenterAdapterBlacklistPhones {
    int getItemsNum();

    String getPhone(int position);

    void onClickRemove(int position);

    void setListener(IPresenterAdapterBlacklistPhonesListener listener);

    String getPhoneCode(int position);
}
