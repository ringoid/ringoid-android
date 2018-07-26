package org.byters.ringoid;

import android.content.Context;

import org.byters.ringoid.controller.data.memorycache.CacheBlacklist;
import org.byters.ringoid.controller.data.memorycache.CacheCountryList;
import org.byters.ringoid.controller.data.memorycache.CacheExplore;
import org.byters.ringoid.controller.data.memorycache.CacheLikes;
import org.byters.ringoid.controller.data.memorycache.CacheMessages;
import org.byters.ringoid.controller.data.memorycache.CacheProfile;
import org.byters.ringoid.controller.data.memorycache.CacheRank;
import org.byters.ringoid.controller.data.memorycache.CacheRegister;
import org.byters.ringoid.controller.data.memorycache.CacheRegisterReferral;
import org.byters.ringoid.controller.data.memorycache.CacheScroll;
import org.byters.ringoid.controller.data.memorycache.CacheToken;
import org.byters.ringoid.controller.data.memorycache.CacheWallet;
import org.byters.ringoid.controller.data.memorycache.ICacheBlacklist;
import org.byters.ringoid.controller.data.memorycache.ICacheCountryList;
import org.byters.ringoid.controller.data.memorycache.ICacheExplore;
import org.byters.ringoid.controller.data.memorycache.ICacheLikes;
import org.byters.ringoid.controller.data.memorycache.ICacheMessages;
import org.byters.ringoid.controller.data.memorycache.ICacheProfile;
import org.byters.ringoid.controller.data.memorycache.ICacheRank;
import org.byters.ringoid.controller.data.memorycache.ICacheRegister;
import org.byters.ringoid.controller.data.memorycache.ICacheRegisterReferral;
import org.byters.ringoid.controller.data.memorycache.ICacheScroll;
import org.byters.ringoid.controller.data.memorycache.ICacheToken;
import org.byters.ringoid.controller.data.memorycache.ICacheWallet;
import org.byters.ringoid.controller.data.repository.IRepositoryCountryList;
import org.byters.ringoid.controller.data.repository.IRepositoryRegister;
import org.byters.ringoid.controller.data.repository.IRepositoryRegisterConfirm;
import org.byters.ringoid.controller.data.repository.IRepositoryRegisterReferralConfirm;
import org.byters.ringoid.controller.data.repository.IRepositoryRegisterReferralDescription;
import org.byters.ringoid.controller.data.repository.IRepositoryWallet;
import org.byters.ringoid.controller.data.repository.RepositoryCountryList;
import org.byters.ringoid.controller.data.repository.RepositoryRegister;
import org.byters.ringoid.controller.data.repository.RepositoryRegisterConfirm;
import org.byters.ringoid.controller.data.repository.RepositoryRegisterReferralConfirm;
import org.byters.ringoid.controller.data.repository.RepositoryRegisterReferralDescription;
import org.byters.ringoid.controller.data.repository.RepositoryWallet;
import org.byters.ringoid.controller.device.CacheStorage;
import org.byters.ringoid.controller.device.ICacheStorage;
import org.byters.ringoid.view.INavigator;
import org.byters.ringoid.view.INavigatorPages;
import org.byters.ringoid.view.Navigator;
import org.byters.ringoid.view.NavigatorPages;
import org.byters.ringoid.view.presenter.IPresenterActivityMain;
import org.byters.ringoid.view.presenter.IPresenterAdapterBlacklistPhones;
import org.byters.ringoid.view.presenter.IPresenterAdapterCountryList;
import org.byters.ringoid.view.presenter.IPresenterAdapterExplore;
import org.byters.ringoid.view.presenter.IPresenterAdapterExploreImages;
import org.byters.ringoid.view.presenter.IPresenterAdapterLikes;
import org.byters.ringoid.view.presenter.IPresenterAdapterLikesImages;
import org.byters.ringoid.view.presenter.IPresenterAdapterMessages;
import org.byters.ringoid.view.presenter.IPresenterAdapterMessagesImages;
import org.byters.ringoid.view.presenter.IPresenterAdapterProfile;
import org.byters.ringoid.view.presenter.IPresenterAdapterRank;
import org.byters.ringoid.view.presenter.IPresenterAdapterRankImages;
import org.byters.ringoid.view.presenter.IPresenterBlacklistPhones;
import org.byters.ringoid.view.presenter.IPresenterExplore;
import org.byters.ringoid.view.presenter.IPresenterLikes;
import org.byters.ringoid.view.presenter.IPresenterMessages;
import org.byters.ringoid.view.presenter.IPresenterPagesContainer;
import org.byters.ringoid.view.presenter.IPresenterProfile;
import org.byters.ringoid.view.presenter.IPresenterRank;
import org.byters.ringoid.view.presenter.IPresenterRegister;
import org.byters.ringoid.view.presenter.PresenterActivityMain;
import org.byters.ringoid.view.presenter.PresenterAdapterBlacklistPhones;
import org.byters.ringoid.view.presenter.PresenterAdapterCountryList;
import org.byters.ringoid.view.presenter.PresenterAdapterExplore;
import org.byters.ringoid.view.presenter.PresenterAdapterExploreImages;
import org.byters.ringoid.view.presenter.PresenterAdapterLikes;
import org.byters.ringoid.view.presenter.PresenterAdapterLikesImages;
import org.byters.ringoid.view.presenter.PresenterAdapterMessages;
import org.byters.ringoid.view.presenter.PresenterAdapterMessagesImages;
import org.byters.ringoid.view.presenter.PresenterAdapterProfile;
import org.byters.ringoid.view.presenter.PresenterAdapterRank;
import org.byters.ringoid.view.presenter.PresenterAdapterRankImages;
import org.byters.ringoid.view.presenter.PresenterBlacklistPhones;
import org.byters.ringoid.view.presenter.PresenterExplore;
import org.byters.ringoid.view.presenter.PresenterLikes;
import org.byters.ringoid.view.presenter.PresenterMessages;
import org.byters.ringoid.view.presenter.PresenterPagesContainer;
import org.byters.ringoid.view.presenter.PresenterProfile;
import org.byters.ringoid.view.presenter.PresenterRank;
import org.byters.ringoid.view.presenter.PresenterRegister;

