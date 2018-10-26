package com.ringoid.view.ui.dialog;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheChatMessages;
import com.ringoid.controller.data.memorycache.ICacheInterfaceState;
import com.ringoid.controller.data.memorycache.ICacheMessageCompose;
import com.ringoid.controller.data.memorycache.ICacheMessages;
import com.ringoid.view.IViewPopup;
import com.ringoid.view.ui.adapter.AdapterChatMessages;
import com.ringoid.view.ui.util.DividerItemDecoration;
import com.ringoid.view.ui.util.IHelperMessageSend;
import com.ringoid.view.ui.util.KeyboardUtils;
import com.ringoid.view.ui.view.EditTextPreIme;
import com.ringoid.view.ui.view.callback.IEditTextPreImeListener;

import javax.inject.Inject;

public class DialogChatCompose implements View.OnClickListener {

    PopupWindow dialog;

    @Inject
    KeyboardUtils keyboardUtils;

    @Inject
    IViewPopup viewPopup;

    @Inject
    IHelperMessageSend helperMessageSend;

    @Inject
    ICacheMessages cacheMessages;

    @Inject
    ICacheMessageCompose cacheMessageCompose;

    @Inject
    ICacheInterfaceState cacheInterfaceState;

    @Inject
    ICacheChatMessages cacheChatMessages;

    private View viewContainer;

    private EditTextPreIme etMessage;
    private RecyclerView rvMessages;

    public DialogChatCompose(Context context, View container) {
        ApplicationRingoid.getComponent().inject(this);
        this.viewContainer = container;

        View popupView = LayoutInflater.from(context).inflate(R.layout.view_dialog_chat_compose, null);
        initViews(popupView);

        dialog = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        dialog.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        dialog.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        dialog.setAnimationStyle(0);

        dialog.setOutsideTouchable(true);
        dialog.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setOnDismissListener(new ListenerDismiss());
    }

    private void initViews(View view) {
        view.findViewById(R.id.ivSend).setOnClickListener(this);
        etMessage = view.findViewById(R.id.etMessage);
        etMessage.setListener(new ListenerViewPreIme());

        String message = cacheMessageCompose.getMessage();
        if (!TextUtils.isEmpty(message)) {
            etMessage.setText(message);
            etMessage.setSelection(message.length());
            cacheMessageCompose.resetCache();
        }
        initList(view);

    }

    private void initList(View view) {
        rvMessages = view.findViewById(R.id.rvItems);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setReverseLayout(true);
        rvMessages.setLayoutManager(layoutManager);
        rvMessages.setAdapter(new AdapterChatMessages());
        rvMessages.addItemDecoration(new DividerItemDecoration(view.getContext()));
        scrollToEnd();
    }

    private void scrollToEnd() {
        int dataSize = cacheChatMessages.getDataSize(cacheMessages.getUserSelectedID());
        rvMessages.scrollToPosition(0);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivSend) {
            String message = getMessage();
            if (!TextUtils.isEmpty(message)) {
                viewPopup.showToast(R.string.message_sent);
                helperMessageSend.sendMessage(cacheMessages.getUserSelectedID(), message);
                etMessage.setText("");
                scrollToEnd();
            }
        }
    }

    private String getMessage() {
        return etMessage.getText().toString().trim();
    }

    public void show() {
        if (dialog == null) return;
        dialog.showAtLocation(viewContainer, Gravity.BOTTOM, 0, 0);
        cacheInterfaceState.setDialogComposeShowState(true);
        etMessage.requestFocus();
    }

    public void cancel() {
        if (dialog == null) return;
        dialog.dismiss();
    }

    private class ListenerDismiss implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            keyboardUtils.keyboardHide(viewContainer.getContext(), viewContainer);

            cacheMessageCompose.setMessage(getMessage());
            cacheInterfaceState.setDialogComposeShowState(false);
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
}
