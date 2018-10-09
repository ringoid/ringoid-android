/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ringoid.ApplicationRingoid;
import com.ringoid.BuildConfig;
import com.ringoid.controller.data.memorycache.ICacheToken;
import com.ringoid.controller.data.memorycache.ICacheUser;
import com.ringoid.controller.data.network.interceptor.InterceptorRetry;
import com.ringoid.controller.data.network.interceptor.listener.IInterceptorRetryListener;
import com.ringoid.view.INavigator;
import com.ringoid.view.IViewDialogs;
import com.ringoid.view.IViewPopup;
import com.ringoid.view.presenter.util.ILogoutHelper;
import com.ringoid.view.ui.dialog.callback.IDialogErrorAppVersionListener;
import com.ringoid.view.ui.util.IHelperFullscreen;
import com.ringoid.view.ui.util.IHelperScreenshots;

import javax.inject.Inject;

public class PresenterActivityMain implements IPresenterActivityMain {

    @Inject
    ICacheToken cacheToken;

    @Inject
    ICacheUser cacheUser;

    @Inject
    INavigator navigator;

    @Inject
    IViewDialogs viewDialogs;

    @Inject
    IViewPopup viewPopup;

    @Inject
    InterceptorRetry interceptorRequest;

    @Inject
    ILogoutHelper logoutHelper;

    @Inject
    IHelperScreenshots helperScreenshots;

    @Inject
    IHelperFullscreen helperFullscreen;

    private ListenerDialogErrorAppVersion listenerDialogErrorAppVersion;
    private ListenerInterceptor listenerInterceptor;

    public PresenterActivityMain() {
        ApplicationRingoid.getComponent().inject(this);
        interceptorRequest.setListener(listenerInterceptor = new ListenerInterceptor());
        listenerDialogErrorAppVersion = new ListenerDialogErrorAppVersion();
    }

    @Override
    public void onCreateView(AppCompatActivity activity, View view, FragmentManager supportFragmentManager, int viewId) {
        navigator.set(activity, supportFragmentManager, viewId);
        viewDialogs.set(activity);
        viewPopup.setView(view);
        helperFullscreen.set(activity.getWindow());
        helperScreenshots.set(activity.getWindow());

        navigate();
    }

    private void navigate() {
        if (!cacheToken.isTokenExist()) {
            navigator.navigateLogin();
            return;
        }

        if (!cacheUser.isRegistered()) {
            navigator.navigateProfileUpdate();
            return;
        }

        navigator.navigateFeed();
    }

    @Override
    public boolean onBackPressed() {
        return navigator.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        navigator.onActivityResult(requestCode, resultCode, data);
    }

    private class ListenerInterceptor implements IInterceptorRetryListener {
        @Override
        public void onRequestTokenInvalid() {
            logoutHelper.logout();
        }

        @Override
        public void onRequestErrorUnknown() {
            viewDialogs.showDialogErrorUnknown();
        }

        @Override
        public void onRequestErrorAppVersion() {
            viewDialogs.showDialogErrorAppVersion(listenerDialogErrorAppVersion);
        }

        @Override
        public void onRequestErrorConnection() {
            navigator.navigateErrorConnection();
        }
    }

    private class ListenerDialogErrorAppVersion implements IDialogErrorAppVersionListener {
        @Override
        public void onConfirm() {
            if (!BuildConfig.DEBUG)
                throw new IllegalArgumentException();
            navigator.finish();
        }
    }
}
