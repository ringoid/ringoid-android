package org.byters.ringoid.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.view.presenter.IPresenterProfile;
import org.byters.ringoid.view.ui.adapter.AdapterProfile;
import org.byters.ringoid.view.ui.util.IndicatorHelper;
import org.byters.ringoid.view.ui.util.LinesIndicator;

import javax.inject.Inject;

public class FragmentProfile extends FragmentBase {

    @Inject
    IPresenterProfile presenterProfile;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initList(view);
        return view;
    }

    private void initList(View view) {
        RecyclerView rvItems = view.findViewById(R.id.rvItems);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        AdapterProfile adapter = new AdapterProfile();

        rvItems.setLayoutManager(layoutManager);
        rvItems.setAdapter(adapter);

        new PagerSnapHelper().attachToRecyclerView(rvItems);

        IndicatorHelper dotsIndicatorHelper = IndicatorHelper.getLinesHelper((FrameLayout) view.findViewById(R.id.flDots), rvItems, (LinearLayoutManager) rvItems.getLayoutManager());
        dotsIndicatorHelper.updateData(presenterProfile.getItemsNum());
    }
}
