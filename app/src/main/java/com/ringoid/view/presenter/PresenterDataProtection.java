package com.ringoid.view.presenter;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheUser;
import com.ringoid.view.IViewPopup;
import com.ringoid.view.presenter.callback.IPresenterDataProtectionListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import static android.content.Context.CLIPBOARD_SERVICE;

public class PresenterDataProtection implements IPresenterDataProtection {

    @Inject
    IViewPopup viewPopup;

    @Inject
    ICacheUser cacheUser;

    @Inject
    WeakReference<Context> refContext;

    private WeakReference<IPresenterDataProtectionListener> refListener;

    public PresenterDataProtection() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void onCreateView() {
        updateViewCustomerID();
    }

    private void updateViewCustomerID() {
        String customerId = cacheUser.getCustomerID();
        if (TextUtils.isEmpty(customerId)) return;
        if (refListener == null || refListener.get() == null) return;
        refListener.get().setCustomerId(customerId);
    }

    @Override
    public void setListener(IPresenterDataProtectionListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void onClickCustomerId() {
        copyToClipboard();
        viewPopup.showToast(R.string.message_copy_to_clipboard);
    }

    private void copyToClipboard() {
        if (refContext == null || refContext.get() == null) return;
        String customerId = cacheUser.getCustomerID();
        if (TextUtils.isEmpty(customerId)) return;
        ClipboardManager clipboardManager = (ClipboardManager) refContext.get().getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText(refContext.get().getString(R.string.message_clipboard_customer_id), customerId));
    }
}
