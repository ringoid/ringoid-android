package org.byters.ringoid.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.view.presenter.IPresenterRegister;
import org.byters.ringoid.view.presenter.callback.IPresenterRegisterListener;

import javax.inject.Inject;

public class FragmentLogin extends FragmentBase
        implements View.OnClickListener {

    @Inject
    IPresenterRegister presenterRegister;
    private ViewSwitcher vsLogin;
    private TextView etPhone, etPhoneCheck;
    private CheckBox cbAge, cbTerms;
    private ListenerPresenter listenerPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
        presenterRegister.setListener(listenerPresenter = new ListenerPresenter());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        initViews(view);
        return view;
    }

    private void initViews(View view) {
        vsLogin = view.findViewById(R.id.vsLogin);
        etPhone = view.findViewById(R.id.etPhone);
        etPhoneCheck = view.findViewById(R.id.etPhoneCheck);
        cbAge = view.findViewById(R.id.cbAge);
        cbTerms = view.findViewById(R.id.cbTerms);

        view.findViewById(R.id.tvRegister).setOnClickListener(this);
        view.findViewById(R.id.tvRegisterConfirm).setOnClickListener(this);
        view.findViewById(R.id.ivSexFemale).setOnClickListener(this);
        view.findViewById(R.id.ivSexMale).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvRegister) {
            presenterRegister.onClickRegister(etPhone.getText().toString(), cbAge.isChecked(), cbTerms.isChecked());
        }

        if (view.getId() == R.id.tvRegisterConfirm) {
            presenterRegister.onClickRegisterConfirm(etPhoneCheck.getText().toString());
        }
        if (view.getId() == R.id.ivSexFemale)
            presenterRegister.onClickFemale();

        if (view.getId() == R.id.ivSexMale)
            presenterRegister.onClickMale();
    }

    private class ListenerPresenter implements IPresenterRegisterListener {


        @Override
        public void onError(int stringId) {
            Toast.makeText(getContext(), stringId, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void navigateConfirm() {
            vsLogin.getNextView();
        }
    }
}
