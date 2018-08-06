package org.byters.ringoid.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.model.SEX;
import org.byters.ringoid.view.presenter.IPresenterRegister;
import org.byters.ringoid.view.presenter.callback.IPresenterRegisterListener;
import org.byters.ringoid.view.ui.adapter.AdapterSpinnerCodes;
import org.byters.ringoid.view.ui.dialog.DialogDateBirth;
import org.byters.ringoid.view.ui.dialog.callback.IDialogDateCallback;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

public class FragmentLogin extends FragmentBase
        implements View.OnClickListener {

    @Inject
    IPresenterRegister presenterRegister;

    private ViewFlipper vfLogin;
    private TextView etPhone, etCodeSMS;
    private CheckBox cbAge, cbTerms;
    private ListenerPresenter listenerPresenter;

    private DialogDateBirth dialogDateBirth;
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
        etCodeSMS = view.findViewById(R.id.etCodeSMS);
        cbAge = view.findViewById(R.id.cbAge);

        view.findViewById(R.id.tvRegister).setOnClickListener(this);
        view.findViewById(R.id.tvSexFemale).setOnClickListener(this);
        view.findViewById(R.id.tvSexMale).setOnClickListener(this);
        view.findViewById(R.id.tvCodeSMSConfirm).setOnClickListener(this);
        view.findViewById(R.id.tvLoginTermsAgreement).setOnClickListener(this);
        view.findViewById(R.id.tvLoginPhoneVerify).setOnClickListener(this);
        view.findViewById(R.id.ivCalendar).setOnClickListener(this);
        view.findViewById(R.id.ivBack).setOnClickListener(this);

        initSpinner(view);
    }

    private void initSpinner(View view) {
        Spinner spinner = view.findViewById(R.id.spCodes);
        BaseAdapter adapter = new AdapterSpinnerCodes();
        spinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.tvLoginTermsAgreement)
            presenterRegister.onClickLoginTermsAgreement(cbTerms.isChecked(), cbAge.isChecked());

        if (view.getId() == R.id.tvLoginPhoneVerify)
            presenterRegister.onClickLoginPhoneVerify(etPhone.getText().toString());

        if (view.getId() == R.id.tvCodeSMSConfirm)
            presenterRegister.onClickCodeSMSConfirm(etCodeSMS.getText().toString());

        if (view.getId() == R.id.ivBack)
            vfLogin.showPrevious();

        if (view.getId() == R.id.tvRegister)
            presenterRegister.onClickRegister();

        if (view.getId() == R.id.tvSexFemale)
            presenterRegister.onClickFemale();

        if (view.getId() == R.id.tvSexMale)
            presenterRegister.onClickMale();

        if (view.getId() == R.id.ivCalendar)
            showDialogCalendar();
    }

    private void showDialogCalendar() {
        if (dialogDateBirth != null) dialogDateBirth.cancel();
        dialogDateBirth = new DialogDateBirth(getContext());
        dialogDateBirth.setListener(listenerDialogDate);
        dialogDateBirth.show();
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
        public void showDateBirth(long time) {
            ((TextView) getView().findViewById(R.id.etDateBirth)).setText(new SimpleDateFormat("yyyy MM dd").format(new Date(time)));
        }

        @Override
        public void setGenderSelected(SEX sex) {
            getView().findViewById(R.id.tvSexFemale).setBackground(sex == SEX.FEMALE ? getResources().getDrawable(R.drawable.border_rounded_pink) : null);
            getView().findViewById(R.id.tvSexMale).setBackground(sex == SEX.MALE ? getResources().getDrawable(R.drawable.border_rounded_blue) : null);
        }

    }

    private class ListenerDialogDate implements IDialogDateCallback {
        @Override
        public void onResult(long timeInMillis) {
            presenterRegister.onDataBirthSet(timeInMillis);
        }
    }
}
