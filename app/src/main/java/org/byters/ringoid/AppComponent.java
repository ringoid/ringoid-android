package org.byters.ringoid;

import org.byters.ringoid.controller.data.memorycache.CacheToken;
import org.byters.ringoid.view.presenter.PresenterActivityMain;
import org.byters.ringoid.view.ui.activity.ActivityMain;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    void inject(ActivityMain activityMain);

    void inject(PresenterActivityMain presenterActivityMain);

    void inject(CacheToken cacheToken);
}
