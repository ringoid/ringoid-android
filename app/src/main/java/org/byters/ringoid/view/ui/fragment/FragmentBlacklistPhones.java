package org.byters.ringoid.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.view.INavigator;
import org.byters.ringoid.view.presenter.IPresenterBlacklistPhones;
import org.byters.ringoid.view.ui.adapter.AdapterBlacklistPhones;

import javax.inject.Inject;

public class FragmentBlacklistPhones extends FragmentBase
        implements View.OnClickListener {
    @Inject
    INavigator navigator;

    @Inject
    IPresenterBlacklistPhones presenterBlacklistPhones;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blacklist, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.ivBlacklistAdd).setOnClickListener(this);
        view.findViewById(R.id.ivBack).setOnClickListener(this);

        RecyclerView rvItems = view.findViewById(R.id.rvItems);
        rvItems.setLayoutManager(new LinearLayoutManager(getContext()));
        rvItems.setAdapter(new AdapterBlacklistPhones());


        TextView tvSubtitle = view.findViewById(R.id.tvSubtitle);
        tvSubtitle.setText(R.string.settings_privacy_phones_subtitle);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivBlacklistAdd)
            navigator.navigateBlacklistPhonesAdd();

        if (v.getId() == R.id.ivBack && getActivity() != null)
            getActivity().onBackPressed();
    }
}
