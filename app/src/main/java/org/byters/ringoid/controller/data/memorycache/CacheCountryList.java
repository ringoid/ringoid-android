package org.byters.ringoid.controller.data.memorycache;

import org.byters.ringoid.controller.data.memorycache.listener.ICacheCountryListListener;
import org.byters.ringoid.model.DataCountry;

import java.util.ArrayList;
import java.util.WeakHashMap;

public class CacheCountryList implements ICacheCountryList {

    private ArrayList<DataCountry> data;
    private int selectedCountryIndex;

    private WeakHashMap<String, ICacheCountryListListener> listeners;

    public CacheCountryList() {
        selectedCountryIndex = -1;
        data = null;
    }

    @Override
    public void setData(ArrayList<DataCountry> data) {
        this.data = data;
        selectedCountryIndex = -1;
        notifyListenersDataUpdate();
        notifyListenersSelectedIndexChanged();
    }

    @Override
    public void setSelectedCountryIndex(int popsition) {
        this.selectedCountryIndex = popsition;
        notifyListenersSelectedIndexChanged();
    }

    private void notifyListenersSelectedIndexChanged() {
        if (listeners == null) return;
        for (String key : listeners.keySet()) {
            if (listeners.get(key) == null) continue;
            listeners.get(key).onSelectedItemUpdate(getSelectedItem());
        }
    }

    private DataCountry getSelectedItem() {
        return getItem(selectedCountryIndex);
    }

    private void notifyListenersDataUpdate() {
        if (listeners == null) return;
        for (String key : listeners.keySet()) {
            if (listeners.get(key) == null) continue;
            listeners.get(key).onDataUpdate();
        }
    }

    @Override
    public boolean isCountrySelected() {
        return selectedCountryIndex != -1;
    }

    @Override
    public void addListener(ICacheCountryListListener listener) {
        if (listeners == null) listeners = new WeakHashMap<>();
        listeners.put(listener.getClass().getSimpleName(), listener);
    }

    @Override
    public int getItemsNum() {
        return data == null ? 0 : data.size();
    }

    @Override
    public DataCountry getItem(int position) {
        if (data == null || position < 0 || position >= data.size())
            return null;
        return data.get(position);
    }
}
