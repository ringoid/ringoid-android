package org.byters.ringoid.controller.data.memorycache;

import org.byters.ringoid.controller.data.memorycache.listener.ICacheBlacklistListener;
import org.byters.ringoid.model.DataBlacklistPhone;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class CacheBlacklist implements ICacheBlacklist {

    private ArrayList<DataBlacklistPhone> data;
    private WeakReference<ICacheBlacklistListener> refListener;

    @Override
    public void addPhone(String phone) {
        if (isContains(phone)) return;
        if (data == null) data = new ArrayList<>();
        data.add(new DataBlacklistPhone(phone));
        notifyListeners();
    }

    private boolean isContains(String phone) {
        if (data == null) return false;
        for (DataBlacklistPhone item : data)
            if (item.isPhoneEquals(phone)) return true;
        return false;
    }

    private void notifyListeners() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().onUpdate();
    }

    @Override
    public void setListener(ICacheBlacklistListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public int getItemsNum() {
        return data == null ? 0 : data.size();
    }

    @Override
    public DataBlacklistPhone getItem(int position) {
        return data.get(position);
    }

    @Override
    public void remove(int position) {
        if (data == null || position < 0 || position >= data.size()) return;
        data.remove(position);
        notifyListeners();
    }

}
