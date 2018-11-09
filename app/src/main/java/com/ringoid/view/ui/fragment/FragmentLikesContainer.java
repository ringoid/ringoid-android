package com.ringoid.view.ui.fragment;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.INavigator;
import com.ringoid.view.presenter.IPresenterLikesContainer;
import com.ringoid.view.presenter.callback.IPresenterLikesContainerListener;

import javax.inject.Inject;

public class FragmentLikesContainer extends FragmentBase
        implements View.OnClickListener {

    @Inject
    IPresenterLikesContainer presenterLikesContainer;

    @Inject
    INavigator navigator;

    private ListenerPresenter listenerPresenter;
    private LinearLayout llTitles;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
        presenterLikesContainer.setListener(listenerPresenter = new ListenerPresenter());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_likes_container, container, false);
        initViews(view);
        presenterLikesContainer.onCreateView(getChildFragmentManager(), R.id.flContentLikes);
        return view;
    }

    private void initViews(View view) {
        llTitles = view.findViewById(R.id.llTitles);
        view.findViewById(R.id.tvLikes).setOnClickListener(this);
        view.findViewById(R.id.tvMatches).setOnClickListener(this);
        view.findViewById(R.id.tvMessages).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        presenterLikesContainer.onClick(v.getId());
    }

    private class ListenerPresenter implements IPresenterLikesContainerListener {
        @Override
        public void setPageSelected(int currentPageIndex) {
            for (int i = 0; i < llTitles.getChildCount(); ++i) {
                TextView tvTitle = (TextView) llTitles.getChildAt(i);
                tvTitle.setTypeface(null, currentPageIndex == i ? Typeface.BOLD : Typeface.NORMAL);
            }
        }

        @Override
        public void setTabbarShown(boolean isShown) {
            llTitles.setVisibility(isShown?View.VISIBLE:View.GONE);
        }

    }
}
