package org.byters.ringoid.view.presenter;

import org.byters.ringoid.view.presenter.callback.IPresenterAdapterCountryListListener;

public interface IPresenterAdapterCountryList {
    int getItemsNum();

    String getItemTitle(int position);

    void setLstener(IPresenterAdapterCountryListListener listener);

    void onClickItem(int position);
}
