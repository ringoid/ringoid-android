package org.byters.ringoid;

import org.byters.ringoid.controller.data.memorycache.CacheToken;
import org.byters.ringoid.controller.data.repository.RepositoryRegister;
import org.byters.ringoid.controller.data.repository.RepositoryRegisterConfirm;
import org.byters.ringoid.view.presenter.PresenterActivityMain;
import org.byters.ringoid.view.presenter.PresenterPagesContainer;
import org.byters.ringoid.view.presenter.PresenterRegister;
import org.byters.ringoid.view.ui.activity.ActivityMain;
import org.byters.ringoid.view.ui.fragment.FragmentPages;
import org.byters.ringoid.view.ui.fragment.FragmentLogin;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    void inject(ActivityMain activityMain);

    void inject(PresenterActivityMain presenterActivityMain);

    void inject(CacheToken cacheToken);

    void inject(FragmentLogin fragmentLogin);

    void inject(PresenterRegister presenterRegister);

    void inject(RepositoryRegister repositoryRegister);

    void inject(RepositoryRegisterConfirm repositoryRegisterConfirm);

    void inject(FragmentPages fragmentPages);

    void inject(PresenterPagesContainer presenterPagesContainer);
}
