package com.ringoid.view.ui.util;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.controller.data.memorycache.ICacheInterfaceState;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class HelperTheme implements IHelperTheme {

    WeakReference<Context> refContext;

    @Inject
    ICacheInterfaceState cacheInterfaceState;

    public HelperTheme() {
        ApplicationRingoid.getComponent().inject(this);
    }

    public void set(Context context) {
        this.refContext = new WeakReference<>(context);
    }

    @Override
    public int getDrawableMenuProfileActive() {
        return cacheInterfaceState.isThemeDark() ? R.drawable.ic_menu_profile_white_24dp : R.drawable.ic_menu_profile_black_24dp;
    }

    @Override
    public int getDrawableMenuProfile() {
        return cacheInterfaceState.isThemeDark() ? R.drawable.ic_menu_profile_24dp : R.drawable.ic_menu_profile_gray_24dp;
    }

    @Override
    public int getDrawableMenuLikesActive() {
        return cacheInterfaceState.isThemeDark() ? R.drawable.ic_menu_favorite_white_24dp : R.drawable.ic_menu_favorite_black_24dp;
    }

    @Override
    public int getDrawableMenuLikes() {
        return cacheInterfaceState.isThemeDark() ? R.drawable.ic_menu_favorite_24dp : R.drawable.ic_menu_favorite_gray_24dp;
    }

    @Override
    public int getDrawableMenuExploreActive() {
        return cacheInterfaceState.isThemeDark() ? R.drawable.ic_menu_explore_white_24dp : R.drawable.ic_menu_explore_black_24dp;
    }

    @Override
    public int getDrawableMenuExplore() {
        return cacheInterfaceState.isThemeDark() ? R.drawable.ic_menu_explore_24dp : R.drawable.ic_menu_explore_gray_24dp;
    }

    @Override
    public int getColor(int res) {
        if (refContext == null || refContext.get() == null) return 0;

        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = refContext.get().getTheme();
        theme.resolveAttribute(res, typedValue, true);
        return typedValue.data;
    }
}
