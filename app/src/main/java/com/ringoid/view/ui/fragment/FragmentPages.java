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
import com.ringoid.view.presenter.IPresenterPagesContainer;
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
        view.findViewById(R.id.ivMenuMessages).setOnClickListener(this);
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

        if (view.getId() == R.id.ivMenuMessages)
            presenterPagesContainer.onClickPageMessages();

        if (view.getId() == R.id.ivMenuExplore)
            presenterPagesContainer.onClickPageExplore();
    }

    private class ListenerPresenter implements IPresenterPagesContainerListener {

        @Override
        public void setPosition(int pos) {
            llBottomAppBar.setTranslationY(pos);
        }

        @Override
        public void setPageSelected(int num) {
            if (llBottomAppBar == null) return;
            for (int i = 0; i < llBottomAppBar.getChildCount(); ++i) {
                View view = llBottomAppBar.getChildAt(i);
                if (view == null) continue;

                if (i == num)
                    view.setBackgroundColor(getContext().getResources().getColor(R.color.menu_bottom_selected));
                else
                    view.setBackground(null);

            }
        }

        @Override
        public void setBottomSheetDrawables(int likes, int messages, int explore) {
            if (llBottomAppBar == null || llBottomAppBar.getChildCount() != 4) return;
            ((ImageView) llBottomAppBar.getChildAt(1)).setImageResource(likes);
            ((ImageView) llBottomAppBar.getChildAt(2)).setImageResource(messages);
            ((ImageView) llBottomAppBar.getChildAt(3)).setImageResource(explore);

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
