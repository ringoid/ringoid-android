/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.BuildConfig;
import com.ringoid.R;
import com.ringoid.view.PAGE_ENUM;
import com.ringoid.view.presenter.IPresenterPagesContainer;
import com.ringoid.view.presenter.PresenterPagesContainer;
import com.ringoid.view.presenter.callback.IPresenterPagesContainerListener;
import com.ringoid.view.ui.util.IHelperAnimation;

import java.util.Random;

import javax.inject.Inject;

public class FragmentPages extends FragmentBase
        implements View.OnClickListener {

    @Inject
    IPresenterPagesContainer presenterPagesContainer;

    @Inject
    IHelperAnimation helperAnimation;

    @Inject
    Random random;

    private ListenerPresenter listenerPresenter;
    private ViewGroup llBottomAppBar;

    @Override
    public PAGE_ENUM getPage() {
        return PAGE_ENUM.FEED;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
        presenterPagesContainer.setListener(listenerPresenter = new ListenerPresenter());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pages, container, false);

        initViews(view);

        presenterPagesContainer.onViewCreate(getChildFragmentManager(), R.id.flContent);
        return view;
    }

    private void initViews(View view) {

        llBottomAppBar = view.findViewById(R.id.llBottomAppBar);

        view.findViewById(R.id.ivMenuLikes).setOnClickListener(this);
        view.findViewById(R.id.ivMenuProfile).setOnClickListener(this);
        view.findViewById(R.id.ivMenuMatches).setOnClickListener(this);
        view.findViewById(R.id.ivMenuMessages).setOnClickListener(this);
        view.findViewById(R.id.ivMenuExplore).setOnClickListener(this);
        view.findViewById(R.id.ivMenuSettings).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (BuildConfig.DEBUG) {
            float randomValue = random.nextFloat() * 3;

            if (randomValue < 1)
                listenerPresenter.showAnimationLike();
            else if (randomValue < 2)
                listenerPresenter.showAnimationMessage();
            else listenerPresenter.showAnimationMatches();
        }

        if (view.getId() == R.id.ivMenuLikes)
            presenterPagesContainer.onClickPageLikes();

        if (view.getId() == R.id.ivMenuProfile)
            presenterPagesContainer.onClickPageProfile();

        if (view.getId() == R.id.ivMenuMessages)
            presenterPagesContainer.onClickPageMessages();

        if (view.getId() == R.id.ivMenuMatches)
            presenterPagesContainer.onClickPageMatches();

        if (view.getId() == R.id.ivMenuExplore)
            presenterPagesContainer.onClickPageExplore();

        if (view.getId() == R.id.ivMenuSettings)
            presenterPagesContainer.onClickPageSettings();
    }

    private class ListenerPresenter implements IPresenterPagesContainerListener {

        @Override
        public void setPosition(int pos) {
           // llBottomAppBar.setTranslationY(pos);
        }

        @Override
        public void setPageSelected(int num, int iconResProfile, int iconResMatches, int iconResLikes, int iconResMessages, int iconResExplore, int iconResSettings) {
            if (getContext() == null) return;
            if (llBottomAppBar == null) return;
            for (int i = 0; i < llBottomAppBar.getChildCount(); ++i) {
                View view = llBottomAppBar.getChildAt(i);
                if (view == null) continue;

                if (i == num)
                    view.setBackgroundColor(getContext().getResources().getColor(R.color.menu_bottom_selected));
                else
                    view.setBackground(null);
            }

            setDrawables(iconResProfile, iconResMatches, iconResLikes, iconResMessages, iconResExplore, iconResSettings);
        }

        private void setDrawables(int iconResProfile, int iconResMatches, int iconResLikes, int iconResMessages, int iconResExplore, int iconResSettings) {
            ((ImageView) llBottomAppBar.getChildAt(PresenterPagesContainer.INDEX_PAGE_PROFILE)).setImageResource(iconResProfile);
            ((ImageView) llBottomAppBar.getChildAt(PresenterPagesContainer.INDEX_PAGE_LIKES)).setImageResource(iconResLikes);
            ((ImageView) llBottomAppBar.getChildAt(PresenterPagesContainer.INDEX_PAGE_MATCHES)).setImageResource(iconResMatches);
            ((ImageView) llBottomAppBar.getChildAt(PresenterPagesContainer.INDEX_PAGE_MESSAGES)).setImageResource(iconResMessages);
            ((ImageView) llBottomAppBar.getChildAt(PresenterPagesContainer.INDEX_PAGE_EXPLORE)).setImageResource(iconResExplore);
            ((ImageView) llBottomAppBar.getChildAt(PresenterPagesContainer.INDEX_PAGE_SETTINGS)).setImageResource(iconResSettings);
        }

        @Override
        public void showAnimationLike() {
            helperAnimation.showPopupLikes((ViewGroup) getView().findViewById(R.id.flPages));
        }

        @Override
        public void showAnimationMessage() {
            helperAnimation.showPopupMessage((ViewGroup) getView().findViewById(R.id.flPages));
        }

        @Override
        public void showAnimationMatches() {
            helperAnimation.showPopupMatches((ViewGroup) getView().findViewById(R.id.flPages));
        }
    }
}
