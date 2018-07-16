package org.byters.ringoid;

import android.content.Context;

import org.byters.ringoid.controller.data.memorycache.CacheToken;
import org.byters.ringoid.controller.data.memorycache.ICacheToken;
import org.byters.ringoid.controller.device.CacheStorage;
import org.byters.ringoid.controller.device.ICacheStorage;
import org.byters.ringoid.view.INavigator;
import org.byters.ringoid.view.Navigator;
import org.byters.ringoid.view.presenter.IPresenterActivityMain;
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
    IPresenterActivityMain getCachePreference() {
        return new PresenterActivityMain();
    }
}