import java.lang.ref.WeakReference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class AppModule {


    private WeakReference<Context> refContext;

    AppModule(Context context) {
        this.refContext = new WeakReference<>(context);
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
    ICacheWallet getCacheWallet() {
        return new CacheWallet();
    }

    @Provides
    @Singleton
    ICacheCountryList getCacheCountryList() {
        return new CacheCountryList();
    }


    @Provides
    @Singleton
    ICacheRegisterReferral getCacheRegisterReferral() {
        return new CacheRegisterReferral();
    }

    @Provides
    @Singleton
    ICacheBlacklist getCacheBlacklist() {
        return new CacheBlacklist();
    }


    @Provides
    @Singleton
    ICacheRank getCacheRank() {
        return new CacheRank();
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
    IPresenterAdapterCountryList getPresenterAdapterCountryList() {
        return new PresenterAdapterCountryList();
    }

    @Provides
    @Singleton
    IPresenterAdapterRank getPresenterAdapterRank() {
        return new PresenterAdapterRank();
    }

    @Provides
    @Singleton
    IPresenterAdapterRankImages getPresenterAdapterRankImages() {
        return new PresenterAdapterRankImages();
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
    IPresenterRank getPresenterRank() {
        return new PresenterRank();
    }

    @Provides
    @Singleton
    IRepositoryRegister getRepositoryRegister() {
        return new RepositoryRegister();
    }

    @Provides
    @Singleton
    IRepositoryCountryList getRepositoryCountryList() {
        return new RepositoryCountryList();
    }

    @Provides
    @Singleton
    IRepositoryWallet getRepositoryWallet() {
        return new RepositoryWallet();
    }


    @Provides
    @Singleton
    IRepositoryRegisterConfirm getRepositoryRegisterConfirm() {
        return new RepositoryRegisterConfirm();
    }

    @Provides
    @Singleton
    IRepositoryRegisterReferralDescription getRepositoryRegisterReferralDescription() {
        return new RepositoryRegisterReferralDescription();
    }


    @Provides
    @Singleton
    IRepositoryRegisterReferralConfirm getRepositoryRegisterReferralConfirm() {
        return new RepositoryRegisterReferralConfirm();
    }

}
