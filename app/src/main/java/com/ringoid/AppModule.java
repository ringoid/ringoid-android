/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ringoid.controller.data.memorycache.CacheBlacklist;
import com.ringoid.controller.data.memorycache.CacheChatMessages;
import com.ringoid.controller.data.memorycache.CacheExplore;
import com.ringoid.controller.data.memorycache.CacheLikes;
import com.ringoid.controller.data.memorycache.CacheLocale;
import com.ringoid.controller.data.memorycache.CacheMessages;
import com.ringoid.controller.data.memorycache.CacheProfile;
import com.ringoid.controller.data.memorycache.CacheRegister;
import com.ringoid.controller.data.memorycache.CacheScroll;
import com.ringoid.controller.data.memorycache.CacheSettingsPrivacy;
import com.ringoid.controller.data.memorycache.CacheToken;
import com.ringoid.controller.data.memorycache.CacheTutorial;
import com.ringoid.controller.data.memorycache.CacheUser;
import com.ringoid.controller.data.memorycache.ICacheBlacklist;
import com.ringoid.controller.data.memorycache.ICacheChatMessages;
import com.ringoid.controller.data.memorycache.ICacheExplore;
import com.ringoid.controller.data.memorycache.ICacheLikes;
import com.ringoid.controller.data.memorycache.ICacheLocale;
import com.ringoid.controller.data.memorycache.ICacheMessages;
import com.ringoid.controller.data.memorycache.ICacheProfile;
import com.ringoid.controller.data.memorycache.ICacheRegister;
import com.ringoid.controller.data.memorycache.ICacheScroll;
import com.ringoid.controller.data.memorycache.ICacheSettingsPrivacy;
import com.ringoid.controller.data.memorycache.ICacheToken;
import com.ringoid.controller.data.memorycache.ICacheTutorial;
import com.ringoid.controller.data.memorycache.ICacheUser;
import com.ringoid.controller.data.network.IApiRingoid;
import com.ringoid.controller.data.network.interceptor.InterceptorRetry;
import com.ringoid.controller.data.repository.IRepositoryRegisterCodeConfirm;
import com.ringoid.controller.data.repository.IRepositoryRegisterPhone;
import com.ringoid.controller.data.repository.IRepositoryRegisterUserDetails;
import com.ringoid.controller.data.repository.IRepositorySettingsGet;
import com.ringoid.controller.data.repository.IRepositorySettingsSave;
import com.ringoid.controller.data.repository.RepositoryRegisterCodeConfirm;
import com.ringoid.controller.data.repository.RepositoryRegisterPhone;
import com.ringoid.controller.data.repository.RepositoryRegisterUserDetails;
import com.ringoid.controller.data.repository.RepositorySettingsGet;
import com.ringoid.controller.data.repository.RepositorySettingsSave;
import com.ringoid.controller.device.CacheStorage;
import com.ringoid.controller.device.ICacheStorage;
import com.ringoid.view.INavigator;
import com.ringoid.view.INavigatorPages;
import com.ringoid.view.IViewDialogs;
import com.ringoid.view.IViewPopup;
import com.ringoid.view.Navigator;
import com.ringoid.view.NavigatorPages;
import com.ringoid.view.ViewDialogs;
import com.ringoid.view.ViewPopup;
import com.ringoid.view.presenter.IPresenterActivityMain;
import com.ringoid.view.presenter.IPresenterAdapterBlacklistPhones;
import com.ringoid.view.presenter.IPresenterAdapterChatMessages;
import com.ringoid.view.presenter.IPresenterAdapterExplore;
import com.ringoid.view.presenter.IPresenterAdapterExploreImages;
import com.ringoid.view.presenter.IPresenterAdapterLikes;
import com.ringoid.view.presenter.IPresenterAdapterLikesImages;
import com.ringoid.view.presenter.IPresenterAdapterMessages;
import com.ringoid.view.presenter.IPresenterAdapterMessagesImages;
import com.ringoid.view.presenter.IPresenterAdapterProfile;
import com.ringoid.view.presenter.IPresenterBlacklistPhones;
import com.ringoid.view.presenter.IPresenterChat;
import com.ringoid.view.presenter.IPresenterExplore;
import com.ringoid.view.presenter.IPresenterItemImageLikeable;
import com.ringoid.view.presenter.IPresenterLikes;
import com.ringoid.view.presenter.IPresenterMessages;
import com.ringoid.view.presenter.IPresenterPagesContainer;
import com.ringoid.view.presenter.IPresenterProfile;
import com.ringoid.view.presenter.IPresenterRegister;
import com.ringoid.view.presenter.IPresenterSettingsPrivacy;
import com.ringoid.view.presenter.IPresenterSettingsPrivacyDistance;
import com.ringoid.view.presenter.IPresenterSettingsPush;
import com.ringoid.view.presenter.PresenterActivityMain;
import com.ringoid.view.presenter.PresenterAdapterBlacklistPhones;
import com.ringoid.view.presenter.PresenterAdapterChatMessages;
import com.ringoid.view.presenter.PresenterAdapterExplore;
import com.ringoid.view.presenter.PresenterAdapterExploreImages;
import com.ringoid.view.presenter.PresenterAdapterLikes;
import com.ringoid.view.presenter.PresenterAdapterLikesImages;
import com.ringoid.view.presenter.PresenterAdapterMessages;
import com.ringoid.view.presenter.PresenterAdapterMessagesImages;
import com.ringoid.view.presenter.PresenterAdapterProfile;
import com.ringoid.view.presenter.PresenterBlacklistPhones;
import com.ringoid.view.presenter.PresenterChat;
import com.ringoid.view.presenter.PresenterExplore;
import com.ringoid.view.presenter.PresenterItemImageLikeable;
import com.ringoid.view.presenter.PresenterLikes;
import com.ringoid.view.presenter.PresenterMessages;
import com.ringoid.view.presenter.PresenterPagesContainer;
import com.ringoid.view.presenter.PresenterProfile;
import com.ringoid.view.presenter.PresenterRegister;
import com.ringoid.view.presenter.PresenterSettingsPrivacy;
import com.ringoid.view.presenter.PresenterSettingsPrivacyDistance;
import com.ringoid.view.presenter.PresenterSettingsPush;
import com.ringoid.view.presenter.util.ISettingsHelper;
import com.ringoid.view.presenter.util.SettingsHelper;
import com.ringoid.view.ui.util.IStatusBarViewHelper;
import com.ringoid.view.ui.util.StatusBarViewHelper;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
class AppModule {


