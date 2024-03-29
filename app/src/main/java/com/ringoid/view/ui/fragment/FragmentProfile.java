/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.PAGE_ENUM;
import com.ringoid.view.presenter.IPresenterProfile;
import com.ringoid.view.presenter.PresenterProfile;
import com.ringoid.view.presenter.callback.IPresenterProfileListener;
import com.ringoid.view.ui.adapter.AdapterProfile;
import com.ringoid.view.ui.util.IndicatorHelper;

import javax.inject.Inject;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class FragmentProfile extends FragmentBase implements View.OnClickListener {

    public static final String ARG_ACTION = "arg_action";

    @Inject
    IPresenterProfile presenterProfile;

    private IPresenterProfileListener listenerPresenter;
    private IndicatorHelper dotsIndicatorHelper;
    private View vPhotos, vEmpty;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout srlPhotos;
    private ListenerRefreshLayout listenerRefreshLayout;
    private RecyclerView rvItems;

    public static FragmentProfile getInstancePhotoAdd() {
        FragmentProfile fragmentProfile = new FragmentProfile();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_ACTION, PresenterProfile.ACTION_PHOTO_ADD);
        fragmentProfile.setArguments(bundle);
        return fragmentProfile;
    }

    @Override
    public PAGE_ENUM getPage() {
        return PAGE_ENUM.FEED_PROFILE;
    }

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
        vEmpty = view.findViewById(R.id.tvEmpty);
        srlPhotos = view.findViewById(R.id.srlProfilePhotos);

        srlPhotos.setOnRefreshListener(listenerRefreshLayout = new ListenerRefreshLayout());

        view.findViewById(R.id.fabProfile).setOnClickListener(this);
        view.findViewById(R.id.ivSettings).setOnClickListener(this);

        initList(view);
        presenterProfile.onCreateView(getArguments() == null ? 0 : getArguments().getInt(ARG_ACTION, 0));
        return view;
    }

    private void setViewState() {
        boolean photosVisible = presenterProfile.getItemsNum() != 0;
        vPhotos.setVisibility(photosVisible ? View.VISIBLE : View.GONE);
        vEmpty.setVisibility(photosVisible ? View.GONE : View.VISIBLE);
    }

    private void initList(View view) {
        rvItems = view.findViewById(R.id.rvItems);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        AdapterProfile adapter = new AdapterProfile();

        rvItems.setLayoutManager(layoutManager);
        rvItems.setAdapter(adapter);
        rvItems.addOnScrollListener(new ListenerScroll());
        OverScrollDecoratorHelper.setUpOverScroll(rvItems, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);

        new PagerSnapHelper().attachToRecyclerView(rvItems);
        rvItems.setScrollingTouchSlop(RecyclerView.TOUCH_SLOP_PAGING);

        FrameLayout flDots = (FrameLayout) view.findViewById(R.id.flDots);
        dotsIndicatorHelper = IndicatorHelper.getLinesHelper(flDots, rvItems, (LinearLayoutManager) rvItems.getLayoutManager());
        dotsIndicatorHelper.updateData(presenterProfile.getItemsNum());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fabProfile)
            presenterProfile.onClickPhotoAdd();

        if (v.getId() == R.id.llToolbarTitle)
            presenterProfile.onClickToolbar();

        if (v.getId() == R.id.ivSettings)
            presenterProfile.onClickSettings();
    }

    private class ListenerPresenter implements IPresenterProfileListener {

        @Override
        public void updateView(int position) {
            if (getContext() == null) return;
            dotsIndicatorHelper.updateData(presenterProfile.getItemsNum());
            dotsIndicatorHelper.setPosition(position);
            setViewState();
        }

        @Override
        public void refreshComplete() {
            srlPhotos.setRefreshing(false);
        }

        @Override
        public void scrollToPosition(int pos) {
            rvItems.scrollToPosition(pos);
        }
    }

    private class ListenerRefreshLayout implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            presenterProfile.onSwipeRefresh();
        }
    }

    private class ListenerScroll extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int pos = layoutManager.findFirstVisibleItemPosition();

            int right = layoutManager.findViewByPosition(pos).getRight();

            if (right < recyclerView.getContext().getResources().getDisplayMetrics().widthPixels / 2)
                pos += 1;

            presenterProfile.onShownItem(pos);
        }
    }
}
