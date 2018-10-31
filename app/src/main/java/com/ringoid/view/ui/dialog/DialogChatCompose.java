package com.ringoid.view.ui.dialog;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterChat;
import com.ringoid.view.presenter.callback.IPresenterChatListener;
import com.ringoid.view.ui.adapter.AdapterChatMessages;
import com.ringoid.view.ui.util.DividerItemDecoration;
import com.ringoid.view.ui.util.KeyboardUtils;
import com.ringoid.view.ui.view.EditTextPreIme;
import com.ringoid.view.ui.view.callback.IEditTextPreImeListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class DialogChatCompose implements View.OnClickListener {

    Dialog dialog;

    @Inject
    KeyboardUtils keyboardUtils;

    @Inject
    IPresenterChat presenterChat;

    private IPresenterChatListener listenerPresenter;

    private View viewContainer;
    private EditTextPreIme etMessage;
    private RecyclerView rvMessages;
    private LinearLayoutManager layoutManager;

    public DialogChatCompose(Context context, View container) {
        ApplicationRingoid.getComponent().inject(this);
        presenterChat.setListener(listenerPresenter = new ListenerPresenter());

        this.viewContainer = container;

        dialog = new Dialog(context, R.style.themeDialogFullscreen);

        dialog.setContentView(R.layout.view_dialog_chat_compose);
        initViews(dialog);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setOnDismissListener(new ListenerDismiss());
        dialog.setOnShowListener(new ListenerShow());
        keyboardUtils.keyboardShow(dialog.getWindow());
    }

    private void initViews(Dialog view) {
        view.findViewById(R.id.ivSend).setOnClickListener(this);
        etMessage = view.findViewById(R.id.etMessage);
        etMessage.setListener(new ListenerViewPreIme());

        presenterChat.onDialogCreate();
    }

    private void initList(Dialog view) {
        rvMessages = view.findViewById(R.id.rvItems);
        rvMessages.setOnTouchListener(new TouchListener());
        layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setReverseLayout(true);
        rvMessages.setLayoutManager(layoutManager);
        rvMessages.setAdapter(new AdapterChatMessages(new ListenerAdapter()));
        rvMessages.addItemDecoration(new DividerItemDecoration(view.getContext()));
        rvMessages.addOnScrollListener(new OnScrollListener());
        presenterChat.onListInited();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivSend) {
            String message = getMessage();
            if (!TextUtils.isEmpty(message)) {
                etMessage.setText("");
                presenterChat.onClickMessageSend(message);
            }
        }
    }

    private String getMessage() {
        return etMessage.getText().toString().trim();
    }

    public void show() {
        if (dialog == null) return;
        dialog.show();
    }

    public void cancel() {
        if (dialog == null) return;
        dialog.dismiss();
    }

    private void showMessages() {
        new Handler().postDelayed(new RunnableListShow(this), 250);
    }

    private Dialog getDialog() {
        return dialog;
    }

    private static class RunnableListShow implements Runnable {

        private WeakReference<DialogChatCompose> refDialog;

        RunnableListShow(DialogChatCompose dialog) {
            this.refDialog = new WeakReference<>(dialog);
        }

        @Override
        public void run() {
            if (refDialog == null) return;
            DialogChatCompose dialog = refDialog.get();
            if (dialog == null) return;
            LayoutInflater.from(dialog.getDialog().getContext()).inflate(R.layout.view_messages_in_dialog, (ViewGroup) dialog.getDialog().findViewById(R.id.flMessages), true);
            dialog.initList(dialog.getDialog());
            dialog.getDialog().findViewById(R.id.llCompose).setVisibility(View.VISIBLE);
        }
    }

    private class ListenerDismiss implements DialogInterface.OnDismissListener {
        @Override
        public void onDismiss(DialogInterface dialog) {

            keyboardUtils.keyboardHide(viewContainer.getContext(), viewContainer);

            presenterChat.onDialogDismiss(getMessage());
        }
    }

    private class ListenerViewPreIme implements IEditTextPreImeListener {

        @Override
        public boolean onKeyPreIme(int keyCode, KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                dialog.dismiss();
            }

            return false;
        }
    }

    private class ListenerAdapter implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            cancel();
        }
    }

    private class TouchListener implements View.OnTouchListener {
        private int last_action;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP
                    && last_action == MotionEvent.ACTION_DOWN) {
                cancel();
                return true;
            }
            last_action = event.getAction();
            return false;
        }
    }

    private class ListenerShow implements DialogInterface.OnShowListener {
        @Override
        public void onShow(DialogInterface dialog) {
            presenterChat.onDialogShow();
            etMessage.requestFocus();
            showMessages();
        }
    }

    private class OnScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            int pos = layoutManager.findFirstVisibleItemPosition();
            View firstItemView = layoutManager.findViewByPosition(pos);
            if (firstItemView == null) return;
            int offset = getDialog().findViewById(R.id.flMessages).getBottom() - firstItemView.getBottom();

            presenterChat.onSCroll(newState, pos, offset);
        }
    }

    private class ListenerPresenter implements IPresenterChatListener {
        @Override
        public void setMessageCompose(String message) {
            if (etMessage == null) return;
            etMessage.setText(message);
            etMessage.setSelection(message.length());
        }

        @Override
        public void scrollToEnd() {
            if (rvMessages == null) return;
            rvMessages.scrollToPosition(0);
        }

        @Override
        public void finishView() {
            cancel();
        }

        @Override
        public void scrollToMessage(int position, int offset) {
            if (layoutManager == null) return;
            layoutManager.scrollToPositionWithOffset(position, offset);
        }
    }
}
