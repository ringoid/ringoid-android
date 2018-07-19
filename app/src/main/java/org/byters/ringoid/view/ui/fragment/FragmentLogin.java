package org.byters.ringoid.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.view.presenter.IPresenterRegister;
import org.byters.ringoid.view.presenter.callback.IPresenterRegisterListener;
import org.byters.ringoid.view.ui.dialog.DialogAlertCountryList;
import org.byters.ringoid.view.ui.dialog.DialogDateBirth;
import org.byters.ringoid.view.ui.dialog.callback.IDialogDateCallback;

import javax.inject.Inject;

public class FragmentLogin extends FragmentBase
        implements View.OnClickListener {

    @Inject
    IPresenterRegister presenterRegister;
    private ViewFlipper vfLogin;
    private TextView etPhone, etCodeSMS;
    private CheckBox cbAge, cbTerms;
    private ListenerPresenter listenerPresenter;
    private DialogAlertCountryList dialogAlertCountryList;
    private TextView tvCountry;
    private TextView tvCodeCountry;
    private CheckBox cbTerms2;
    private TextView tvDateBirth;
    private DialogDateBirth dialogDateBirth;
    private TextView tvRegisterReferralTitle;
    private TextView tvRegisterReferralDescription;
    private EditText etLinkReferral;
    private IDialogDateCallback listenerDialogDate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
        presenterRegister.setListener(listenerPresenter = new ListenerPresenter());
        listenerDialogDate = new ListenerDialogDate();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        initViews(view);
        return view;
    }

    private void initViews(View view) {
        vfLogin = view.findViewById(R.id.vfLogin);

        cbTerms = view.findViewById(R.id.cbTerms);

        etPhone = view.findViewById(R.id.etPhone);
        tvCountry = view.findViewById(R.id.tvCountry);
        tvCodeCountry = view.findViewById(R.id.tvCodeCountry);

        etCodeSMS = view.findViewById(R.id.etCodeSMS);

        cbTerms2 = view.findViewById(R.id.cbTerms2);
        cbAge = view.findViewById(R.id.cbAge);
        tvDateBirth = view.findViewById(R.id.tvDateBirth);

        tvRegisterReferralTitle = view.findViewById(R.id.tvLoginReferralTitle);
        tvRegisterReferralDescription = view.findViewById(R.id.tvLoginReferralDescription);
        etLinkReferral = view.findViewById(R.id.etReferral);

        view.findViewById(R.id.tvLoginReferralSkip).setOnClickListener(this);
        view.findViewById(R.id.tvLoginReferralConfirm).setOnClickListener(this);
        view.findViewById(R.id.tvDateBirth).setOnClickListener(this);
        view.findViewById(R.id.tvRegister).setOnClickListener(this);
        view.findViewById(R.id.ivSexFemale).setOnClickListener(this);
        view.findViewById(R.id.ivSexMale).setOnClickListener(this);
        view.findViewById(R.id.tvPhoneReenter).setOnClickListener(this);
        view.findViewById(R.id.tvCodeSMSConfirm).setOnClickListener(this);
        view.findViewById(R.id.tvCountry).setOnClickListener(this);
        view.findViewById(R.id.tvLoginTermsAgreement).setOnClickListener(this);
        view.findViewById(R.id.tvLoginPhoneVerify).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.tvCountry)
            presenterRegister.onClickCountry();

        if (view.getId() == R.id.tvLoginTermsAgreement)
            presenterRegister.onClickLoginTermsAgreement(cbTerms.isChecked());

        if (view.getId() == R.id.tvLoginPhoneVerify)
            presenterRegister.onClickLoginPhoneVerify(etPhone.getText().toString());

        if (view.getId() == R.id.tvCodeSMSConfirm)
            presenterRegister.onClickCodeSMSConfirm(etCodeSMS.getText().toString());

        if (view.getId() == R.id.tvPhoneReenter)
            vfLogin.showPrevious();

        if (view.getId() == R.id.tvRegister)
            presenterRegister.onClickRegister(cbAge.isChecked(), cbTerms2.isChecked());

        if (view.getId() == R.id.ivSexFemale)
            presenterRegister.onClickFemale();

        if (view.getId() == R.id.ivSexMale)
            presenterRegister.onClickMale();

        if (view.getId() == R.id.tvDateBirth)
            presenterRegister.onClickDateBirth();

        if (view.getId() == R.id.tvLoginReferralConfirm) {
            presenterRegister.onClickReferralConfirm(etLinkReferral.getText().toString());
        }

        if (view.getId() == R.id.tvLoginReferralSkip) {
            presenterRegister.onClickReferralSkip();
        }

    }

    private class ListenerPresenter implements IPresenterRegisterListener {

        @Override
        public void onError(int stringId) {
            Toast.makeText(getContext(), stringId, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void navigateNext() {
            vfLogin.showNext();
        }

        @Override
        public void showAlertCountryList() {
            if (dialogAlertCountryList != null) dialogAlertCountryList.cancel();
            dialogAlertCountryList = new DialogAlertCountryList(getContext());
            dialogAlertCountryList.show();
        }

        @Override
        public void dialogCountryListCancel() {
            if (dialogAlertCountryList == null) return;
            dialogAlertCountryList.cancel();
        }

        @Override
        public void showCoutry(String code, String title) {
            tvCountry.setText(TextUtils.isEmpty(title) ? "" : title);
            tvCodeCountry.setText(TextUtils.isEmpty(code) ? "" : code);
        }

        @Override
        public void dialogDateBirthShow(long dateMillis) {
            dialogDateBirth = new DialogDateBirth(getContext());
            dialogDateBirth.setListener(listenerDialogDate);
            dialogDateBirth.show();
        }

        @Override
        public void showReferralData(String title, String description) {
            tvRegisterReferralTitle.setText(TextUtils.isEmpty(title) ? "" : title);
            tvRegisterReferralDescription.setText(TextUtils.isEmpty(description) ? "" : description);

        }
    }

    private class ListenerDialogDate implements IDialogDateCallback {
        @Override
        public void onResult(long timeInMillis) {
            presenterRegister.onDataBirthSet(timeInMillis);
        }
    }
}
