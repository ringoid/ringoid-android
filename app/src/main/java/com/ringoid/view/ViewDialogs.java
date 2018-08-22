package com.ringoid.view;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.Context;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheTutorial;
import com.ringoid.view.ui.dialog.DialogNewFaces;
import com.ringoid.view.ui.dialog.callback.IDialogNewFacesListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class ViewDialogs implements IViewDialogs {

    @Inject
    ICacheTutorial cacheTutorial;

    private ListenerDialogNewFaces listenerDialogNewFaces;
    private WeakReference<Context> refContext;
    private WeakReference<DialogNewFaces> refDialogExplore;

    public ViewDialogs() {
        ApplicationRingoid.getComponent().inject(this);
        listenerDialogNewFaces = new ListenerDialogNewFaces();
    }

    @Override
    public void showDialogExplore() {
        if (refDialogExplore != null && refDialogExplore.get() != null)
            refDialogExplore.get().cancel();

        if (!cacheTutorial.isShowDialogExplore()) return;

        if (refContext == null || refContext.get() == null) return;

        DialogNewFaces dialogNewFaces = new DialogNewFaces(refContext.get(), listenerDialogNewFaces);
        dialogNewFaces.show();
        refDialogExplore = new WeakReference<>(dialogNewFaces);
    }

    @Inject
    INavigator navigator;

    @Override
    public void set(Context context) {
        this.refContext = new WeakReference<>(context);
    }

    private class ListenerDialogNewFaces implements IDialogNewFacesListener {

        @Override
        public void onSelectAbout(boolean b) {
            cacheTutorial.setDialogExploreShow(b);
            navigator.navigateWelcome(false);
        }

        @Override
        public void onSelectPush(boolean b) {
            cacheTutorial.setDialogExploreShow(b);
            navigator.navigateSettingsPush();
        }

        @Override
        public void onSelectOK(boolean b) {
            cacheTutorial.setDialogExploreShow(b);
        }
    }
}
