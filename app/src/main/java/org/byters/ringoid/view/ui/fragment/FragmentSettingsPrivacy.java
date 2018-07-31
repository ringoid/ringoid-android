package org.byters.ringoid.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.view.INavigator;
import org.byters.ringoid.view.presenter.IPresenterSettingsPrivacy;
import org.byters.ringoid.view.presenter.callback.IPresenterSettingsPrivacyListener;

import javax.inject.Inject;

public class FragmentSettingsPrivacy extends FragmentBase
        implements View.OnClickListener {

    private static final String ARG_SHOW_PHOTO = "arg_show_photo";

    @Inject
    INavigator navigator;

    @Inject
    IPresenterSettingsPrivacy presenterSettingsPrivacy;

    private TextView tvPrivacyPhotosAll, tvPrivacyPhotosLikes, tvPrivacyPhotosNoone;
    private TextView tvPrivacyDistance;
    private IPresenterSettingsPrivacyListener listenerPresenter;

    public static Fragment getInstance(boolean showPhoto) {
        Bundle args = new Bundle();
        args.putBoolean(ARG_SHOW_PHOTO, showPhoto);
        Fragment fragment = new FragmentSettingsPrivacy();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
        presenterSettingsPrivacy.setListener(listenerPresenter = new ListenerPresenter());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_privacy, container, false);

        initViews(view);
        return view;
    }

    private void initViews(View view) {
        view.findViewById(R.id.ivBack).setOnClickListener(this);
        ((TextView) view.findViewById(R.id.tvSubtitle)).setText(R.string.settings_privacy_subtitle);

        view.findViewById(R.id.tvPrivacyPhotos).setOnClickListener(this);
        view.findViewById(R.id.tvPrivacyPhone).setOnClickListener(this);
        view.findViewById(R.id.tvPrivacyLocation).setOnClickListener(this);
        view.findViewById(R.id.tvPrivacyAge).setOnClickListener(this);
        view.findViewById(R.id.tvPrivacyActivity).setOnClickListener(this);
        view.findViewById(R.id.tvPrivacyPolicy).setOnClickListener(this);

        tvPrivacyPhotosAll = view.findViewById(R.id.tvPrivacyPhotosAll);
        tvPrivacyPhotosLikes = view.findViewById(R.id.tvPrivacyPhotosLikes);
        tvPrivacyPhotosNoone = view.findViewById(R.id.tvPrivacyPhotosNoone);
        tvPrivacyPhotosAll.setOnClickListener(this);
        tvPrivacyPhotosLikes.setOnClickListener(this);
        tvPrivacyPhotosNoone.setOnClickListener(this);

        view.findViewById(R.id.tvPrivacyPhoneBlacklist).setOnClickListener(this);

        view.findViewById(R.id.llPrivacyDistance).setOnClickListener(this);
        tvPrivacyDistance = view.findViewById(R.id.tvPrivacyDistance);

        view.findViewById(R.id.llPrivacyPhotos).setVisibility(getArguments() != null && getArguments().getBoolean(ARG_SHOW_PHOTO) ? View.VISIBLE : View.GONE);

        view.findViewById(R.id.tvSettingsPrivacyPolicyDataStorage).setOnClickListener(this);
        view.findViewById(R.id.tvSettingsPrivacyPolicyData).setOnClickListener(this);
        view.findViewById(R.id.tvSettingsPrivacyPolicyNotice).setOnClickListener(this);

        presenterSettingsPrivacy.onCreateView();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.ivBack && getActivity() != null)
            getActivity().onBackPressed();

        visibilityChangeCheck(v, R.id.tvPrivacyPhotos, R.id.llPrivacyPhotos);
        visibilityChangeCheck(v, R.id.tvPrivacyPhone, R.id.llPrivacyPhone);
        visibilityChangeCheck(v, R.id.tvPrivacyLocation, R.id.llPrivacyLocation);
        visibilityChangeCheck(v, R.id.tvPrivacyAge, R.id.llPrivacyAge);
        visibilityChangeCheck(v, R.id.tvPrivacyActivity, R.id.llPrivacyActivity);
        visibilityChangeCheck(v, R.id.tvPrivacyPolicy, R.id.llPrivacyPolicy);

        if (v.getId() == R.id.tvPrivacyPhotosAll)
            presenterSettingsPrivacy.onClickPrivacyPhotosAll();

        if (v.getId() == R.id.tvPrivacyPhotosLikes)
            presenterSettingsPrivacy.onClickPrivacyPhotosLikes();

        if (v.getId() == R.id.tvPrivacyPhotosNoone)
            presenterSettingsPrivacy.onClickPrivacyPhotosNoone();

        if (v.getId() == R.id.tvPrivacyPhoneBlacklist)
            navigator.navigateBlacklistPhones();

        if (v.getId() == R.id.llPrivacyDistance)
            navigator.navigateSettingsPrivacyDistance();

        if (v.getId() == R.id.tvSettingsPrivacyPolicyDataStorage)
            navigator.navigateWebView("http://ringoid.com/data-summary.html");

        if (v.getId() == R.id.tvSettingsPrivacyPolicyData)
            navigator.navigateWebView("http://ringoid.com/data-policy.html");

        if (v.getId() == R.id.tvSettingsPrivacyPolicyNotice)
            navigator.navigateWebView("http://ringoid.com/privacy-notice.html");

    }

    private void visibilityChangeCheck(View v, int viewId, int viewContainerId) {
        if (v.getId() != viewId) return;
        View vContainer = getView().findViewById(viewContainerId);
        vContainer.setVisibility(vContainer.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        TextView view = getView().findViewById(viewId);
        view.setCompoundDrawablesWithIntrinsicBounds(null, null, getContext().getResources().getDrawable(vContainer.getVisibility() == View.GONE
                ? R.drawable.ic_keyboard_arrow_down_gray_24dp
                : R.drawable.ic_keyboard_arrow_up_black_24dp), null);
    }

    private class ListenerPresenter implements IPresenterSettingsPrivacyListener {
        @Override
        public void setPrivacyPhotos(int type) {
            tvPrivacyPhotosAll.setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.ic_privacy_all_gray_24dp), null, type == 0 ? getContext().getResources().getDrawable(R.drawable.ic_check_gray_24dp) : null, null);
            tvPrivacyPhotosLikes.setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.ic_privacy_likes_gray_24dp), null, type == 1 ? getContext().getResources().getDrawable(R.drawable.ic_check_gray_24dp) : null, null);
            tvPrivacyPhotosNoone.setCompoundDrawablesWithIntrinsicBounds(getContext().getResources().getDrawable(R.drawable.ic_privacy_noone_gray_24dp), null, type == 2 ? getContext().getResources().getDrawable(R.drawable.ic_check_gray_24dp) : null, null);
        }

        @Override
        public void setPrivacyDistance(int distanceId) {
            tvPrivacyDistance.setText(distanceId);
        }
    }
}
