/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.adapter;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterAdapterBlacklistPhones;
import com.ringoid.view.presenter.callback.IPresenterAdapterBlacklistPhonesListener;

import javax.inject.Inject;

public class AdapterBlacklistPhones extends AdapterBase {

    private final ListenerPresenter listenerPresenter;
    @Inject
    IPresenterAdapterBlacklistPhones presenterAdapterBlacklistPhones;

    public AdapterBlacklistPhones() {
        ApplicationRingoid.getComponent().inject(this);
        presenterAdapterBlacklistPhones.setListener(listenerPresenter = new ListenerPresenter());
    }


    @NonNull
    @Override
    public ViewHolderBase onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderItem(parent);
    }

    @Override
    public int getItemCount() {
        return presenterAdapterBlacklistPhones.getItemsNum();
    }

    private class ListenerPresenter implements IPresenterAdapterBlacklistPhonesListener {
        @Override
        public void onUpdate() {
            notifyDataSetChanged();
        }
    }

    private class ViewHolderItem extends ViewHolderBase implements View.OnClickListener {

        private TextView tvPhone;

        ViewHolderItem(ViewGroup parent) {
            super(parent, R.layout.view_item_blacklist_phone);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            itemView.findViewById(R.id.ivRemove).setOnClickListener(this);
        }

        @Override
        void setData(int position) {
            String phone = presenterAdapterBlacklistPhones.getPhone(position);

            tvPhone.setText(TextUtils.isEmpty(phone) ? "" : phone);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.ivRemove)
                presenterAdapterBlacklistPhones.onClickRemove(getAdapterPosition());
        }
    }
}
