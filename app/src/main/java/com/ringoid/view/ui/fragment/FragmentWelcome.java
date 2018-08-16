/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.INavigator;
import com.ringoid.view.ui.adapter.AdapterWelcome;
import com.ringoid.view.ui.util.IndicatorHelper;

import javax.inject.Inject;

public class FragmentWelcome extends FragmentBase implements View.OnClickListener {

    private static final String ARG_IS_WELCOME = "arg_welcome";
    @Inject
    INavigator navigator;
    private IndicatorHelper dotsIndicatorHelper;
    private RecyclerView rvItems;
    private LinearLayoutManager layoutManager;
    private AdapterWelcome adapter;

    public static Fragment getInstance(boolean isLogin) {
        Bundle args = new Bundle();
        args.putBoolean(ARG_IS_WELCOME, isLogin);
        FragmentWelcome fragmentWelcome = new FragmentWelcome();
        fragmentWelcome.setArguments(args);
        return fragmentWelcome;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        initViews(view);
        return view;
    }

    private void initViews(View view) {
        view.findViewById(R.id.tvSkip).setOnClickListener(this);
        view.findViewById(R.id.tvContinue).setOnClickListener(this);
        initList(view);
    }

    private void initList(View view) {
        rvItems = view.findViewById(R.id.rvItems);
        rvItems.setLayoutManager(layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvItems.setAdapter(adapter = new AdapterWelcome());
        new PagerSnapHelper().attachToRecyclerView(rvItems);

        dotsIndicatorHelper = IndicatorHelper.getLinesAccentGreenHelper((FrameLayout) view.findViewById(R.id.flDots), rvItems, (LinearLayoutManager) rvItems.getLayoutManager());
        dotsIndicatorHelper.updateData(rvItems.getAdapter().getItemCount());
        rvItems.addOnScrollListener(new WelcomeScrollListener());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvSkip) {
            confirm();
        }
        if (v.getId() == R.id.tvContinue) {
            showNext();
        }
    }

    @Override
    public boolean onBackPressed() {
        if (layoutManager.findFirstVisibleItemPosition() == 0 || !getArguments().getBoolean(ARG_IS_WELCOME) )
            return false;

        showPrevious();
        return true;
    }

    private void showPrevious() {
        int pos = layoutManager.findFirstVisibleItemPosition();
        if (pos < 1 || pos >= adapter.getItemCount()) return;
        rvItems.scrollToPosition(pos - 1);
    }

    private void showNext() {
        int pos = layoutManager.findFirstVisibleItemPosition();
        if (pos < 0 || pos >= adapter.getItemCount()) return;
        if (pos == adapter.getItemCount() - 1) {
            confirm();
            return;
        }

        rvItems.scrollToPosition(pos + 1);
    }

    private void confirm() {
        if (!getArguments().getBoolean(ARG_IS_WELCOME)) {
            getActivity().onBackPressed();
            return;
        }
        navigator.navigateLogin();
    }

    private class WelcomeScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int pos = layoutManager.findFirstVisibleItemPosition();
            int right = layoutManager.findViewByPosition(pos).getRight();

            if (right < getContext().getResources().getDisplayMetrics().widthPixels / 2)
                pos += 1;

            getView().findViewById(R.id.tvSkip).setVisibility(adapter.isLast(pos) ? View.GONE : View.VISIBLE);
            ((TextView) getView().findViewById(R.id.tvContinue)).setText(adapter.isLast(pos) ? R.string.button_want_it : R.string.button_continue);
        }
    }
}
