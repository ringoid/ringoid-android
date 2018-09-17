package com.ringoid.view.ui.fragment;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.INavigator;
import com.ringoid.view.presenter.IPresenterDataProtection;
import com.ringoid.view.presenter.callback.IPresenterDataProtectionListener;
import com.ringoid.view.ui.dialog.DialogAccountDelete;

import javax.inject.Inject;

public class FragmentDataProtection extends FragmentBase
        implements View.OnClickListener {

    @Inject
    IPresenterDataProtection presenterDataProtection;
    @Inject
    INavigator navigator;
    private TextView tvCustomerId;
    private IPresenterDataProtectionListener listenerPresenterDataProtection;
    private DialogAccountDelete dialogAccountDelete;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
        presenterDataProtection.setListener(listenerPresenterDataProtection = new ListenerPresenter());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data_protection, container, false);
        initViews(view);
        presenterDataProtection.onCreateView();
        return view;
    }

    private void initViews(View view) {
        tvCustomerId = view.findViewById(R.id.tvCustomerID);

        view.findViewById(R.id.ivBack).setOnClickListener(this);
        view.findViewById(R.id.llCustomerID).setOnClickListener(this);
        view.findViewById(R.id.tvPrivacy).setOnClickListener(this);
        view.findViewById(R.id.tvSettingsTerms).setOnClickListener(this);
        view.findViewById(R.id.tvSettingsLicenses).setOnClickListener(this);
        view.findViewById(R.id.tvMailOfficer).setOnClickListener(this);

        TextView tvSubtitle = view.findViewById(R.id.tvSubtitle);
        tvSubtitle.setText(R.string.data_protection_subtitle);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.ivBack)
            getActivity().onBackPressed();

        if (v.getId() == R.id.llCustomerID)
            presenterDataProtection.onClickCustomerId();


        if (v.getId() == R.id.tvPrivacy)
            navigator.navigateWebView(getString(R.string.url_privacy), getString(R.string.subtitle_privacy));

        if (v.getId() == R.id.tvMailOfficer)
            presenterDataProtection.onClickMailOffices(getContext());

        if (v.getId() == R.id.tvSettingsTerms)
            navigator.navigateWebView(getContext().getString(R.string.url_terms), getContext().getString(R.string.subtitle_terms));


        if (v.getId() == R.id.tvSettingsLicenses)
            navigator.navigateWebView(getContext().getString(R.string.url_licenses), getContext().getString(R.string.subtitle_licenses));
    }

    private void showDialogAccountDelete() {
        if (dialogAccountDelete != null)
            dialogAccountDelete.cancel();
        dialogAccountDelete = new DialogAccountDelete(getContext());
        dialogAccountDelete.show();
    }


    private class ListenerPresenter implements IPresenterDataProtectionListener {
        @Override
        public void setCustomerId(String id) {
            tvCustomerId.setText(id);
        }
    }

}
