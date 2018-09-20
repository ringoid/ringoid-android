/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.model.DataBlacklistPhone;
import com.ringoid.view.presenter.IPresenterAdapterBlacklistPhones;
import com.ringoid.view.presenter.callback.IPresenterAdapterBlacklistPhonesListener;
import com.ringoid.view.ui.dialog.DialogRemoveConfirm;
import com.ringoid.view.ui.dialog.callback.IDialogRemoveConfirmListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class AdapterBlacklistPhones extends AdapterBase {

    @Inject
    IPresenterAdapterBlacklistPhones presenterAdapterBlacklistPhones;
    private WeakReference<Context> refContext;
    private ListenerPresenter listenerPresenter;
    private DialogRemoveConfirm dialogRemoveConfirm;
    private IDialogRemoveConfirmListener listenerDialogRemoveConfirm;

    public AdapterBlacklistPhones(Context context) {
        this.refContext = new WeakReference<>(context);
        ApplicationRingoid.getComponent().inject(this);
        presenterAdapterBlacklistPhones.setListener(listenerPresenter = new ListenerPresenter());
        listenerDialogRemoveConfirm = new ListenerDialogRemoveConfirm();
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

        @Override
        public void showDialogRemove(DataBlacklistPhone item) {
            if (dialogRemoveConfirm != null) dialogRemoveConfirm.cancel();
            if (refContext == null || refContext.get() == null) return;
            dialogRemoveConfirm = new DialogRemoveConfirm(refContext.get(), item, listenerDialogRemoveConfirm);
            dialogRemoveConfirm.show();
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
            String phone = String.format("+%s %s", presenterAdapterBlacklistPhones.getPhoneCode(position), presenterAdapterBlacklistPhones.getPhone(position));

            tvPhone.setText(TextUtils.isEmpty(phone) ? "" : phone);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.ivRemove)
                presenterAdapterBlacklistPhones.onClickRemove(getAdapterPosition());
        }
    }

    private class ListenerDialogRemoveConfirm implements IDialogRemoveConfirmListener {
        @Override
        public void onConfirm(DataBlacklistPhone phone) {
            presenterAdapterBlacklistPhones.onConfirmRemove(phone);
        }
    }
}
