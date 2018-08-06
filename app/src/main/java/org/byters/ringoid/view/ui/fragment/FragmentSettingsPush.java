package org.byters.ringoid.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.byters.ringoid.R;

import java.util.ArrayList;

public class FragmentSettingsPush extends FragmentBase implements View.OnClickListener {

    private ArrayList<Integer> lSelected;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lSelected = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_push, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {

        view.findViewById(R.id.ivBack).setOnClickListener(this);
        view.findViewById(R.id.swItemLikes).setOnClickListener(this);
        view.findViewById(R.id.swItemMatches).setOnClickListener(this);
        view.findViewById(R.id.swItemMessages).setOnClickListener(this);

        TextView tvSubtitle = view.findViewById(R.id.tvSubtitle);
        tvSubtitle.setText(R.string.settings_push_subtitle);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivBack && getActivity() != null)
            getActivity().onBackPressed();
        checkSwitch(v, R.id.swItemLikes);
        checkSwitch(v, R.id.swItemMatches);
        checkSwitch(v, R.id.swItemMessages);
    }

    private void checkSwitch(View v, int viewId) {
        if (v.getId() != viewId) return;
        ImageView ivItem = (ImageView) v;
        updateChecked(viewId);
        ivItem.setImageResource(isChecked(viewId) ? R.drawable.switch_on : R.drawable.switch_off);
    }

    private boolean isChecked(int viewId) {
        return lSelected.contains(viewId);
    }

    private void updateChecked(int viewId) {
        if (lSelected.contains(viewId))
            lSelected.remove((Integer) viewId);
        else lSelected.add(viewId);
    }

}
