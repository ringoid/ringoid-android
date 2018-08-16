/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterBlacklistPhones;
import com.ringoid.view.ui.dialog.DialogPhoneValid;
import com.ringoid.view.ui.dialog.callback.IDialogPhoneValidListener;
import com.ringoid.view.ui.view.ViewPhoneInput;
import com.ringoid.view.ui.view.callback.IViewPhotoInputListener;

import javax.inject.Inject;

public class FragmentBlacklistPhonesAdd extends FragmentBase implements View.OnClickListener {

    @Inject
    IPresenterBlacklistPhones presenterBlacklistPhones;

    private EditText tvPhone;
    private ViewPhoneInput vpiBlacklist;

    private IDialogPhoneValidListener listenerDialogPhoneValid;
    private DialogPhoneValid dialogPhoneValid;
    private EditText etPhoneCode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);

        listenerDialogPhoneValid = new ListenerDialogPhoneValid();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blacklist_add, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tvPhone = view.findViewById(R.id.etTelNum);
        etPhoneCode = view.findViewById(R.id.etTelCode);
        vpiBlacklist = view.findViewById(R.id.vpiBlacklist);
        vpiBlacklist.setListener(new ListenerViewPhoneInput());

        view.findViewById(R.id.tvBlacklistAdd).setOnClickListener(this);
        view.findViewById(R.id.ivBack).setOnClickListener(this);

        TextView tvSubtitle = view.findViewById(R.id.tvSubtitle);
        tvSubtitle.setText(R.string.settings_privacy_phone_blacklist_add_subtitle);
    }

    @Override
    public void onStart() {
        super.onStart();
        showKeyboard(tvPhone);
    }

    @Override
    public boolean onBackPressed() {
        hideKeyboard();
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvBlacklistAdd) {
            if (!vpiBlacklist.isValid()) {
                if (tvPhone.getText().length() > 0 && etPhoneCode.getText().length() > 0)
                    showDialogPhoneValid(etPhoneCode.getText().toString(), tvPhone.getText().toString());
                return;
            }

            confirmPhoneAdd();
        }

        if (v.getId() == R.id.ivBack && getActivity() != null)
            getActivity().onBackPressed();
    }

    private void confirmPhoneAdd() {
        presenterBlacklistPhones.onClickBlacklistAdd(tvPhone.getText().toString());
        tvPhone.setText("");
        getActivity().onBackPressed();
    }

    private void showDialogPhoneValid(String code, String phone) {
        if (dialogPhoneValid != null) dialogPhoneValid.cancel();
        dialogPhoneValid = new DialogPhoneValid(getContext(), code, phone, listenerDialogPhoneValid);
        dialogPhoneValid.show();
    }

    private class ListenerDialogPhoneValid implements IDialogPhoneValidListener {
        @Override
        public void onConfirm() {
            confirmPhoneAdd();
        }
    }

    private class ListenerViewPhoneInput implements IViewPhotoInputListener {
        @Override
        public void onDialogClose() {
            showKeyboard(tvPhone);
        }
    }
}
