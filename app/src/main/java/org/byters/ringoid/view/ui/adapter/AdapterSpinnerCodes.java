package org.byters.ringoid.view.ui.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.byters.ringoid.R;

public class AdapterSpinnerCodes extends BaseAdapter {
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public String getItem(int position) {
        return position == 0 ? "RU <b>+7</b>"
                : position == 1 ? "US <b>+1</b>"
                : "ES <b>+61</b>";
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView == null ? LayoutInflater.from(parent.getContext()).inflate(R.layout.view_spinner_code, parent, false) : convertView;
        setData(view, position);

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = convertView == null ? LayoutInflater.from(parent.getContext()).inflate(R.layout.view_spinner_code_dropdown, parent, false) : convertView;

        setData(view, position);

        return view;
    }

    private void setData(View view, int position) {
        TextView tvCode = view.findViewById(R.id.tvCodeCountry);
        tvCode.setText(Html.fromHtml(getItem(position)));
    }
}
