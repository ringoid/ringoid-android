/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.view.presenter.callback.IPresenterSettingsFAQListener;

import java.lang.ref.WeakReference;

public class PresenterSettingsFAQ implements IPresenterSettingsFAQ {

    private WeakReference<IPresenterSettingsFAQListener> refListener;

    @Override
    public void setListener(IPresenterSettingsFAQListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void onCreateView() {

    }


}
