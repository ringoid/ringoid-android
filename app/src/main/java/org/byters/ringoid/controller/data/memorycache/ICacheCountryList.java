package org.byters.ringoid.controller.data.memorycache;

import org.byters.ringoid.controller.data.memorycache.listener.ICacheCountryListListener;
import org.byters.ringoid.model.DataCountry;

import java.util.ArrayList;

public interface ICacheCountryList {
    void setData(ArrayList<DataCountry> data);

    void setSelectedCountryIndex(int popsition);

    boolean isCountrySelected();

    void addListener(ICacheCountryListListener listener);

    int getItemsNum();

    DataCountry getItem(int position);
}
