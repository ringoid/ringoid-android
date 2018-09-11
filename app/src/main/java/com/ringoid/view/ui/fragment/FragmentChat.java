/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterChat;
import com.ringoid.view.presenter.callback.IPresenterChatListener;
import com.ringoid.view.ui.adapter.AdapterChatMessages;
import com.ringoid.view.ui.dialog.DialogReport;
import com.ringoid.view.ui.util.DividerItemDecoration;
import com.ringoid.view.ui.util.GlideApp;

import javax.inject.Inject;

public class FragmentChat extends FragmentBase implements View.OnClickListener {

    @Inject
    IPresenterChat presenterChat;

    private ImageView ivUser;
    private ListenerPresenter listenerPresenter;
    private DialogReport dialogMenu;
    private RecyclerView rvMessages;
    private EditText etMessage;
    private View ivSend;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
        presenterChat.setListener(listenerPresenter = new ListenerPresenter());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        intiViews(view);
        initList(view);
        presenterChat.onCreateView();
        return view;
    }

    private void initList(View view) {
        RecyclerView rvMessages = view.findViewById(R.id.rvMessages);
        rvMessages.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMessages.setAdapter(new AdapterChatMessages());
        rvMessages.addItemDecoration(new DividerItemDecoration(getContext()));
    }

    private void intiViews(View view) {
        ivUser = view.findViewById(R.id.ivUser);
        rvMessages = view.findViewById(R.id.rvMessages);

        etMessage = view.findViewById(R.id.etMessage);
        ivSend = view.findViewById(R.id.ivSend);

        view.findViewById(R.id.ivBack).setOnClickListener(this);
        view.findViewById(R.id.ivBlock).setOnClickListener(this);
        view.findViewById(R.id.ivClear).setOnClickListener(this);

        ivSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivBack)
            getActivity().onBackPressed();

        if (v.getId() == R.id.ivBlock)
            showMenu();

        if (v.getId() == R.id.ivClear)
            presenterChat.onClickClear();

        if (v.getId() == R.id.ivSend) {
            presenterChat.onClickSend(etMessage.getText().toString());
            etMessage.setText("");
        }
    }

    private void showMenu() {
        if (dialogMenu != null) dialogMenu.cancel();
        dialogMenu = new DialogReport(getView().getContext());
        dialogMenu.show();
    }

    private class ListenerPresenter implements IPresenterChatListener {

        @Override
        public void setImage(String url) {

            if (TextUtils.isEmpty(url))
                ivUser.setImageDrawable(null);
            else
                GlideApp.with(getContext())
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .circleCrop()
                        .into(ivUser);

        }

        @Override
        public void setDataExist(boolean exist) {
        }
    }

}
