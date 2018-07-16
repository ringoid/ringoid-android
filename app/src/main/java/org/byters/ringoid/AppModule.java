package org.byters.ringoid;

import android.content.Context;

import org.byters.ringoid.controller.data.memorycache.CacheRegister;
import org.byters.ringoid.controller.data.memorycache.ICacheRegister;
import org.byters.ringoid.controller.data.repository.IRepositoryRegister;
import org.byters.ringoid.controller.data.repository.IRepositoryRegisterConfirm;
import org.byters.ringoid.controller.data.repository.RepositoryRegister;
import org.byters.ringoid.controller.data.repository.RepositoryRegisterConfirm;
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
    IRepositoryRegister getRepositoryRegister(){
        return new RepositoryRegister();
    }

    @Provides
    @Singleton
    IRepositoryRegisterConfirm getRepositoryRegisterConfirm(){
        return new RepositoryRegisterConfirm();
    }

}
