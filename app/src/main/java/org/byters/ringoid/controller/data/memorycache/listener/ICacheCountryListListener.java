package org.byters.ringoid.controller.data.memorycache.listener;

import org.byters.ringoid.model.DataCountry;

public interface ICacheCountryListListener {
    void onDataUpdate();

    void onSelectedItemUpdate(DataCountry country);
}
