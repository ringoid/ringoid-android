package org.byters.ringoid;

import org.byters.ringoid.view.presenter.IPresenterActivityMain;
import org.byters.ringoid.view.presenter.PresenterActivityMain;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {


    @Provides
    @Singleton
    IPresenterActivityMain getCachePreference() {
        return new PresenterActivityMain();
    }
}
