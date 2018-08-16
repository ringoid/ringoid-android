package com.ringoid.view.presenter;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheBlacklist;
import com.ringoid.controller.data.memorycache.listener.ICacheBlacklistListener;
import com.ringoid.view.presenter.callback.IPresenterAdapterBlacklistPhonesListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterAdapterBlacklistPhones implements IPresenterAdapterBlacklistPhones {

    private final ICacheBlacklistListener listenerCacheBlacklist;
    @Inject
    ICacheBlacklist cacheBlacklist;
    private WeakReference<IPresenterAdapterBlacklistPhonesListener> refListener;

    public PresenterAdapterBlacklistPhones() {
        ApplicationRingoid.getComponent().inject(this);
        cacheBlacklist.setListener(listenerCacheBlacklist = new ListenerCacheBlacklist());
    }

    @Override
    public int getItemsNum() {
        return cacheBlacklist.getItemsNum();
    }

    @Override
    public String getPhone(int position) {
        return cacheBlacklist.getItem(position).getPhone();
    }

    @Override
    public void onClickRemove(int position) {
        cacheBlacklist.remove(position);
    }

    @Override
    public void setListener(IPresenterAdapterBlacklistPhonesListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    private class ListenerCacheBlacklist implements ICacheBlacklistListener {
        @Override
        public void onUpdate() {
            if (refListener == null || refListener.get() == null) return;
            refListener.get().onUpdate();
        }
    }
}
