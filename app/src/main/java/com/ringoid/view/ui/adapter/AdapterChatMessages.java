/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.adapter;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterAdapterChatMessages;
import com.ringoid.view.presenter.callback.IPresenterAdapterChatMessagesListener;

import javax.inject.Inject;

public class AdapterChatMessages extends AdapterBase {

    @Inject
    IPresenterAdapterChatMessages presenterAdapterChatMessages;

    private PresenterListener listenerPresenter;
    private View.OnClickListener listener;

    private int TYPE_SELF = 0,
            TYPE_OTHER = 1;

    public AdapterChatMessages(View.OnClickListener listener) {
        ApplicationRingoid.getComponent().inject(this);
        presenterAdapterChatMessages.setListener(listenerPresenter = new PresenterListener());
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolderBase onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderItemChatMessage(parent, viewType == TYPE_SELF ? R.layout.view_message_self : R.layout.view_message_other);
    }

    @Override
    public int getItemCount() {
        return presenterAdapterChatMessages.getItemsNum();
    }

    @Override
    public int getItemViewType(int position) {
        return presenterAdapterChatMessages.isMessageSelf(position) ? TYPE_SELF : TYPE_OTHER;
    }

    private class ViewHolderItemChatMessage extends ViewHolderBase
            implements View.OnLongClickListener, View.OnClickListener {

        private TextView tvMessage;

        ViewHolderItemChatMessage(ViewGroup parent, int res) {
            super(parent, res);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        void setData(int position) {
            setMessage(position);
        }

        private void setMessage(int position) {
            String message = presenterAdapterChatMessages.getMessage(position);
            tvMessage.setText(TextUtils.isEmpty(message) ? "" : message);
        }

        @Override
        public boolean onLongClick(View v) {
            presenterAdapterChatMessages.onLongClick(itemView.getContext(), getAdapterPosition());
            return true;
        }

        @Override
        public void onClick(View v) {
            if (listener == null) return;
            listener.onClick(v);
        }
    }

    private class PresenterListener implements IPresenterAdapterChatMessagesListener {
        @Override
        public void onUpdate() {
            notifyDataSetChanged();
        }
    }
}
