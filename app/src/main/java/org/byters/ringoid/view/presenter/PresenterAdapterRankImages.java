package org.byters.ringoid.view.presenter;

import com.google.gson.Gson;

import org.byters.ringoid.controller.data.network.response.ResponseDataProfile;
import org.byters.ringoid.model.DataProfile;

import java.util.ArrayList;

public class PresenterAdapterRankImages implements IPresenterAdapterRankImages {

    private final String JSON_DATA = "{ \"data\":[" +
            "{\"urls\":[\"f1/01.jpg\",\"f1/02.jpg\",\"f1/03.jpg\"]}," +
            "{\"urls\":[\"f2/01.jpg\",\"f2/02.jpg\",\"f2/03.png\"]}," +
            "{\"urls\":[\"f3/01.jpg\",\"f3/02.jpg\"]}," +
            "{\"urls\":[\"f4/01.jpg\",\"f4/02.jpg\",\"f4/03.jpg\",\"f4/04.jpg\"]}," +
            "{\"urls\":[\"f5/01.jpg\",\"f5/02.jpg\",\"f5/03.jpg\",\"f5/04.png\",\"f5/05.jpg\"]}," +
            "{\"urls\":[\"f6/01.jpg\",\"f6/02.jpg\",\"f6/03.jpg\",\"f6/04.jpg\",\"f6/05.jpg\",\"f6/05.jpg\"]}," +
            "{\"urls\":[\"f7/01.jpg\",\"f7/02.jpg\",\"f7/03.jpg\"]}," +
            "{\"urls\":[\"f8/01.jpg\",\"f8/02.jpg\",\"f8/03.jpg\",\"f8/04.png\"]}," +
            "{\"urls\":[\"f9/01.jpg\",\"f9/02.jpg\",\"f9/03.jpg\",\"f9/04.jpg\"]}" +
            "]  }";

    private ArrayList<DataProfile> data;

    public PresenterAdapterRankImages() {

        data = new Gson().fromJson(JSON_DATA, ResponseDataProfile.class).getData();
    }

    @Override
    public int getItemsNum(int position) {
        return data == null ? 0 : data.get(position).getUrls().size();
    }

    @Override
    public String getUrl(int position, int imagePosition) {
        return "file:///android_asset/" + data.get(position).getUrls().get(imagePosition);
    }
}
