/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

import com.google.gson.Gson;

import com.ringoid.controller.data.network.response.ResponseDataProfile;
import com.ringoid.model.DataProfile;

import java.util.ArrayList;

public class CacheLikes implements ICacheLikes {

    private final String JSON_DATA = "{ \"data\":[" +
            "{\"urls\":[{\"url\":\"f4/01.jpg\",\"ratio\":1.55},{\"url\":\"f4/02.jpg\",\"ratio\":0.946},{\"url\":\"f4/03.jpg\",\"ratio\":0.667},{\"url\":\"f4/04.jpg\",\"ratio\":0.75}]}," +
            "{\"urls\":[{\"url\":\"f5/01.jpg\",\"ratio\":1.5},{\"url\":\"f5/02.jpg\",\"ratio\":0.56},{\"url\":\"f5/03.jpg\",\"ratio\":1.5},{\"url\":\"f5/04.png\",\"ratio\":1.33},{\"url\":\"f5/05.jpg\",\"ratio\":0.665}]}," +
            "{\"urls\":[{\"url\":\"f9/01.jpg\",\"ratio\":1.5},{\"url\":\"f9/02.jpg\",\"ratio\":1.26},{\"url\":\"f9/03.jpg\",\"ratio\":0.75},{\"url\":\"f9/04.jpg\",\"ratio\":0.625}]}" +
            "]  }";

    private ArrayList<DataProfile> data;


    public CacheLikes() {
        data = new Gson().fromJson(JSON_DATA, ResponseDataProfile.class).getData();
    }

    @Override
    public int getItemsNum() {
        return data.size();
    }

    @Override
    public int getItemsNum(int adapterPosition) {
        return data.get(adapterPosition).getItemsNum();
    }

    @Override
    public void setLiked(int adapterPosition, int itemPosition) {
        data.get(adapterPosition).setLiked(itemPosition);
    }

    @Override
    public boolean isLiked(int adapterPosition, int itemPosition) {
        return data.get(adapterPosition).isLiked(itemPosition);
    }

    @Override
    public String getUrl(int adapterPosition, int itemPosition) {
        return data.get(adapterPosition).getImage(itemPosition);
    }

    @Override
    public boolean isDataExist() {
        return data != null && data.size() > 0;
    }

}
