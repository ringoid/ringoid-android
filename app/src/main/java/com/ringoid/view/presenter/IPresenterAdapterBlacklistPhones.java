package com.ringoid.view.presenter;

import com.ringoid.view.presenter.callback.IPresenterAdapterBlacklistPhonesListener;

public interface IPresenterAdapterBlacklistPhones {
    int getItemsNum();

    String getPhone(int position);

    void onClickRemove(int position);

    void setListener(IPresenterAdapterBlacklistPhonesListener listener);
}
