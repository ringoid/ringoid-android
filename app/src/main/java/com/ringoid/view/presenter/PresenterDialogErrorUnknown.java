package com.ringoid.view.presenter;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.repository.IRepositoryErrorUnknown;
import com.ringoid.controller.data.repository.callback.IRepositoryErrorUnknownListener;
import com.ringoid.view.presenter.callback.IPresenterDialogErrorUnknownListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterDialogErrorUnknown implements IPresenterDialogErrorUnknown {

    @Inject
    IRepositoryErrorUnknown repositoryErrorUnknown;

    private IRepositoryErrorUnknownListener listenerRepository;
    private WeakReference<IPresenterDialogErrorUnknownListener> refListener;

    public PresenterDialogErrorUnknown() {
        ApplicationRingoid.getComponent().inject(this);
        repositoryErrorUnknown.setListener(listenerRepository = new ListenerRepository());
    }

    @Override
    public void setListener(IPresenterDialogErrorUnknownListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void onShow() {
        repositoryErrorUnknown.request();
    }

    private class ListenerRepository implements IRepositoryErrorUnknownListener {
        @Override
        public void onSuccess(String message) {
            if (refListener == null || refListener.get() == null) return;
            refListener.get().setMessage(message);
        }
    }
}
