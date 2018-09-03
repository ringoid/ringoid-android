/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterBlacklistPhones;
import com.ringoid.view.presenter.callback.IPresenterBlacklistPhonesListener;
import com.ringoid.view.ui.view.ViewPhoneInput;
import com.ringoid.view.ui.view.callback.IViewPhotoInputListener;

import javax.inject.Inject;

public class FragmentBlacklistPhonesAdd extends FragmentBase implements View.OnClickListener {

    @Inject
    IPresenterBlacklistPhones presenterBlacklistPhones;

    private EditText tvPhone;
    private ViewPhoneInput vpiBlacklist;

    private EditText etPhoneCode;
    private IPresenterBlacklistPhonesListener listenerPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
        presenterBlacklistPhones.setListener(listenerPresenter = new ListenerPresenterBlacklistPhones());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blacklist_add, container, false);
        initView(view);
        presenterBlacklistPhones.onViewCreated();
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
            if (TextUtils.isEmpty(tvPhone.getText()) || TextUtils.isEmpty(etPhoneCode.getText()))
                return;

            confirmPhoneAdd();
        }

        if (v.getId() == R.id.ivBack && getActivity() != null)
            getActivity().onBackPressed();
    }

    private void confirmPhoneAdd() {
        presenterBlacklistPhones.onClickBlacklistAdd(etPhoneCode.getText().toString(), tvPhone.getText().toString());
        tvPhone.setText("");
        getActivity().onBackPressed();
    }

    private class ListenerViewPhoneInput implements IViewPhotoInputListener {
        @Override
        public void onDialogClose() {
            showKeyboard(tvPhone);
        }
    }

    private class ListenerPresenterBlacklistPhones implements IPresenterBlacklistPhonesListener {
        @Override
        public void setPhoneData(int code) {
            if (code == 0)
                vpiBlacklist.setCountryLocal();
            else
                vpiBlacklist.setCountryCode(code);
        }
    }
}
