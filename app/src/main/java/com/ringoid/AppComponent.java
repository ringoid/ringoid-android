/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid;

import com.ringoid.controller.data.memorycache.CacheBlacklist;
import com.ringoid.controller.data.memorycache.CacheChatMessages;
import com.ringoid.controller.data.memorycache.CacheExplore;
import com.ringoid.controller.data.memorycache.CacheInterfaceState;
import com.ringoid.controller.data.memorycache.CacheLikes;
import com.ringoid.controller.data.memorycache.CacheMessages;
import com.ringoid.controller.data.memorycache.CacheProfile;
import com.ringoid.controller.data.memorycache.CacheScroll;
import com.ringoid.controller.data.memorycache.CacheToken;
import com.ringoid.controller.data.memorycache.CacheTutorial;
import com.ringoid.controller.data.memorycache.CacheUser;
import com.ringoid.controller.data.network.interceptor.InterceptorRetry;
import com.ringoid.controller.data.repository.RepositoryFeedExplore;
import com.ringoid.controller.data.repository.RepositoryFeedLikes;
import com.ringoid.controller.data.repository.RepositoryFeedMessages;
import com.ringoid.controller.data.repository.RepositoryPhotoUpload;
import com.ringoid.controller.data.repository.RepositoryPhotoUploadUri;
import com.ringoid.controller.data.repository.RepositoryProfileImageRemove;
import com.ringoid.controller.data.repository.RepositoryRegisterCodeConfirm;
import com.ringoid.controller.data.repository.RepositoryRegisterLogout;
import com.ringoid.controller.data.repository.RepositoryRegisterPhone;
import com.ringoid.controller.data.repository.RepositoryRegisterUserDetails;
import com.ringoid.controller.data.repository.RepositorySettingsGet;
import com.ringoid.controller.data.repository.RepositorySettingsSave;
import com.ringoid.view.NavigatorPages;
import com.ringoid.view.ViewDialogs;
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
import com.ringoid.view.presenter.PresenterDataProtection;
import com.ringoid.view.presenter.PresenterExplore;
import com.ringoid.view.presenter.PresenterFeedPage;
import com.ringoid.view.presenter.PresenterItemImageLikeable;
import com.ringoid.view.presenter.PresenterLikes;
import com.ringoid.view.presenter.PresenterMessages;
import com.ringoid.view.presenter.PresenterPagesContainer;
import com.ringoid.view.presenter.PresenterPhotoCrop;
import com.ringoid.view.presenter.PresenterProfile;
import com.ringoid.view.presenter.PresenterRegister;
import com.ringoid.view.presenter.PresenterSettings;
import com.ringoid.view.presenter.PresenterSettingsPrivacyDistance;
import com.ringoid.view.presenter.PresenterSettingsPush;
import com.ringoid.view.presenter.util.HelperConnection;
import com.ringoid.view.presenter.util.LogoutHelper;
import com.ringoid.view.presenter.util.SettingsHelper;
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
import com.ringoid.view.ui.adapter.ViewHolderItemImagesLikeable;
import com.ringoid.view.ui.adapter.ViewHolderItemLikes;
import com.ringoid.view.ui.adapter.ViewHolderItemLikesImages;
import com.ringoid.view.ui.adapter.ViewHolderItemMessage;
import com.ringoid.view.ui.adapter.ViewHolderItemMessagesImages;
import com.ringoid.view.ui.dialog.DialogChatCompose;
import com.ringoid.view.ui.fragment.FragmentBlacklistPhones;
import com.ringoid.view.ui.fragment.FragmentBlacklistPhonesAdd;
import com.ringoid.view.ui.fragment.FragmentChat;
import com.ringoid.view.ui.fragment.FragmentDataProtection;
import com.ringoid.view.ui.fragment.FragmentExplore;
import com.ringoid.view.ui.fragment.FragmentFeedPage;
import com.ringoid.view.ui.fragment.FragmentLikes;
import com.ringoid.view.ui.fragment.FragmentLogin;
import com.ringoid.view.ui.fragment.FragmentMessages;
import com.ringoid.view.ui.fragment.FragmentPages;
import com.ringoid.view.ui.fragment.FragmentPhotoCrop;
import com.ringoid.view.ui.fragment.FragmentProfile;
import com.ringoid.view.ui.fragment.FragmentSettings;
import com.ringoid.view.ui.fragment.FragmentSettingsPrivacyDistance;
import com.ringoid.view.ui.fragment.FragmentSettingsPush;
import com.ringoid.view.ui.fragment.FragmentWebView;
import com.ringoid.view.ui.util.HelperAnimation;
import com.ringoid.view.ui.util.ScreenHelper;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {

    void inject(ActivityMain param);

    void inject(FragmentFeedPage param);

    void inject(PresenterFeedPage param);

    void inject(RepositoryFeedExplore param);

    void inject(CacheExplore param);

    void inject(CacheMessages param);

    void inject(RepositoryFeedMessages param);

    void inject(CacheLikes param);

    void inject(RepositoryFeedLikes param);

    void inject(NavigatorPages param);

    void inject(HelperAnimation param);

    void inject(HelperConnection param);

    void inject(CacheInterfaceState param);

    void inject(CacheChatMessages param);

    void inject(DialogChatCompose param);

    void inject(CacheProfile param);

    void inject(RepositoryProfileImageRemove param);

    void inject(ScreenHelper param);

    void inject(FragmentPhotoCrop param);

    void inject(RepositoryPhotoUploadUri repositoryPhotoUploadUri);

    void inject(RepositoryPhotoUpload repositoryPhotoUpload);

    void inject(PresenterPhotoCrop param);

    void inject(InterceptorRetry param);

    void inject(PresenterActivityMain presenterActivityMain);

    void inject(PresenterDataProtection param);

    void inject(CacheToken cacheToken);

    void inject(FragmentLogin fragmentLogin);

    void inject(PresenterRegister presenterRegister);

    void inject(RepositoryRegisterPhone repositoryRegister);

    void inject(RepositoryRegisterCodeConfirm repositoryRegisterConfirm);

    void inject(RepositoryProfilePhotos param);

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

    void inject(FragmentSettingsPrivacyDistance item);

    void inject(PresenterSettingsPrivacyDistance item);

    void inject(FragmentBlacklistPhonesAdd fragmentBlacklistPhonesAdd);

    void inject(CacheUser cacheUser);

    void inject(FragmentDataProtection param);

    void inject(FragmentWebView param);

    void inject(FragmentSettingsPush param);

    void inject(ViewPopup viewPopup);

    void inject(ViewHolderItemImagesLikeable viewHolderItemImagesLikeable);

    void inject(PresenterItemImageLikeable presenterItemImageLikeable);

    void inject(ViewDialogs viewDialogs);

    void inject(CacheTutorial cacheTutorial);

    void inject(RepositoryRegisterUserDetails repositoryRegisterUserDetails);

    void inject(CacheBlacklist cacheBlacklist);

    void inject(PresenterSettingsPush presenterSettingsPush);

    void inject(RepositorySettingsSave repositorySettingsSave);

    void inject(SettingsHelper settingsHelper);

    void inject(RepositorySettingsGet repositorySettingsGet);

    void inject(LogoutHelper logoutHelper);

    void inject(RepositoryRegisterLogout repositoryRegisterLogout);

    void inject(PresenterSettings presenterSettings);
}
