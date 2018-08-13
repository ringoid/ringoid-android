package com.hbb20.model;

import java.util.ArrayList;

public class ModelDataCountryTranslate {
    private ArrayList<DataCountryTranslate> data;

    private String dialog_title;
    private String dialog_hint;
    private String dialog_no_result;

    public ArrayList<DataCountryTranslate> getData() {
        return data;
    }

    public String getDialogTitle() {
        return dialog_title;
    }

    public String getDialogHint() {
        return dialog_hint;
    }

    public String getDialogNoResult() {
        return dialog_no_result;
    }
}
