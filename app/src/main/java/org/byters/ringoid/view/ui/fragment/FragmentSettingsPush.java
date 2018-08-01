package org.byters.ringoid.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.byters.ringoid.R;

public class FragmentSettingsPush extends FragmentBase implements View.OnClickListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        TextView tvSubtitle = view.findViewById(R.id.tvSubtitle);
        tvSubtitle.setText(R.string.settings_push_subtitle);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivBack && getActivity() != null)
            getActivity().onBackPressed();
    }

}
