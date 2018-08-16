package com.ringoid.controller.data.memorycache;

import com.google.gson.Gson;

import com.ringoid.model.DataProfile;

public class CacheProfile implements ICacheProfile {

    private final String JSON_DATA =
            "{\"urls\":[" +
                    "{\"url\":\"m1/01.jpg\",\"ratio\":0.67,\"likes\":312}," +
                    "{\"url\":\"m1/02.jpg\",\"ratio\":1.22,\"likes\":54}," +
                    "{\"url\":\"m1/03.jpg\",\"ratio\":0.75,\"likes\":0}" +
                    "]}";

    private DataProfile data;

    public CacheProfile() {
        data = new Gson().fromJson(JSON_DATA, DataProfile.class);
    }

    @Override
    public int getItemsNum() {
        return data == null ? 0 : data.getItemsNum();
    }

    @Override
    public int getLikesNum(int position) {
        return data.getLikes(position);
    }

    @Override
    public String getImage(int pos) {
        return data.getImage(pos);
    }
}
