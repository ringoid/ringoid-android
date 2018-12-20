package com.ringoid.controller.data.repository;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.google.gson.Gson;
import com.ringoid.ApplicationRingoid;
import com.ringoid.BuildConfig;
import com.ringoid.controller.data.memorycache.ICacheMessages;
import com.ringoid.controller.data.network.response.ResponseDataProfile;
import com.ringoid.model.DataProfile;

import java.util.ArrayList;

import javax.inject.Inject;

public class RepositoryFeedMessages implements IRepositoryFeedMessages {

    @Inject
    ICacheMessages cacheMessages;

    public RepositoryFeedMessages() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void request() {
        if (!BuildConfig.DEBUG)
            throw new UnsupportedOperationException();
        else {

            //todo MESSAGES
            String JSON_DATA = "{ \"data\":[" +
                    "{\"messagesExist\":true,\"id\":123,\"urls\":[{\"isLiked\":true,\"url\":\"f6/01.jpg\"},{\"url\":\"f6/02.jpg\"},{\"url\":\"f6/03.jpg\"},{\"url\":\"f6/04.jpg\"},{\"url\":\"f6/05.jpg\"}]}," +
                    "{\"id\":174,\"urls\":[{\"url\":\"f8/01.jpg\"},{\"isLiked\":true,\"url\":\"f8/02.jpg\"},{\"url\":\"f8/03.jpg\"}]}," +
                    "{\"id\":5253,\"urls\":[{\"url\":\"f9/01.jpg\"},{\"url\":\"f9/02.jpg\"},{\"url\":\"f9/03.jpg\"},{\"isLiked\":true,\"url\":\"f9/04.jpg\"}]}" +
                    "]  }";

            ArrayList<DataProfile> data = new Gson().fromJson(JSON_DATA, ResponseDataProfile.class).getData();
            cacheMessages.setData(data);
        }
    }
}
