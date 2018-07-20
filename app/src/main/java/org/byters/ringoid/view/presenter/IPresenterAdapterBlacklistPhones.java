package org.byters.ringoid.view.presenter;

import org.byters.ringoid.view.presenter.callback.IPresenterAdapterBlacklistPhonesListener;

public interface IPresenterAdapterBlacklistPhones {
    int getItemsNum();

    String getPhone(int position);

    void onClickRemove(int position);

    void onClickItem(int position);

    void setListener(IPresenterAdapterBlacklistPhonesListener listener);

    boolean isSelected(int position);
}
