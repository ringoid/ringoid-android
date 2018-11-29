/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.memorycache.ICacheInterfaceState;
import com.ringoid.controller.data.memorycache.ICacheRegister;
import com.ringoid.controller.data.memorycache.ICacheToken;
import com.ringoid.controller.data.memorycache.ICacheUser;
import com.ringoid.controller.data.memorycache.listener.ICacheInterfaceStateListener;
import com.ringoid.controller.data.network.interceptor.InterceptorRetry;
import com.ringoid.controller.data.network.interceptor.listener.IInterceptorRetryListener;
import com.ringoid.view.INavigator;
import com.ringoid.view.IViewDialogs;
import com.ringoid.view.IViewPopup;
import com.ringoid.view.PAGE_ENUM;
import com.ringoid.view.presenter.callback.IPresenterActivityMainListener;
import com.ringoid.view.presenter.util.IHelperPhotoUpload;
import com.ringoid.view.presenter.util.ILogoutHelper;
import com.ringoid.view.presenter.util.SimpleCacheInterfaceStateListener;
import com.ringoid.view.ui.util.IHelperFullscreen;
import com.ringoid.view.ui.util.IHelperScreenshots;
import com.ringoid.view.ui.util.IHelperTheme;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class PresenterActivityMain implements IPresenterActivityMain {

    @Inject
    ICacheToken cacheToken;

    @Inject
    ICacheRegister cacheRegister;

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

    @Inject
    IHelperPhotoUpload helperPhotoUpload;

    @Inject
    ICacheInterfaceState cacheInterfaceState;

    @Inject
    IHelperTheme helperTheme;

    private ListenerInterceptor listenerInterceptor;
    private ICacheInterfaceStateListener listenerCacheInterfaceState;
    private WeakReference<IPresenterActivityMainListener> refListener;

    public PresenterActivityMain() {
        ApplicationRingoid.getComponent().inject(this);
        interceptorRequest.setListener(listenerInterceptor = new ListenerInterceptor());
        cacheInterfaceState.addListener(listenerCacheInterfaceState = new ListenerCacheInterfaceState());
    }

    @Override
    public void onCreateView(AppCompatActivity activity, View view, FragmentManager supportFragmentManager, int viewId) {
        navigator.set(activity, supportFragmentManager, viewId);
        viewDialogs.set(activity, view);
        helperFullscreen.set(activity.getWindow());
        helperScreenshots.set(activity.getWindow());
        helperTheme.set(activity);

        navigate();
    }

    @Override
    public void setListener(IPresenterActivityMainListener listener) {
        this.refListener = new WeakReference<>(listener);
    }

    @Override
    public void onCreate() {
        updateTheme();
    }

    private void navigate() {

        if (!cacheToken.isTokenExist()) {
            if (cacheRegister.isSessionIdExist()
                    && cacheInterfaceState.getCurrentPage().equals(PAGE_ENUM.LOGIN_PROFILE)) {
                //navigator.navigateRegisterCodeConfirm();
                navigator.navigateProfileUpdate();
                return;
            }

            navigator.navigateProfileUpdate();

            return;
        }

        if (!cacheUser.isRegistered()) {
            navigator.navigateProfileUpdate();
            return;
        }

        helperPhotoUpload.checkState();

        if (cacheInterfaceState.getCurrentPage().equals(PAGE_ENUM.FEED_SETTINGS))
            navigator.navigateSettings();
        else
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

    private void updateTheme() {
        if (refListener == null || refListener.get() == null) return;
        refListener.get().setTheme(cacheInterfaceState.getTheme());
    }

    private void restartView() {
        navigator.restartView();
    }

    private class ListenerInterceptor implements IInterceptorRetryListener {
        @Override
        public void onRequestTokenInvalid() {
            logoutHelper.logout();
        }

        @Override
        public void onRequestErrorUnknown() {
            viewDialogs.showDialogErrorUnknown(null);
        }

        @Override
        public void onRequestErrorAppVersion() {
            navigator.navigateErrorAppversion();
        }

        @Override
        public void onRequestErrorConnection() {
            navigator.navigateErrorConnection();
        }
    }

    private class ListenerCacheInterfaceState extends SimpleCacheInterfaceStateListener {
        @Override
        public void onThemeUpdate() {
            restartView();
        }
    }
}
