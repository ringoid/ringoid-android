package org.byters.ringoid.view.presenter;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.controller.data.memorycache.ICacheCountryList;
import org.byters.ringoid.controller.data.memorycache.listener.ICacheCountryListListener;
import org.byters.ringoid.model.DataCountry;
import org.byters.ringoid.view.presenter.callback.IPresenterAdapterCountryListListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterAdapterCountryList implements IPresenterAdapterCountryList {

    private final ListenerCache listenerCache;
    @Inject
    ICacheCountryList cacheCountryList;
    private WeakReference<IPresenterAdapterCountryListListener> refListener;

    public PresenterAdapterCountryList() {
        ApplicationRingoid.getComponent().inject(this);
        cacheCountryList.addListener(listenerCache = new ListenerCache());
    }

    @Override
    public int getItemsNum() {
        return cacheCountryList.getItemsNum();
    }

    @Override
    public String getItemTitle(int position) {
        DataCountry country = cacheCountryList.getItem(position);
        return country == null ? null : country.getTitle();
    }

    @Override
    public void setLstener(IPresenterAdapterCountryListListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void onClickItem(int position) {
        cacheCountryList.setSelectedCountryIndex(position);
    }

    private void updateView() {
        if (refListener == null || refListener.get() == null) refListener.get().onUpdate();
    }

    private class ListenerCache implements ICacheCountryListListener {
        @Override
        public void onDataUpdate() {
            updateView();
        }

        @Override
        public void onSelectedItemUpdate(DataCountry country) {

        }
    }
}
