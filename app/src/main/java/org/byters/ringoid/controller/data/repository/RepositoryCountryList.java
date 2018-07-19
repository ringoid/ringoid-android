package org.byters.ringoid.controller.data.repository;

import com.google.gson.Gson;

import org.byters.mockserver.MockServer;
import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.controller.data.memorycache.ICacheCountryList;
import org.byters.ringoid.controller.data.network.response.ResponseCountryList;
import org.byters.ringoid.model.DataCountry;

import java.util.ArrayList;

import javax.inject.Inject;

public class RepositoryCountryList implements IRepositoryCountryList {

    @Inject
    ICacheCountryList cacheCountryList;

    public RepositoryCountryList() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void request() {
        ArrayList<DataCountry> data = new Gson().fromJson(MockServer.requestCountryList(), ResponseCountryList.class).getData();
        cacheCountryList.setData(data);
    }
}
