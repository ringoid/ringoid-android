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

import org.byters.ringoid.R;
import org.byters.ringoid.view.ui.adapter.AdapterWelcome;
import org.byters.ringoid.view.ui.util.DotsIndicator;
import org.byters.ringoid.view.ui.util.IndicatorHelper;

public class FragmentWelcome extends FragmentBase implements View.OnClickListener {

    private IndicatorHelper dotsIndicatorHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        intiList(view);
    }

    private void intiList(View view) {
        RecyclerView rvItems = view.findViewById(R.id.rvItems);
        rvItems.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvItems.setAdapter(new AdapterWelcome());
        new PagerSnapHelper().attachToRecyclerView(rvItems);

        dotsIndicatorHelper = new IndicatorHelper((FrameLayout) view.findViewById(R.id.flDots), rvItems, (LinearLayoutManager) rvItems.getLayoutManager(),new DotsIndicator());
        dotsIndicatorHelper.updateData(rvItems.getAdapter().getItemCount());
    }

    @Override
    public void onClick(View v) {
        getActivity().onBackPressed();
    }
}
