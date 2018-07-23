package org.byters.ringoid.controller.data.memorycache;

import com.google.gson.Gson;

import org.byters.ringoid.controller.data.network.response.ResponseDataProfile;
import org.byters.ringoid.model.DataProfile;

import java.util.ArrayList;

public class CacheRank implements ICacheRank {


    private final String JSON_DATA = "{ \"data\":[" +
            "{\"urls\":[{\"url\":\"f1/01.jpg\",\"ratio\":0.54},{\"url\":\"f1/02.jpg\",\"ratio\":0.62},{\"url\":\"f1/03.jpg\",\"ratio\":0.67}]}," +
            "{\"urls\":[{\"url\":\"f2/01.jpg\",\"ratio\":1.38},{\"url\":\"f2/02.jpg\",\"ratio\":0.67},{\"url\":\"f2/03.png\",\"ratio\":1.27}]}," +
            "{\"urls\":[{\"url\":\"f3/01.jpg\",\"ratio\":1.37},{\"url\":\"f3/02.jpg\",\"ratio\":0.66}]}," +
            "{\"urls\":[{\"url\":\"f4/01.jpg\",\"ratio\":1.55},{\"url\":\"f4/02.jpg\",\"ratio\":0.946},{\"url\":\"f4/03.jpg\",\"ratio\":0.667},{\"url\":\"f4/04.jpg\",\"ratio\":0.75}]}," +
            "{\"urls\":[{\"url\":\"f5/01.jpg\",\"ratio\":1.5},{\"url\":\"f5/02.jpg\",\"ratio\":0.56},{\"url\":\"f5/03.jpg\",\"ratio\":1.5},{\"url\":\"f5/04.png\",\"ratio\":1.33},{\"url\":\"f5/05.jpg\",\"ratio\":0.665}]}," +
            "{\"urls\":[{\"url\":\"f6/01.jpg\",\"ratio\":1.446},{\"url\":\"f6/02.jpg\",\"ratio\":0.75},{\"url\":\"f6/03.jpg\",\"ratio\":1.5},{\"url\":\"f6/04.jpg\",\"ratio\":1.5},{\"url\":\"f6/05.jpg\",\"ratio\":0.67},{\"url\":\"f6/05.jpg\",\"ratio\":0.56}]}," +
            "{\"urls\":[{\"url\":\"f7/01.jpg\",\"ratio\":0.625},{\"url\":\"f7/02.jpg\",\"ratio\":0.56},{\"url\":\"f7/03.jpg\",\"ratio\":0.556}]}," +
            "{\"urls\":[{\"url\":\"f8/01.jpg\",\"ratio\":1.5},{\"url\":\"f8/02.jpg\",\"ratio\":1.39},{\"url\":\"f8/03.jpg\",\"ratio\":0.667},{\"url\":\"f8/04.png\",\"ratio\":0.5625}]}," +
            "{\"urls\":[{\"url\":\"f9/01.jpg\",\"ratio\":1.5},{\"url\":\"f9/02.jpg\",\"ratio\":1.26},{\"url\":\"f9/03.jpg\",\"ratio\":0.75},{\"url\":\"f9/04.jpg\",\"ratio\":0.625}]}" +
            "]  }";
    private ArrayList<DataProfile> data;

    public CacheRank() {

        data = new Gson().fromJson(JSON_DATA, ResponseDataProfile.class).getData();
    }

    @Override
    public int getItemsNum() {
        return data == null ? 0 : data.size();
    }

    @Override
    public float getItemRatio(int adapterPosition, int itemPos) {
        return data.get(adapterPosition).getRatio(itemPos);
    }

    @Override
    public int getItemsNum(int position) {
        return data.get(position).getItemsNum();
    }

    @Override
    public String getImage(int position, int imagePosition) {
        return data.get(position).getImage(imagePosition);
    }
}
