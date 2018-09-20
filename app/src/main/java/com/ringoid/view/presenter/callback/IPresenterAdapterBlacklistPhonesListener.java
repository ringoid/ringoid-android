/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter.callback;

import com.ringoid.model.DataBlacklistPhone;

public interface IPresenterAdapterBlacklistPhonesListener {
    void onUpdate();

    void showDialogRemove(DataBlacklistPhone item);
}
