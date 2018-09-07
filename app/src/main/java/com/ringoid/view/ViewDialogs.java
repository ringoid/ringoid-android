package com.ringoid.view;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.Context;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheTutorial;
import com.ringoid.view.ui.dialog.DialogNewFaces;
import com.ringoid.view.ui.dialog.DialogWhoLikedYou;
import com.ringoid.view.ui.dialog.callback.IDialogNewFacesListener;
import com.ringoid.view.ui.dialog.callback.IDialogWhoLikedYouListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class ViewDialogs implements IViewDialogs {

    @Inject
    ICacheTutorial cacheTutorial;

    @Inject
    INavigator navigator;

    private ListenerDialogNewFaces listenerDialogNewFaces;
    private ListenerDialogLiked listenerDialogLiked;
    private WeakReference<Context> refContext;
    private WeakReference<DialogNewFaces> refDialogExplore;
    private WeakReference<DialogWhoLikedYou> refDialogLikes;

    public ViewDialogs() {
        ApplicationRingoid.getComponent().inject(this);
        listenerDialogNewFaces = new ListenerDialogNewFaces();
        listenerDialogLiked = new ListenerDialogLiked();
    }

    @Override
    public void showDialogExplore() {
    }

    @Override
    public void set(Context context) {
        this.refContext = new WeakReference<>(context);
    }

    @Override
    public void showDialogLikes() {

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

    private class ListenerDialogLiked implements IDialogWhoLikedYouListener {
        @Override
        public void onSelectAbout(boolean b) {
            cacheTutorial.setProfileDialogLikeShow(b);
            navigator.navigateWelcome(false);
        }

        @Override
        public void onSelectPush(boolean b) {
            cacheTutorial.setProfileDialogLikeShow(b);
            navigator.navigateSettingsPush();
        }

        @Override
        public void onSelectOK(boolean b) {
            cacheTutorial.setProfileDialogLikeShow(b);
        }
    }
}
