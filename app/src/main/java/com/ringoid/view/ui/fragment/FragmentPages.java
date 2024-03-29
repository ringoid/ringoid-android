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
import com.ringoid.view.ui.util.IHelperTheme;

import java.util.Random;

import javax.inject.Inject;

public class FragmentPages extends FragmentBase
        implements View.OnClickListener {

    public static final String ARG_ACTION = "ARG_ACTION";

    @Inject
    IPresenterPagesContainer presenterPagesContainer;

    @Inject
    IHelperAnimation helperAnimation;

    @Inject
    IHelperTheme helperTheme;

    @Inject
    Random random;

    private ListenerPresenter listenerPresenter;
    private ViewGroup llBottomAppBar;

    public static FragmentPages getInstanceProfilePhotoUpload() {
        FragmentPages fragment = new FragmentPages();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_ACTION, PresenterPagesContainer.ACTION_PHOTO_ADD);
        fragment.setArguments(bundle);
        return fragment;
    }

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

        presenterPagesContainer.onViewCreate(getChildFragmentManager(), R.id.flContent, getArguments() == null ? 0 : getArguments().getInt(ARG_ACTION, 0));
        return view;
    }

    private void initViews(View view) {

        llBottomAppBar = view.findViewById(R.id.llBottomAppBar);

        view.findViewById(R.id.ivMenuLikes).setOnClickListener(this);
        view.findViewById(R.id.ivMenuProfile).setOnClickListener(this);
        view.findViewById(R.id.ivMenuExplore).setOnClickListener(this);
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

        if (view.getId() == R.id.ivMenuExplore)
            presenterPagesContainer.onClickPageExplore();
    }

    private class ListenerPresenter implements IPresenterPagesContainerListener {

        @Override
        public void setPosition(int pos) {
            // llBottomAppBar.setTranslationY(pos);
        }

        @Override
        public void setPageSelected(int num,
                                    int iconResProfile,
                                    int iconResLikes,
                                    int iconResExplore) {

            ((ImageView) llBottomAppBar.getChildAt(PresenterPagesContainer.INDEX_PAGE_PROFILE)).setImageResource(iconResProfile);
            ((ImageView) llBottomAppBar.getChildAt(PresenterPagesContainer.INDEX_PAGE_LIKES)).setImageResource(iconResLikes);
            ((ImageView) llBottomAppBar.getChildAt(PresenterPagesContainer.INDEX_PAGE_EXPLORE)).setImageResource(iconResExplore);
        }

        @Override
        public void showAnimationLike() {
            helperAnimation.showPopupLikes((ViewGroup) getView().findViewById(R.id.flAnimationPopup));
        }

        @Override
        public void showAnimationMessage() {
            helperAnimation.showPopupMessage((ViewGroup) getView().findViewById(R.id.flAnimationPopup));
        }

        @Override
        public void showAnimationMatches() {
            helperAnimation.showPopupMatches((ViewGroup) getView().findViewById(R.id.flAnimationPopup));
        }
    }
}
