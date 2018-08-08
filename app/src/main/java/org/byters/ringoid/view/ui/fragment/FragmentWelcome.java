package org.byters.ringoid.view.ui.fragment;

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

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.view.INavigator;
import org.byters.ringoid.view.ui.adapter.AdapterWelcome;
import org.byters.ringoid.view.ui.adapter.IAdapterWelcomeListener;
import org.byters.ringoid.view.ui.util.IndicatorHelper;

import javax.inject.Inject;

public class FragmentWelcome extends FragmentBase implements View.OnClickListener {

    private static final String ARG_IS_WELCOME = "arg_welcome";
    @Inject
    INavigator navigator;
    private IndicatorHelper dotsIndicatorHelper;
    private ListenerAdapterWelcome listenerAdapter;
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
        rvItems.setAdapter(adapter = new AdapterWelcome(listenerAdapter = new ListenerAdapterWelcome()));
        new PagerSnapHelper().attachToRecyclerView(rvItems);

        dotsIndicatorHelper = IndicatorHelper.getLinesAccentGreenHelper((FrameLayout) view.findViewById(R.id.flDots), rvItems, (LinearLayoutManager) rvItems.getLayoutManager());
        dotsIndicatorHelper.updateData(rvItems.getAdapter().getItemCount());
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

    private class ListenerAdapterWelcome implements IAdapterWelcomeListener {
        @Override
        public void onPageShown(boolean isLast) {
            getView().findViewById(R.id.tvSkip).setVisibility(isLast ? View.GONE : View.VISIBLE);
            ((TextView) getView().findViewById(R.id.tvContinue)).setText(isLast ? R.string.button_want_it : R.string.button_continue);
        }
    }
}
