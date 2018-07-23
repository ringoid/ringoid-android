package org.byters.ringoid;

import org.byters.ringoid.controller.data.memorycache.CacheToken;
import org.byters.ringoid.controller.data.repository.RepositoryCountryList;
import org.byters.ringoid.controller.data.repository.RepositoryRegister;
import org.byters.ringoid.controller.data.repository.RepositoryRegisterConfirm;
import org.byters.ringoid.controller.data.repository.RepositoryRegisterReferralConfirm;
import org.byters.ringoid.controller.data.repository.RepositoryRegisterReferralDescription;
import org.byters.ringoid.view.presenter.PresenterActivityMain;
import org.byters.ringoid.view.presenter.PresenterAdapterBlacklistPhones;
import org.byters.ringoid.view.presenter.PresenterAdapterCountryList;
import org.byters.ringoid.view.presenter.PresenterAdapterProfile;
import org.byters.ringoid.view.presenter.PresenterAdapterRank;
import org.byters.ringoid.view.presenter.PresenterAdapterRankImages;
import org.byters.ringoid.view.presenter.PresenterBlacklistPhones;
import org.byters.ringoid.view.presenter.PresenterPagesContainer;
import org.byters.ringoid.view.presenter.PresenterProfile;
import org.byters.ringoid.view.presenter.PresenterRegister;
import org.byters.ringoid.view.ui.activity.ActivityMain;
import org.byters.ringoid.view.ui.adapter.AdapterBlacklistPhones;
import org.byters.ringoid.view.ui.adapter.AdapterCountryList;
import org.byters.ringoid.view.ui.adapter.AdapterProfile;
import org.byters.ringoid.view.ui.adapter.AdapterRank;
import org.byters.ringoid.view.ui.adapter.AdapterRankImages;
import org.byters.ringoid.view.ui.fragment.FragmentBlacklistPhones;
import org.byters.ringoid.view.ui.fragment.FragmentLogin;
import org.byters.ringoid.view.ui.fragment.FragmentPages;
import org.byters.ringoid.view.ui.fragment.FragmentProfile;
import org.byters.ringoid.view.ui.fragment.FragmentSettings;

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

    void inject(AdapterCountryList adapterCountryList);

    void inject(RepositoryCountryList repositoryCountryList);

    void inject(PresenterAdapterCountryList presenterAdapterCountryList);

    void inject(RepositoryRegisterReferralDescription repositoryRegisterReferralDescription);

    void inject(RepositoryRegisterReferralConfirm repositoryRegisterReferralConfirm);

    void inject(AdapterRank adapterRank);

    void inject(AdapterRankImages adapterRankImages);

    void inject(FragmentSettings fragmentSettings);

    void inject(FragmentBlacklistPhones fragmentBlacklistPhones);

    void inject(AdapterBlacklistPhones adapterBlacklistPhones);

    void inject(PresenterBlacklistPhones presenterBlacklistPhones);

    void inject(PresenterAdapterBlacklistPhones presenterAdapterBlacklistPhones);

    void inject(PresenterAdapterRank presenterAdapterRank);

    void inject(PresenterAdapterRankImages presenterAdapterRankImages);

    void inject(AdapterProfile adapterProfile);

    void inject(FragmentProfile fragmentProfile);

    void inject(PresenterProfile presenterProfile);

    void inject(PresenterAdapterProfile presenterAdapterProfile);
}