    private WeakReference<Context> refContext;

    AppModule(Context context) {
        this.refContext = new WeakReference<>(context);
    }

    @Provides
    @Singleton
    public IApiRingoid getApi() {

        Gson gson = new GsonBuilder().create();

        Interceptor interceptorRetry = new InterceptorRetry(gson);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(interceptorRetry);

        OkHttpClient client = builder.build();


        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(IApiRingoid.class);
    }

    @Provides
    @Singleton
    WeakReference<Context> getContext() {
        return refContext;
    }

    @Provides
    @Singleton
    INavigator getNavigator() {
        return new Navigator();
    }

    @Provides
    @Singleton
    INavigatorPages getNavigatorPages() {
        return new NavigatorPages();
    }

    @Provides
    @Singleton
    IViewPopup getViewPopup() {
        return new ViewPopup();
    }

    @Provides
    @Singleton
    IViewDialogs getViewDialogs() {
        return new ViewDialogs();
    }

    @Provides
    @Singleton
    IStatusBarViewHelper getStatusBarViewHelper() {
        return new StatusBarViewHelper();
    }

    @Provides
    @Singleton
    ISettingsHelper getSettingsHelper() {
        return new SettingsHelper();
    }

    //region cache


    @Provides
    @Singleton
    ICacheToken getCacheToken() {
        return new CacheToken();
    }

    @Provides
    @Singleton
    ICacheStorage getCacheStorage() {
        return new CacheStorage(refContext == null ? null : refContext.get());
    }

    @Provides
    @Singleton
    ICacheRegister getCacheRegister() {
        return new CacheRegister();
    }

    @Provides
    @Singleton
    ICacheBlacklist getCacheBlacklist() {
        return new CacheBlacklist();
    }

    @Provides
    @Singleton
    ICacheExplore getCacheExplore() {
        return new CacheExplore();
    }

    @Provides
    @Singleton
    ICacheMessages getCacheMessages() {
        return new CacheMessages();
    }

    @Provides
    @Singleton
    ICacheLikes getCacheLikes() {
        return new CacheLikes();
    }

    @Provides
    @Singleton
    ICacheScroll getCacheScroll() {
        return new CacheScroll();
    }

    @Provides
    @Singleton
    ICacheProfile getCacheProfile() {
        return new CacheProfile();
    }

    @Provides
    @Singleton
    ICacheChatMessages getCacheChatMessages() {
        return new CacheChatMessages();
    }


    @Provides
    @Singleton
    ICacheSettingsPrivacy getCacheSettingsPrivacy() {
        return new CacheSettingsPrivacy();
    }

