package org.byters.ringoid;

import org.byters.ringoid.controller.data.memorycache.CacheScroll;
import org.byters.ringoid.controller.data.memorycache.CacheToken;
import org.byters.ringoid.controller.data.repository.RepositoryCountryList;
import org.byters.ringoid.controller.data.repository.RepositoryRegister;
import org.byters.ringoid.controller.data.repository.RepositoryRegisterConfirm;
import org.byters.ringoid.controller.data.repository.RepositoryRegisterReferralConfirm;
import org.byters.ringoid.controller.data.repository.RepositoryRegisterReferralDescription;
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
import org.byters.ringoid.view.presenter.PresenterChat;
import org.byters.ringoid.view.presenter.PresenterExplore;
import org.byters.ringoid.view.presenter.PresenterLikes;
import org.byters.ringoid.view.presenter.PresenterMessages;
import org.byters.ringoid.view.presenter.PresenterPagesContainer;
import org.byters.ringoid.view.presenter.PresenterProfile;
import org.byters.ringoid.view.presenter.PresenterRank;
import org.byters.ringoid.view.presenter.PresenterRegister;
import org.byters.ringoid.view.ui.activity.ActivityMain;
import org.byters.ringoid.view.ui.adapter.AdapterBlacklistPhones;
import org.byters.ringoid.view.ui.adapter.AdapterCountryList;
import org.byters.ringoid.view.ui.adapter.AdapterExplore;
import org.byters.ringoid.view.ui.adapter.AdapterExploreImages;
import org.byters.ringoid.view.ui.adapter.AdapterLikes;
import org.byters.ringoid.view.ui.adapter.AdapterLikesImages;
import org.byters.ringoid.view.ui.adapter.AdapterMessages;
import org.byters.ringoid.view.ui.adapter.AdapterMessagesImages;
import org.byters.ringoid.view.ui.adapter.AdapterProfile;
import org.byters.ringoid.view.ui.adapter.AdapterRank;
import org.byters.ringoid.view.ui.adapter.AdapterRankImages;
import org.byters.ringoid.view.ui.adapter.ViewHolderItemExplore;
import org.byters.ringoid.view.ui.adapter.ViewHolderItemExploreImages;
import org.byters.ringoid.view.ui.adapter.ViewHolderItemLikes;
import org.byters.ringoid.view.ui.adapter.ViewHolderItemLikesImages;
import org.byters.ringoid.view.ui.adapter.ViewHolderItemMessage;
import org.byters.ringoid.view.ui.adapter.ViewHolderItemMessagesImages;
import org.byters.ringoid.view.ui.fragment.FragmentBlacklistPhones;
import org.byters.ringoid.view.ui.fragment.FragmentChat;
import org.byters.ringoid.view.ui.fragment.FragmentExplore;
import org.byters.ringoid.view.ui.fragment.FragmentLikes;
import org.byters.ringoid.view.ui.fragment.FragmentLogin;
import org.byters.ringoid.view.ui.fragment.FragmentMessages;
import org.byters.ringoid.view.ui.fragment.FragmentPages;
import org.byters.ringoid.view.ui.fragment.FragmentProfile;
import org.byters.ringoid.view.ui.fragment.FragmentRank;
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

    void inject(PresenterRank presenterRank);

    void inject(FragmentRank fragmentRank);

    void inject(CacheScroll cacheScroll);

    void inject(PresenterExplore presenterExplore);

    void inject(FragmentExplore fragmentExplore);

    void inject(AdapterExplore adapterExplore);

    void inject(ViewHolderItemExplore viewHolderItemExplore);

    void inject(AdapterExploreImages adapterExploreImages);

    void inject(ViewHolderItemExploreImages viewHolderItemExploreImages);

    void inject(PresenterAdapterExplore presenterAdapterExplore);

    void inject(PresenterAdapterExploreImages presenterAdapterExploreImages);

    void inject(FragmentLikes fragmentLikes);

    void inject(PresenterLikes presenterLikes);

    void inject(PresenterAdapterLikes presenterAdapterLikes);

    void inject(ViewHolderItemLikes viewHolderItemLikes);

    void inject(AdapterLikes adapterLikes);

    void inject(PresenterAdapterLikesImages presenterAdapterLikesImages);

    void inject(AdapterLikesImages adapterLikesImages);

    void inject(ViewHolderItemLikesImages viewHolderItemLikesImages);

    void inject(FragmentMessages fragmentMessages);

    void inject(PresenterMessages presenterMessages);

    void inject(AdapterMessages adapterMessages);

    void inject(ViewHolderItemMessage viewHolderItemMessage);

    void inject(ViewHolderItemMessagesImages viewHolderItemMessagesImages);

    void inject(PresenterAdapterMessages presenterAdapterMessages);

    void inject(AdapterMessagesImages adapterMessagesImages);

    void inject(PresenterAdapterMessagesImages presenterAdapterMessagesImages);

    void inject(FragmentChat fragmentChat);

    void inject(PresenterChat presenterChat);
}
