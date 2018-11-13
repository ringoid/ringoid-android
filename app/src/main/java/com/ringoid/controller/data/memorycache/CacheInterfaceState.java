package com.ringoid.controller.data.memorycache;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.ApplicationRingoid;
import com.ringoid.controller.data.FileEnum;
import com.ringoid.controller.data.memorycache.listener.ICacheInterfaceStateListener;
import com.ringoid.controller.device.ICacheStorage;
import com.ringoid.model.ModelInterfaceState;
import com.ringoid.view.PAGE_ENUM;

import java.util.WeakHashMap;

import javax.inject.Inject;

public class CacheInterfaceState implements ICacheInterfaceState {

    @Inject
    ICacheStorage cacheStorage;

    private ModelInterfaceState data;
    private WeakHashMap<String, ICacheInterfaceStateListener> listeners;

    public CacheInterfaceState() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void setProfileOriginPhotoId(String originPhotoId) {
        getData().setOriginPhotoId(originPhotoId);
        saveData();
    }

    @Override
    public void resetOriginPhotoId() {
        getData().setOriginPhotoId(null);
        saveData();
    }

    private void saveData() {
        cacheStorage.writeData(FileEnum.CACHE_INTERFACE, data);
    }

    @Override
    public String getOriginPhotoId() {
        return getData().getOriginPhotoId();
    }

    @Override
    public void resetCache() {
        data = null;
        cacheStorage.removeData(FileEnum.CACHE_INTERFACE);
    }

    private ModelInterfaceState getData() {
        if (data == null)
            data = cacheStorage.readObject(FileEnum.CACHE_INTERFACE, ModelInterfaceState.class);
        if (data == null) {
            data = new ModelInterfaceState();
            resetCurrentPage();
        }
        return data;
    }

    @Override
    public void resetCurrentPage() {
        setCurrentPage(PAGE_ENUM.FEED_PROFILE);
    }

    @Override
    public void addListener(ICacheInterfaceStateListener listener) {
        if (listeners == null) listeners = new WeakHashMap<>();
        listeners.put(listener.getClass().getName(), listener);
    }

    @Override
    public PAGE_ENUM getCurrentPage() {
        return getData().getCurrentPage();
    }

    @Override
    public void setCurrentPage(PAGE_ENUM page) {
        getData().setCurrentPage(page);
        saveData();
        notifyListenersPageUpdated();
    }

    @Override
    public int getPositionScrollPageLikes() {
        return getData().getPositionScrollPageLikes();
    }

    @Override
    public void setPositionScrollPageLikes(int position, int offset) {
        getData().setPositionScrollPageLikes(position, offset);
        saveData();
    }

    @Override
    public int getPositionScrollPageMessages() {
        return getData().getPositionScrollPageMessages();
    }

    @Override
    public void setPositionScrollPageMessages(int position, int offset) {
        getData().setPositionScrollPageMessages(position, offset);
        saveData();
    }

    private void setPositionScrollPageMatches(int position, int offset) {
        getData().setPositionScrollPageMatches(position, offset);
        saveData();
    }

    @Override
    public void setPositionScrollPageExplore(int firstVisibleItemPosition, int offset) {
        getData().setPositionScrollPageExplore(firstVisibleItemPosition, offset);
        saveData();
    }


    private void setPositionScrollPageSettings(int firstVisibleItemPosition, int offset) {
        getData().setPositionScrollPageSettings(firstVisibleItemPosition, offset);
        saveData();
    }

    @Override
    public int getPositionScrollPageExploreOffset() {
        return getData().getPositionScrollPageExploreOffset();
    }

    @Override
    public int getPositionScrollPageLikesOffset() {
        return getData().getPositionScrollPageLikesOffset();
    }

    @Override
    public int getPositionScrollPageMessagesOffset() {
        return getData().getPositionScrollPageMessagesOffset();
    }

    @Override
    public void resetCachePositionLikes() {
        setPositionScrollPageLikes(0, 0);
    }

    @Override
    public void resetCachePositionMessages() {
        setPositionScrollPageMessages(0, 0);
    }

    @Override
    public void resetCachePositionMatches() {
        setPositionScrollPageMatches(0, 0);
    }

    @Override
    public void resetCacheSavedPhone() {
        getData().setPhone(null);
        getData().setPhoneCode(null);
        saveData();
    }

    @Override
    public boolean isPhoneExist() {
        return getData().getPhone() != null;
    }

    @Override
    public int getPhoneCode() {
        String sCode = getData().getPhoneCode();
        if (sCode == null) return 0;
        int code;

        try {
            code = Integer.valueOf(sCode.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            return 0;
        }

        return code;
    }

    @Override
    public void setPhoneCode(String s) {
        getData().setPhoneCode(s);
        saveData();
    }

    @Override
    public String getPhone() {
        return getData().getPhone();
    }

    @Override
    public void setPhone(String s) {
        getData().setPhone(s);
        saveData();
    }

    @Override
    public void resetCachePositionExplore() {
        setPositionScrollPageExplore(0, 0);
    }

    @Override
    public void setPositionScrollSettings(int firsVisibleItemPosition, int offset) {
        setPositionScrollPageSettings(firsVisibleItemPosition, offset);
    }

    @Override
    public int getPositionScrollPageSettings() {
        return getData().getPositionScrollPageSettings();
    }

    @Override
    public int getPositionScrollPageSettingsOffset() {
        return getData().getPositionScrollPageSettingsOffset();
    }

    @Override
    public void setDialogComposeShowState(boolean isShown) {
        getData().setDialogComposeShow(isShown);
        notifyListenersDialogComposeState(isShown);
    }

    @Override
    public boolean isDialogComposeShown() {
        return getData().isDialogComposeShown();
    }

    @Override
    public void updateTheme() {
        getData().updateTheme();
        saveData();
        notifyListenersThemeUpdated();
    }

    @Override
    public void setThemeDark(boolean isChecked) {
        getData().setThemeDark(isChecked);
        saveData();
        notifyListenersThemeUpdated();
    }


    @Override
    public int getTheme() {
        return getData().getThemeId();
    }

    @Override
    public PAGE_ENUM getPageLikes() {
        return getData().getPageLikes();
    }

    @Override
    public void setPageLikes(PAGE_ENUM page) {
        getData().setPageLikes(page);
        saveData();
    }

    @Override
    public int getPositionScrollPageExplore() {
        return getData().getPositionScrollPageExplore();
    }

    private void notifyListenersThemeUpdated() {
        if (listeners == null) return;
        for (String key : listeners.keySet()) {
            ICacheInterfaceStateListener listener = listeners.get(key);
            if (listener == null) continue;
            listener.onThemeUpdate();
        }
    }

    private void notifyListenersPageUpdated() {
        if (listeners == null) return;
        for (String key : listeners.keySet()) {
            ICacheInterfaceStateListener listener = listeners.get(key);
            if (listener == null) continue;
            listener.onPageSelected(getData().getCurrentPage());
        }
    }


    private void notifyListenersDialogComposeState(boolean isShown) {
        if (listeners == null) return;
        for (String key : listeners.keySet()) {
            ICacheInterfaceStateListener listener = listeners.get(key);
            if (listener == null) continue;
            listener.onDialogComposeShowState(isShown);
        }

    }
}
