package com.ringoid.controller.data.repository;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.google.gson.Gson;
import com.ringoid.ApplicationRingoid;
import com.ringoid.BuildConfig;
import com.ringoid.controller.data.memorycache.ICacheExplore;
import com.ringoid.controller.data.network.response.ResponseDataProfile;
import com.ringoid.model.DataProfile;

import java.util.ArrayList;

import javax.inject.Inject;

public class RepositoryFeedExplore implements IRepositoryFeedExplore {

    @Inject
    ICacheExplore cacheExplore;

    public RepositoryFeedExplore() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void request() {
        if (!BuildConfig.DEBUG)
            throw new UnsupportedOperationException();
        else {

            String JSON_DATA = "{ \"data\":[" +
                    "{\"urls\":[{\"id\":\"11\",\"url\":\"f1/01.jpg\"},{\"id\":\"12\",\"url\":\"f1/02.jpg\"},{\"id\":\"13\",\"url\":\"f1/03.jpg\"}]}," +
                    "{\"urls\":[{\"id\":\"21\",\"url\":\"f2/01.jpg\"},{\"id\":\"22\",\"url\":\"f2/02.jpg\"},{\"id\":\"23\",\"url\":\"f2/03.png\"}]}," +
                    "{\"urls\":[{\"id\":\"31\",\"url\":\"f3/01.jpg\"},{\"id\":\"32\",\"url\":\"f3/02.jpg\"}]}," +
                    "{\"urls\":[{\"id\":\"41\",\"url\":\"f4/01.jpg\"},{\"id\":\"42\",\"url\":\"f4/02.jpg\"},{\"id\":\"43\",\"url\":\"f4/03.jpg\"},{\"id\":\"34\",\"url\":\"f4/04.jpg\"}]}," +
                    "{\"urls\":[{\"id\":\"61\",\"url\":\"f6/01.jpg\"},{\"id\":\"62\",\"url\":\"f6/02.jpg\"},{\"id\":\"63\",\"url\":\"f6/03.jpg\"},{\"id\":\"44\",\"url\":\"f6/04.jpg\"},{\"id\":\"45\",\"url\":\"f6/05.jpg\"}]}," +
                    "{\"urls\":[{\"id\":\"51\",\"url\":\"f5/01.jpg\"},{\"id\":\"52\",\"url\":\"f5/02.jpg\"},{\"id\":\"53\",\"url\":\"f5/03.jpg\"},{\"id\":\"54\",\"url\":\"f5/04.png\"},{\"id\":\"55\",\"url\":\"f5/05.jpg\"}]}," +
                    "{\"urls\":[{\"id\":\"71\",\"url\":\"f7/01.jpg\"},{\"id\":\"72\",\"url\":\"f7/02.jpg\"},{\"id\":\"73\",\"url\":\"f7/03.jpg\"}]}," +
                    "{\"urls\":[{\"id\":\"81\",\"url\":\"f8/01.jpg\"},{\"id\":\"82\",\"url\":\"f8/02.jpg\"},{\"id\":\"83\",\"url\":\"f8/03.jpg\"}]}," +
                    "{\"urls\":[{\"id\":\"91\",\"url\":\"f9/01.jpg\"},{\"id\":\"92\",\"url\":\"f9/02.jpg\"},{\"id\":\"93\",\"url\":\"f9/03.jpg\"},{\"id\":\"94\",\"url\":\"f9/04.jpg\"}]}" +
                    "]  }";

            ArrayList<DataProfile> data = new Gson().fromJson(JSON_DATA, ResponseDataProfile.class).getData();
            cacheExplore.setData(data);
        }
    }
}
