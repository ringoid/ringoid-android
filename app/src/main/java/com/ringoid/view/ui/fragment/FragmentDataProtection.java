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
import com.ringoid.view.ui.dialog.DialogEraseData;
import com.ringoid.view.ui.dialog.DialogWithdraw;
import com.ringoid.view.ui.dialog.callback.IDialogWithdrawListener;

import javax.inject.Inject;

public class FragmentDataProtection extends FragmentBase
        implements View.OnClickListener {

    @Inject
    IPresenterDataProtection presenterDataProtection;
    @Inject
    INavigator navigator;
    private TextView tvCustomerId;
    private IPresenterDataProtectionListener listenerPresenterDataProtection;
    private DialogEraseData dialogEraseData;
    private DialogWithdraw dialogWithdraw;
    private DialogAccountDelete dialogAccountDelete;
    private IDialogWithdrawListener listenerDialogWithdraw;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
        presenterDataProtection.setListener(listenerPresenterDataProtection = new ListenerPresenter());
        listenerDialogWithdraw = new ListenerDialogWithdraw();
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

        view.findViewById(R.id.llCustomerID).setOnClickListener(this);
        view.findViewById(R.id.tvEraseData).setOnClickListener(this);
        view.findViewById(R.id.tvDownloadData).setOnClickListener(this);
        view.findViewById(R.id.tvFAQ).setOnClickListener(this);
        view.findViewById(R.id.tvPrivacy).setOnClickListener(this);
        view.findViewById(R.id.tvMailOfficer).setOnClickListener(this);
        view.findViewById(R.id.tvWithdraw).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.llCustomerID)
            presenterDataProtection.onClickCustomerId();
        if (v.getId() == R.id.tvEraseData)
            showDialogEraseData();

        if (v.getId() == R.id.tvDownloadData) {
            //todo implement
        }
        if (v.getId() == R.id.tvFAQ)
            navigator.navigateSettingsPrivacy(false);

        if (v.getId() == R.id.tvPrivacy)
            navigator.navigateWebView(getString(R.string.url_privacy), getString(R.string.subtitle_privacy));

        if (v.getId() == R.id.tvMailOfficer)
            navigator.navigateEmailProtectionOfficer(getContext());

        if (v.getId() == R.id.tvWithdraw)
            showDialogWithdraw();

    }

    private void showDialogWithdraw() {
        if (dialogWithdraw != null) dialogWithdraw.cancel();
        dialogWithdraw = new DialogWithdraw(getContext(),listenerDialogWithdraw);
        dialogWithdraw.show();
    }

    private void showDialogEraseData() {
        if (dialogEraseData != null) dialogEraseData.cancel();
        dialogEraseData = new DialogEraseData(getContext());
        dialogEraseData.show();
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

    private class ListenerDialogWithdraw implements IDialogWithdrawListener {
        @Override
        public void onConfirm() {
            showDialogAccountDelete();
        }
    }

}
