/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterMessages;
import com.ringoid.view.presenter.callback.IPresenterMessagesListener;
import com.ringoid.view.ui.adapter.AdapterMessages;
import com.ringoid.view.ui.util.DividerItemDecoration;

import javax.inject.Inject;

public class FragmentMessages extends FragmentBase {

    @Inject
    IPresenterMessages presenterMessages;

    private LinearLayoutManager layoutManager;
    private ListenerPresenterMessages listenerPresenterMessages;
    private RecyclerView rvItems;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
        presenterMessages.setListener(listenerPresenterMessages = new ListenerPresenterMessages());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        initList(view);
        presenterMessages.onCreateView();

        return view;

    }

    private void initList(View view) {

        rvItems = view.findViewById(R.id.rvItems);
        rvItems.setLayoutManager(layoutManager = new LinearLayoutManager(view.getContext()));
        rvItems.setAdapter(new AdapterMessages());
        rvItems.addOnScrollListener(new OnScrollListener());
        rvItems.addItemDecoration(new DividerItemDecoration(getContext()));
    }

    private class OnScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            presenterMessages.onScroll(dy);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            presenterMessages.onScrollState(newState, layoutManager.findFirstVisibleItemPosition());
        }
    }

    private class ListenerPresenterMessages implements IPresenterMessagesListener {
        @Override
        public void scrollToPosition(int positionScrollPageMessages) {
            rvItems.scrollToPosition(positionScrollPageMessages);
        }
    }
}