    @Provides
    @Singleton
    ICacheUser getCacheUser() {
        return new CacheUser();
    }

    @Provides
    @Singleton
    ICacheTutorial getCacheTutorial() {
        return new CacheTutorial();
    }

    @Provides
    @Singleton
    ICacheLocale getCacheLocale() {
        return new CacheLocale();
    }

    //endregion

    //region presenter


    @Provides
    @Singleton
    IPresenterActivityMain getPresenterActivityMain() {
        return new PresenterActivityMain();
    }

    @Provides
    @Singleton
    IPresenterRegister getPresenterRegister() {
        return new PresenterRegister();
    }

    @Provides
    @Singleton
    IPresenterPagesContainer getPresenterPagesContainer() {
        return new PresenterPagesContainer();
    }

    @Provides
    @Singleton
    IPresenterAdapterExplore getPresenterAdapterExplore() {
        return new PresenterAdapterExplore();
    }

    @Provides
    @Singleton
    IPresenterAdapterExploreImages getPresenterAdapterExploreImages() {
        return new PresenterAdapterExploreImages();
    }

    @Provides
    @Singleton
    IPresenterExplore getPresenterExplore() {
        return new PresenterExplore();
    }

    @Provides
    @Singleton
    IPresenterMessages getPresenterMessages() {
        return new PresenterMessages();
    }

    @Provides
    @Singleton
    IPresenterAdapterMessages getPresenterAdapterMessages() {
        return new PresenterAdapterMessages();
    }

    @Provides
    @Singleton
    IPresenterAdapterMessagesImages getPresenterAdapterMessagesImages() {
        return new PresenterAdapterMessagesImages();
    }


    @Provides
    @Singleton
    IPresenterItemImageLikeable getPresenterItemImageLikeable() {
        return new PresenterItemImageLikeable();
    }

    @Provides
    @Singleton
    IPresenterAdapterLikes getPresenterAdapterLikes() {
        return new PresenterAdapterLikes();
    }

    @Provides
    @Singleton
    IPresenterAdapterLikesImages getPresenterAdapterLikesImages() {
        return new PresenterAdapterLikesImages();
    }

    @Provides
    @Singleton
    IPresenterLikes getPresenterLikes() {
        return new PresenterLikes();
    }

    @Provides
    @Singleton
    IPresenterBlacklistPhones getPresenterBlacklistPhones() {
        return new PresenterBlacklistPhones();
    }

    @Provides
    @Singleton
    IPresenterAdapterBlacklistPhones getPresenterAdapterBlacklistPhones() {
        return new PresenterAdapterBlacklistPhones();
    }

    @Provides
    @Singleton
    IPresenterProfile getPresenterProfile() {
        return new PresenterProfile();
    }

    @Provides
    @Singleton
    IPresenterAdapterProfile getPresenterAdapterProfile() {
        return new PresenterAdapterProfile();
    }

    @Provides
    @Singleton
    IPresenterChat getPresenterChat() {
        return new PresenterChat();
    }

    @Provides
    @Singleton
    IPresenterAdapterChatMessages getPresenterAdapterChatMessages() {
        return new PresenterAdapterChatMessages();
    }

    @Provides
    @Singleton
    IPresenterSettingsPrivacy getPresenterSettingsPrivacy() {
        return new PresenterSettingsPrivacy();
    }

    @Provides
    @Singleton
    IPresenterSettingsPrivacyDistance getPresenterSettingsPrivacyDistance() {
        return new PresenterSettingsPrivacyDistance();
    }

    @Provides
    @Singleton
    IPresenterSettingsPush getPresenterSettingsPush() {
        return new PresenterSettingsPush();
    }

    //endregion

    //region repository

    @Provides
    @Singleton
    IRepositoryRegisterPhone getRepositoryRegister() {
        return new RepositoryRegisterPhone();
    }

    @Provides
    @Singleton
    IRepositoryRegisterCodeConfirm getRepositoryRegisterConfirm() {
        return new RepositoryRegisterCodeConfirm();
    }

    @Provides
    @Singleton
    IRepositoryRegisterUserDetails getRepositoryRegisterUserDetails() {
        return new RepositoryRegisterUserDetails();
    }

    @Provides
    @Singleton
    IRepositorySettingsSave getRepositorySettingsSave() {
        return new RepositorySettingsSave();
    }

    @Provides
    @Singleton
    IRepositorySettingsGet getRepositorySettingsGet() {
        return new RepositorySettingsGet();
    }

    //endregion
}
