package org.byters.ringoid.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.view.presenter.IPresenterBlacklistPhones;
import org.byters.ringoid.view.ui.dialog.DialogPhoneValid;
import org.byters.ringoid.view.ui.dialog.callback.IDialogPhoneValidListener;
import org.byters.ringoid.view.ui.view.ViewPhoneInput;

import javax.inject.Inject;

public class FragmentBlacklistPhonesAdd extends FragmentBase implements View.OnClickListener {

    @Inject
    IPresenterBlacklistPhones presenterBlacklistPhones;

    private EditText tvPhone;
    private ViewPhoneInput vpiLogin;

    private IDialogPhoneValidListener listenerDialogPhoneValid;
    private DialogPhoneValid dialogPhoneValid;

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
        tvPhone = view.findViewById(R.id.etPhone);
        vpiLogin = view.findViewById(R.id.vpiLogin);

        view.findViewById(R.id.tvBlacklistAdd).setOnClickListener(this);
        view.findViewById(R.id.ivBack).setOnClickListener(this);

        TextView tvSubtitle = view.findViewById(R.id.tvSubtitle);
        tvSubtitle.setText(R.string.settings_privacy_phone_blacklist_add_subtitle);

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
            if (!vpiLogin.isValid()) {
                showDialogPhoneValid(tvPhone.getText().toString());
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

    private void showDialogPhoneValid(String phone) {
        if (dialogPhoneValid != null) dialogPhoneValid.cancel();
        dialogPhoneValid = new DialogPhoneValid(getContext(), phone, listenerDialogPhoneValid);
        dialogPhoneValid.show();
    }

    private class ListenerDialogPhoneValid implements IDialogPhoneValidListener {
        @Override
        public void onConfirm() {
            confirmPhoneAdd();
        }
    }
}
