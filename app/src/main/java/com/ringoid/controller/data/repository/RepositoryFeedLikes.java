package com.ringoid.controller.data.repository;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.google.gson.Gson;
import com.ringoid.ApplicationRingoid;
import com.ringoid.BuildConfig;
import com.ringoid.controller.data.memorycache.ICacheLikes;
import com.ringoid.controller.data.network.response.ResponseDataProfile;
import com.ringoid.model.DataProfile;

import java.util.ArrayList;

import javax.inject.Inject;

public class RepositoryFeedLikes implements IRepositoryFeedLikes {

    @Inject
    ICacheLikes cacheLikes;

    public RepositoryFeedLikes() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void request() {
        if (!BuildConfig.DEBUG)
            throw new UnsupportedOperationException();
        else {

            //todo LIKES YOU
            String JSON_DATA = "{ \"data\":[" +
                    "{\"id\":\"123\", \"urls\":[{\"isLiked\":true,\"id\":\"111\",\"url\":\"f4/01.jpg\"}, {\"id\":\"112\",\"url\":\"f4/02.jpg\"},{\"url\":\"f4/03.jpg\"}, {\"id\":\"113\",\"url\":\"f4/04.jpg\"}]}," +
                    "{\"id\":\"1234\",\"urls\":[{\"id\":\"121\",\"url\":\"f5/01.jpg\"}, {\"id\":\"122\",\"url\":\"f5/02.jpg\"},{\"url\":\"f5/03.jpg\"}, {\"id\":\"123\",\"url\":\"f5/04.png\"},{\"id\":\"124\",\"url\":\"f5/05.jpg\"}]}," +
                    "{\"id\":\"1235\",\"urls\":[{\"id\":\"131\",\"url\":\"f9/01.jpg\"}, {\"id\":\"132\",\"url\":\"f9/02.jpg\"},{\"url\":\"f9/03.jpg\"}, {\"id\":\"133\",\"url\":\"f9/04.jpg\"}]}" +
                    "]  }";

            ArrayList<DataProfile> data = new Gson().fromJson(JSON_DATA, ResponseDataProfile.class).getData();
            cacheLikes.setData(data);
        }
    }
}
