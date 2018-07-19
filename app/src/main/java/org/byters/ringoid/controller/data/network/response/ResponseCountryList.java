package org.byters.ringoid.controller.data.network.response;

import org.byters.ringoid.model.DataCountry;

import java.util.ArrayList;

public class ResponseCountryList extends ResponseBase{
    private ArrayList<DataCountry> data;

    public ArrayList<DataCountry> getData() {
        return data;
    }
}
