/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.data.memorycache;

import com.google.gson.Gson;

import com.ringoid.controller.data.network.response.ResponseDataProfile;
import com.ringoid.model.DataProfile;

import java.util.ArrayList;

public class CacheMessages implements ICacheMessages {

    private final String JSON_DATA = "{ \"data\":[" +
            "{\"id\":123,\"urls\":[{\"url\":\"f6/01.jpg\"},{\"url\":\"f6/02.jpg\"},{\"url\":\"f6/03.jpg\"},{\"url\":\"f6/04.jpg\"},{\"url\":\"f6/05.jpg\"}]}," +
            "{\"id\":174,\"urls\":[{\"url\":\"f8/01.jpg\"},{\"url\":\"f8/02.jpg\"},{\"url\":\"f8/03.jpg\"}]}," +
            "{\"id\":5253,\"urls\":[{\"url\":\"f9/01.jpg\"},{\"url\":\"f9/02.jpg\"},{\"url\":\"f9/03.jpg\"},{\"url\":\"f9/04.jpg\"}]}" +
            "]  }";

    private ArrayList<DataProfile> data;
    private String selectedUserId;

    public CacheMessages() {
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
    public String getUrl(int adapterPosition, int itemPosition) {
        return data.get(adapterPosition).getImage(itemPosition);
    }

    @Override
    public void setUserSelected(int position) {
        this.selectedUserId = data.get(position).getId();
    }

    @Override
    public String getUrlSelectedUser() {
        DataProfile user = getSelectedUser();
        return user == null ? null : user.getImage(0);
    }

    @Override
    public boolean isDataExist() {
        return data != null && data.size() > 0;
    }

    private DataProfile getSelectedUser() {
        if (data == null) return null;
        for (DataProfile item : data) {
            if (item.getId().equals(selectedUserId))
                return item;
        }
        return null;
    }
}
