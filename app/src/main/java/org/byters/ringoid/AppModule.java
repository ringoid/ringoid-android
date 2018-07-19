package org.byters.ringoid;

import android.content.Context;

import org.byters.ringoid.controller.data.memorycache.CacheCountryList;
import org.byters.ringoid.controller.data.memorycache.CacheRegister;
import org.byters.ringoid.controller.data.memorycache.CacheRegisterReferral;
import org.byters.ringoid.controller.data.memorycache.CacheWallet;
import org.byters.ringoid.controller.data.memorycache.ICacheCountryList;
import org.byters.ringoid.controller.data.memorycache.ICacheRegister;
import org.byters.ringoid.controller.data.memorycache.ICacheRegisterReferral;
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
import org.byters.ringoid.view.INavigatorPages;
import org.byters.ringoid.view.NavigatorPages;
import org.byters.ringoid.view.presenter.IPresenterAdapterCountryList;
import org.byters.ringoid.view.presenter.IPresenterPagesContainer;
import org.byters.ringoid.view.presenter.PresenterAdapterCountryList;
import org.byters.ringoid.view.presenter.PresenterPagesContainer;
import org.byters.ringoid.view.presenter.PresenterRegister;
import org.byters.ringoid.controller.data.memorycache.CacheToken;
import org.byters.ringoid.controller.data.memorycache.ICacheToken;
import org.byters.ringoid.controller.device.CacheStorage;
import org.byters.ringoid.controller.device.ICacheStorage;
import org.byters.ringoid.view.INavigator;
import org.byters.ringoid.view.Navigator;
import org.byters.ringoid.view.presenter.IPresenterActivityMain;
import org.byters.ringoid.view.presenter.IPresenterRegister;
import org.byters.ringoid.view.presenter.PresenterActivityMain;

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
    INavigator getNavigator(){
        return new Navigator();
    }

    @Provides
    @Singleton
    INavigatorPages getNavigatorPages(){
        return new NavigatorPages();
    }

    @Provides
    @Singleton
    ICacheToken getCacheToken(){
        return new CacheToken();
    }

    @Provides
    @Singleton
    ICacheStorage getCacheStorage(){
        return new CacheStorage(refContext == null ? null : refContext.get());
    }

    @Provides
    @Singleton
    ICacheRegister getCacheRegister(){
        return new CacheRegister();
    }

    @Provides
    @Singleton
    ICacheWallet getCacheWallet(){
        return new CacheWallet();
    }

    @Provides
    @Singleton
    ICacheCountryList getCacheCountryList(){
        return new CacheCountryList();
    }


    @Provides
    @Singleton
    ICacheRegisterReferral getCacheRegisterReferral(){
        return new CacheRegisterReferral();
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
    IRepositoryRegister getRepositoryRegister(){
        return new RepositoryRegister();
    }

    @Provides
    @Singleton
    IRepositoryCountryList getRepositoryCountryList(){
        return new RepositoryCountryList();
    }

    @Provides
    @Singleton
    IRepositoryWallet getRepositoryWallet(){
        return new RepositoryWallet();
    }


    @Provides
    @Singleton
    IRepositoryRegisterConfirm getRepositoryRegisterConfirm(){
        return new RepositoryRegisterConfirm();
    }

    @Provides
    @Singleton
    IRepositoryRegisterReferralDescription getRepositoryRegisterReferralDescription(){
        return new RepositoryRegisterReferralDescription();
    }


    @Provides
    @Singleton
    IRepositoryRegisterReferralConfirm getRepositoryRegisterReferralConfirm(){
        return new RepositoryRegisterReferralConfirm();
    }

}
