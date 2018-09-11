/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterProfile;
import com.ringoid.view.presenter.callback.IPresenterProfileListener;
import com.ringoid.view.ui.adapter.AdapterProfile;
import com.ringoid.view.ui.util.IndicatorHelper;

import javax.inject.Inject;

public class FragmentProfile extends FragmentBase implements View.OnClickListener {

    @Inject
    IPresenterProfile presenterProfile;

    private IPresenterProfileListener listenerPresenter;
    private IndicatorHelper dotsIndicatorHelper;
    private View vPhotos, vEmpty;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
        presenterProfile.setListener(listenerPresenter = new ListenerPresenter());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        vPhotos = view.findViewById(R.id.flPhotos);
        vEmpty = view.findViewById(R.id.flEmpty);

        view.findViewById(R.id.fabProfile).setOnClickListener(this);
        view.findViewById(R.id.tvSettings).setOnClickListener(this);
        view.findViewById(R.id.llToolbarTitle).setOnClickListener(this);

        initList(view);
        presenterProfile.onCreateView();
        return view;
    }

    private void setViewState() {
        boolean photosVisible = presenterProfile.getItemsNum() != 0;
        vPhotos.setVisibility(photosVisible ? View.VISIBLE : View.GONE);
        vEmpty.setVisibility(photosVisible ? View.GONE : View.VISIBLE);
    }

    private void initList(View view) {
        RecyclerView rvItems = view.findViewById(R.id.rvItems);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        AdapterProfile adapter = new AdapterProfile();

        rvItems.setLayoutManager(layoutManager);
        rvItems.setAdapter(adapter);

        new PagerSnapHelper().attachToRecyclerView(rvItems);

        dotsIndicatorHelper = IndicatorHelper.getLinesHelper((FrameLayout) view.findViewById(R.id.flDots), rvItems, (LinearLayoutManager) rvItems.getLayoutManager());
        dotsIndicatorHelper.updateData(presenterProfile.getItemsNum());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fabProfile)
            presenterProfile.onClickPhotoAdd();

        if (v.getId() == R.id.llToolbarTitle)
            presenterProfile.onClickToolbar();

        if (v.getId() == R.id.tvSettings)
            presenterProfile.onClickSettings();
    }

    private class ListenerPresenter implements IPresenterProfileListener {

        @Override
        public void updateView() {
            if (getContext() == null) return;
            dotsIndicatorHelper.updateData(presenterProfile.getItemsNum());
            setViewState();
        }
    }
}
