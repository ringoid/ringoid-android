/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterPagesContainer;
import com.ringoid.view.presenter.callback.IPresenterPagesContainerListener;
import com.ringoid.view.ui.util.IStatusBarViewHelper;

import javax.inject.Inject;

public class FragmentPages extends FragmentBase
        implements View.OnClickListener {

    @Inject
    IPresenterPagesContainer presenterPagesContainer;
    @Inject
    IStatusBarViewHelper statusBarViewHelper;
    private TextView tvSubtitle;
    private AlertDialog dialogInvite;
    private ListenerPresenter listenerPresenter;
    private View flToolbar;
    private ViewGroup llBottomAppBar;
    private ImageView ivPrivacy;

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
        view.findViewById(R.id.llToolbarTitle).setOnClickListener(this);
        return view;
    }

    private void initViews(View view) {

        tvSubtitle = view.findViewById(R.id.tvSubtitle);

        flToolbar = view.findViewById(R.id.flToolbar);
        llBottomAppBar = view.findViewById(R.id.llBottomAppBar);

        view.findViewById(R.id.ivMenuLikes).setOnClickListener(this);
        view.findViewById(R.id.ivMenuProfile).setOnClickListener(this);
        view.findViewById(R.id.ivMenuMessages).setOnClickListener(this);
        view.findViewById(R.id.ivMenuExplore).setOnClickListener(this);
        view.findViewById(R.id.tvSettings).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.tvSettings)
            presenterPagesContainer.onClickSettings();

        if (view.getId() == R.id.ivMenuLikes)
            presenterPagesContainer.onClickPageLikes();

        if (view.getId() == R.id.ivMenuProfile)
            presenterPagesContainer.onClickPageProfile();

        if (view.getId() == R.id.ivMenuMessages)
            presenterPagesContainer.onClickPageMessages();

        if (view.getId() == R.id.ivMenuExplore)
            presenterPagesContainer.onClickPageExplore();

        if (view.getId() == R.id.llToolbarTitle)
            presenterPagesContainer.onClickToolbar();

    }

    private void hideStatusbar() {
        if (getActivity() == null) return;
        View decorView = getActivity().getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void showStatusbar() {
        if (getActivity() == null) return;
        View decorView = getActivity().getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private class ListenerPresenter implements IPresenterPagesContainerListener {

        @Override
        public void setPosition(boolean toolbarScroll, int topPos, int bottomPos, float alpha) {
            if (toolbarScroll) {
                flToolbar.setTranslationY(topPos);
                flToolbar.setAlpha(alpha);
            }

            llBottomAppBar.setTranslationY(bottomPos);

            llBottomAppBar.setAlpha(alpha);
        }

        @Override
        public void scrollComplete(boolean toolbarScroll, int scrollSum, int alpha) {
            if (toolbarScroll)
                flToolbar.animate()
                        .alpha(alpha)
                        .translationY(-scrollSum)
                        .setDuration(250);

            llBottomAppBar.animate()
                    .alpha(alpha)
                    .translationY(scrollSum)
                    .setDuration(250);
        }

        @Override
        public void hideToolbar() {

            hideStatusbar();

            int translation = (int) getContext().getResources().getDimension(R.dimen.toolbar_height);
            flToolbar.animate()
                    .alpha(0)
                    .translationY(-translation)
                    .setDuration(250);
        }

        @Override
        public void showToolbar() {

            showStatusbar();

            flToolbar.animate()
                    .alpha(1)
                    .translationY(0)
                    .setDuration(250);
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
        public void setBottomSheetDrawables(int profile, int likes, int messages, int explore) {
            if (llBottomAppBar == null || llBottomAppBar.getChildCount() != 4) return;
            ((ImageView) llBottomAppBar.getChildAt(0)).setImageResource(profile);
            ((ImageView) llBottomAppBar.getChildAt(1)).setImageResource(likes);
            ((ImageView) llBottomAppBar.getChildAt(2)).setImageResource(messages);
            ((ImageView) llBottomAppBar.getChildAt(3)).setImageResource(explore);

        }

        @Override
        public void setStatusBarColor(int type) {
            statusBarViewHelper.setColor((AppCompatActivity) getActivity(), type);
        }
    }
}
