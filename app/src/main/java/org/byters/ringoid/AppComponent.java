package org.byters.ringoid;

import org.byters.ringoid.controller.data.memorycache.CacheScroll;
import org.byters.ringoid.controller.data.memorycache.CacheToken;
import org.byters.ringoid.controller.data.repository.RepositoryRegister;
import org.byters.ringoid.controller.data.repository.RepositoryRegisterConfirm;
import org.byters.ringoid.view.presenter.PresenterActivityMain;
import org.byters.ringoid.view.presenter.PresenterAdapterBlacklistPhones;
import org.byters.ringoid.view.presenter.PresenterAdapterChatMessages;
import org.byters.ringoid.view.presenter.PresenterAdapterExplore;
import org.byters.ringoid.view.presenter.PresenterAdapterExploreImages;
import org.byters.ringoid.view.presenter.PresenterAdapterLikes;
import org.byters.ringoid.view.presenter.PresenterAdapterLikesImages;
import org.byters.ringoid.view.presenter.PresenterAdapterMessages;
import org.byters.ringoid.view.presenter.PresenterAdapterMessagesImages;
import org.byters.ringoid.view.presenter.PresenterAdapterProfile;
import org.byters.ringoid.view.presenter.PresenterBlacklistPhones;
import org.byters.ringoid.view.presenter.PresenterChat;
import org.byters.ringoid.view.presenter.PresenterExplore;
import org.byters.ringoid.view.presenter.PresenterLikes;
import org.byters.ringoid.view.presenter.PresenterMessages;
import org.byters.ringoid.view.presenter.PresenterPagesContainer;
import org.byters.ringoid.view.presenter.PresenterProfile;
import org.byters.ringoid.view.presenter.PresenterRegister;
import org.byters.ringoid.view.presenter.PresenterSettingsPrivacy;
import org.byters.ringoid.view.presenter.PresenterSettingsPrivacyDistance;
import org.byters.ringoid.view.ui.activity.ActivityMain;
import org.byters.ringoid.view.ui.adapter.AdapterBlacklistPhones;
import org.byters.ringoid.view.ui.adapter.AdapterChatMessages;
import org.byters.ringoid.view.ui.adapter.AdapterExplore;
import org.byters.ringoid.view.ui.adapter.AdapterExploreImages;
import org.byters.ringoid.view.ui.adapter.AdapterLikes;
import org.byters.ringoid.view.ui.adapter.AdapterLikesImages;
import org.byters.ringoid.view.ui.adapter.AdapterMessages;
import org.byters.ringoid.view.ui.adapter.AdapterMessagesImages;
import org.byters.ringoid.view.ui.adapter.AdapterProfile;
import org.byters.ringoid.view.ui.adapter.ViewHolderItemExplore;
import org.byters.ringoid.view.ui.adapter.ViewHolderItemExploreImages;
import org.byters.ringoid.view.ui.adapter.ViewHolderItemLikes;
import org.byters.ringoid.view.ui.adapter.ViewHolderItemLikesImages;
import org.byters.ringoid.view.ui.adapter.ViewHolderItemMessage;
import org.byters.ringoid.view.ui.adapter.ViewHolderItemMessagesImages;
import org.byters.ringoid.view.ui.fragment.FragmentBlacklistPhones;
import org.byters.ringoid.view.ui.fragment.FragmentBlacklistPhonesAdd;
import org.byters.ringoid.view.ui.fragment.FragmentChat;
import org.byters.ringoid.view.ui.fragment.FragmentExplore;
import org.byters.ringoid.view.ui.fragment.FragmentLikes;
import org.byters.ringoid.view.ui.fragment.FragmentLogin;
import org.byters.ringoid.view.ui.fragment.FragmentMessages;
import org.byters.ringoid.view.ui.fragment.FragmentPages;
import org.byters.ringoid.view.ui.fragment.FragmentProfile;
import org.byters.ringoid.view.ui.fragment.FragmentSettings;
import org.byters.ringoid.view.ui.fragment.FragmentSettingsPrivacy;
import org.byters.ringoid.view.ui.fragment.FragmentSettingsPrivacyDistance;
import org.byters.ringoid.view.ui.fragment.FragmentWelcome;

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

    void inject(FragmentSettings fragmentSettings);

    void inject(FragmentBlacklistPhones fragmentBlacklistPhones);

    void inject(AdapterBlacklistPhones adapterBlacklistPhones);

    void inject(PresenterBlacklistPhones presenterBlacklistPhones);

    void inject(PresenterAdapterBlacklistPhones presenterAdapterBlacklistPhones);

    void inject(AdapterProfile adapterProfile);

    void inject(FragmentProfile fragmentProfile);

    void inject(PresenterProfile presenterProfile);

    void inject(PresenterAdapterProfile presenterAdapterProfile);

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

    void inject(AdapterChatMessages adapterChatMessages);

    void inject(PresenterAdapterChatMessages presenterAdapterChatMessages);

    void inject(FragmentSettingsPrivacy fragmentSettingsPrivacy);

    void inject(PresenterSettingsPrivacy presenterSettingsPrivacy);

    void inject(FragmentSettingsPrivacyDistance item);

    void inject(PresenterSettingsPrivacyDistance item);

    void inject(FragmentBlacklistPhonesAdd fragmentBlacklistPhonesAdd);

    void inject(FragmentWelcome fragmentWelcome);
}
