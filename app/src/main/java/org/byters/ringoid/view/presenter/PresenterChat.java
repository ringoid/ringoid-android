package org.byters.ringoid.view.presenter;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.controller.data.memorycache.ICacheMessages;
import org.byters.ringoid.view.presenter.callback.IPresenterChatListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterChat implements IPresenterChat {

    @Inject
    ICacheMessages cacheMessages;

    private WeakReference<IPresenterChatListener> refListener;

    public PresenterChat() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void onCreateView() {
        setUserImage();

    }

    private void setUserImage() {
        if (refListener == null || refListener.get() == null) return;
        String url = cacheMessages.getUrlSelectedUser();
        refListener.get().setImage("file:///android_asset/"+url);
    }

    @Override
    public void setListener(IPresenterChatListener listener) {
        this.refListener = new WeakReference<>(listener);
    }
}
