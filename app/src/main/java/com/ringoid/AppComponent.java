/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid;

import com.ringoid.controller.data.memorycache.CacheScroll;
import com.ringoid.controller.data.memorycache.CacheToken;
import com.ringoid.controller.data.memorycache.CacheUser;
import com.ringoid.controller.data.repository.RepositoryRegister;
import com.ringoid.controller.data.repository.RepositoryRegisterConfirm;
import com.ringoid.view.ViewPopup;
import com.ringoid.view.presenter.PresenterActivityMain;
import com.ringoid.view.presenter.PresenterAdapterBlacklistPhones;
import com.ringoid.view.presenter.PresenterAdapterChatMessages;
import com.ringoid.view.presenter.PresenterAdapterExplore;
import com.ringoid.view.presenter.PresenterAdapterExploreImages;
import com.ringoid.view.presenter.PresenterAdapterLikes;
import com.ringoid.view.presenter.PresenterAdapterLikesImages;
import com.ringoid.view.presenter.PresenterAdapterMessages;
import com.ringoid.view.presenter.PresenterAdapterMessagesImages;
import com.ringoid.view.presenter.PresenterAdapterProfile;
import com.ringoid.view.presenter.PresenterBlacklistPhones;
import com.ringoid.view.presenter.PresenterChat;
import com.ringoid.view.presenter.PresenterExplore;
import com.ringoid.view.presenter.PresenterLikes;
import com.ringoid.view.presenter.PresenterMessages;
import com.ringoid.view.presenter.PresenterPagesContainer;
import com.ringoid.view.presenter.PresenterProfile;
import com.ringoid.view.presenter.PresenterRegister;
import com.ringoid.view.presenter.PresenterSettingsPrivacy;
import com.ringoid.view.presenter.PresenterSettingsPrivacyDistance;
import com.ringoid.view.ui.activity.ActivityMain;
import com.ringoid.view.ui.adapter.AdapterBlacklistPhones;
import com.ringoid.view.ui.adapter.AdapterChatMessages;
import com.ringoid.view.ui.adapter.AdapterExplore;
import com.ringoid.view.ui.adapter.AdapterExploreImages;
import com.ringoid.view.ui.adapter.AdapterLikes;
import com.ringoid.view.ui.adapter.AdapterLikesImages;
import com.ringoid.view.ui.adapter.AdapterMessages;
import com.ringoid.view.ui.adapter.AdapterMessagesImages;
import com.ringoid.view.ui.adapter.AdapterProfile;
import com.ringoid.view.ui.adapter.ViewHolderItemExplore;
import com.ringoid.view.ui.adapter.ViewHolderItemExploreImages;
import com.ringoid.view.ui.adapter.ViewHolderItemLikes;
import com.ringoid.view.ui.adapter.ViewHolderItemLikesImages;
import com.ringoid.view.ui.adapter.ViewHolderItemMessage;
import com.ringoid.view.ui.adapter.ViewHolderItemMessagesImages;
import com.ringoid.view.ui.fragment.FragmentBase;
import com.ringoid.view.ui.fragment.FragmentBlacklistPhones;
import com.ringoid.view.ui.fragment.FragmentBlacklistPhonesAdd;
import com.ringoid.view.ui.fragment.FragmentChat;
import com.ringoid.view.ui.fragment.FragmentExplore;
import com.ringoid.view.ui.fragment.FragmentLikes;
import com.ringoid.view.ui.fragment.FragmentLogin;
import com.ringoid.view.ui.fragment.FragmentMessages;
import com.ringoid.view.ui.fragment.FragmentPages;
import com.ringoid.view.ui.fragment.FragmentProfile;
import com.ringoid.view.ui.fragment.FragmentSettings;
import com.ringoid.view.ui.fragment.FragmentSettingsPrivacy;
import com.ringoid.view.ui.fragment.FragmentSettingsPrivacyDistance;
import com.ringoid.view.ui.fragment.FragmentWelcome;
import com.ringoid.view.ui.view.ViewToolbar;

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

    void inject(CacheUser cacheUser);

    void inject(FragmentBase fragmentBase);

    void inject(ViewToolbar viewToolbar);

    void inject(ViewPopup viewPopup);
}
