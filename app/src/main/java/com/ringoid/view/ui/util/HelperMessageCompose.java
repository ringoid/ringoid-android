package com.ringoid.view.ui.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheMessages;
import com.ringoid.model.DataProfile;
import com.ringoid.view.IViewDialogs;
import com.ringoid.view.ui.util.listener.IHelperMessageComposeListener;

import java.util.WeakHashMap;

import javax.inject.Inject;

public class HelperMessageCompose implements IHelperMessageCompose {

    @Inject
    IViewDialogs viewDialogs;

    @Inject
    ICacheMessages cacheMessages;


    private WeakHashMap<String, IHelperMessageComposeListener> listeners;

    public HelperMessageCompose() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void onClick(DataProfile user) {
        cacheMessages.setUserSelected(user);
        notifyListeners(user.getId());
        viewDialogs.showDialogChatCompose();
    }

    @Override
    public void addListener(IHelperMessageComposeListener listener) {
        if (listeners == null) listeners = new WeakHashMap<>();
        listeners.put(listener.getClass().getName(), listener);
    }

    private void notifyListeners(String userId) {
        if (listeners == null) return;
        for (String name : listeners.keySet()) {
            IHelperMessageComposeListener listener = listeners.get(name);
            if (listener == null) continue;
            listener.scrollToPosition(userId);
        }
    }
}
