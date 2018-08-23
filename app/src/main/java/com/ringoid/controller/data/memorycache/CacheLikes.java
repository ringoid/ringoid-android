/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

import com.google.gson.Gson;

import com.ringoid.controller.data.network.response.ResponseDataProfile;
import com.ringoid.model.DataProfile;

import java.util.ArrayList;

public class CacheLikes implements ICacheLikes {

    private final String JSON_DATA = "{ \"data\":[" +
            "{\"urls\":[{\"id\":\"111\",\"url\":\"f4/01.jpg\"}, {\"id\":\"112\",\"url\":\"f4/02.jpg\"},{\"url\":\"f4/03.jpg\"}, {\"id\":\"113\",\"url\":\"f4/04.jpg\"}]}," +
            "{\"urls\":[{\"id\":\"121\",\"url\":\"f5/01.jpg\"}, {\"id\":\"122\",\"url\":\"f5/02.jpg\"},{\"url\":\"f5/03.jpg\"}, {\"id\":\"123\",\"url\":\"f5/04.png\"},{\"id\":\"124\",\"url\":\"f5/05.jpg\"}]}," +
            "{\"urls\":[{\"id\":\"131\",\"url\":\"f9/01.jpg\"}, {\"id\":\"132\",\"url\":\"f9/02.jpg\"},{\"url\":\"f9/03.jpg\"}, {\"id\":\"133\",\"url\":\"f9/04.jpg\"}]}" +
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

    @Override
    public void changeLiked(int adapterPosition, int itemPosition) {
        data.get(adapterPosition).changeLiked(itemPosition);
    }

    @Override
    public String getItemId(int adapterPosition, int itemPosition) {
        return data.get(adapterPosition).getImageId(itemPosition);
    }

}
