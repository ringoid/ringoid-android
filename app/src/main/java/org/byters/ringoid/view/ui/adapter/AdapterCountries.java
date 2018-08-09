package org.byters.ringoid.view.ui.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import org.byters.ringoid.R;
import org.byters.ringoid.model.Country;

import java.util.List;

public class AdapterCountries extends ArrayAdapter<Country> implements SpinnerAdapter {

    private final LayoutInflater mInflater;

    public AdapterCountries(Context context, List<Country> countries) {
        super(context, R.layout.view_spinner_code, R.id.tvCodeCountry, countries);
        mInflater = LayoutInflater.from(getContext());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = mInflater.inflate(R.layout.view_spinner_code, parent, false);

        setData(convertView, position);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = mInflater.inflate(R.layout.view_spinner_code_dropdown, parent, false);
        setData(convertView, position);
        return convertView;

    }

    private void setData(View view, int position) {
        TextView tvCode = view.findViewById(R.id.tvCodeCountry);
        tvCode.setText(Html.fromHtml(getItemMessage(position)));
    }

    private String getItemMessage(int position) {
        Country item = getItem(position);
        return String.format("%s <b>+%d</b>", item.getCode().toUpperCase(), item.getDialCode());
    }
}

