package com.ringoid.view.ui.adapter;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterAdapterChatMessages;
import com.ringoid.view.presenter.callback.IPresenterAdapterChatMessagesListener;
import com.ringoid.view.ui.util.GlideApp;

import javax.inject.Inject;

public class AdapterChatMessages extends AdapterBase {

    private final PresenterListener listenerPresenter;
    @Inject
    IPresenterAdapterChatMessages presenterAdapterChatMessages;

    private int TYPE_SELF = 0,
            TYPE_OTHER = 1;


    public AdapterChatMessages() {
        ApplicationRingoid.getComponent().inject(this);
        presenterAdapterChatMessages.setListener(listenerPresenter = new PresenterListener());
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

    private class ViewHolderItemChatMessage extends ViewHolderBase {

        private ImageView ivUser, ivMessage;
        private TextView tvMessage;

        ViewHolderItemChatMessage(ViewGroup parent, int res) {
            super(parent, res);
            ivUser = itemView.findViewById(R.id.ivUser);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            ivMessage = itemView.findViewById(R.id.ivMessage);
        }

        @Override
        void setData(int position) {
            setAvatar(position);
            setMessage(position);
        }

        private void setAvatar(int position) {
            if (ivUser == null) return;
            String url = presenterAdapterChatMessages.getUrl(position);
            if (TextUtils.isEmpty(url))
                ivUser.setImageDrawable(null);
            else
                GlideApp.with(itemView.getContext())
                        .load(url)
                        .circleCrop()
                        .into(ivUser);
        }

        private void setMessage(int position) {
            tvMessage.setVisibility(View.GONE);
            ivMessage.setVisibility(View.GONE);

            if (presenterAdapterChatMessages.isMessageText(position)) {
                String message = presenterAdapterChatMessages.getMessage(position);
                tvMessage.setText(TextUtils.isEmpty(message) ? "" : message);
                tvMessage.setVisibility(View.VISIBLE);
            }

            if (presenterAdapterChatMessages.isMessageSmile(position)) {
                int res = presenterAdapterChatMessages.getMessageSmile(position);
                ivMessage.setImageResource(res);
                ivMessage.setVisibility(View.VISIBLE);
            }
        }
    }

    private class PresenterListener implements IPresenterAdapterChatMessagesListener {
        @Override
        public void onUpdate() {
            notifyDataSetChanged();
        }
    }
}
