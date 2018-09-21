package com.ringoid.model;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import java.io.Serializable;
import java.util.ArrayList;

public class ModelFeedExplore implements Serializable {
    private ArrayList<DataProfile> data;

    public ModelFeedExplore(ArrayList<DataProfile> data) {
        this.data = data;
    }

    public ModelFeedExplore() {

    }

    public int size() {
        return data == null ? 0 : data.size();
    }

    public DataProfile get(int position) {
        return data == null || position < 0 || position >= data.size() ? null : data.get(position);
    }
}
