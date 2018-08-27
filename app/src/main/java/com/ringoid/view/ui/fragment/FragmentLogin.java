/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Layout;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.model.SEX;
import com.ringoid.view.INavigator;
import com.ringoid.view.presenter.IPresenterRegister;
import com.ringoid.view.presenter.callback.IPresenterRegisterListener;
import com.ringoid.view.ui.dialog.DialogDateBirth;
import com.ringoid.view.ui.dialog.DialogPhoneValid;
import com.ringoid.view.ui.dialog.callback.IDialogDateCallback;
import com.ringoid.view.ui.dialog.callback.IDialogPhoneValidListener;
import com.ringoid.view.ui.view.ViewPhoneInput;
import com.ringoid.view.ui.view.callback.IViewPhotoInputListener;
import com.ringoid.view.ui.view.utils.ClipboardUtils;

import java.util.Date;

import javax.inject.Inject;

public class FragmentLogin extends FragmentBase
        implements View.OnClickListener, View.OnLongClickListener {

    private static final String ARG_PROFILE_UPDATE = "arg_profile_update";
    private static final int INDEX_PROFILE_UPDATE = 3;

    @Inject
    IPresenterRegister presenterRegister;

    @Inject
    INavigator navigator;

    private ViewFlipper vfLogin;
    private EditText etPhone, etCodeSMS;
    private CheckBox cbAge, cbTerms;
    private ListenerPresenter listenerPresenter;

    private DialogDateBirth dialogDateBirth;
    private IDialogDateCallback listenerDialogDate;
    private ViewPhoneInput vpiLogin;
    private DialogPhoneValid dialogPhoneValid;
    private IDialogPhoneValidListener listenerDialogPhoneValid;
    private EditText etPhoneCode;
    private View vContainerSMSCode;

    public static FragmentLogin getInstanceProfileUpdate() {
        FragmentLogin fragment = new FragmentLogin();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PROFILE_UPDATE, true);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
        presenterRegister.setListener(listenerPresenter = new ListenerPresenter());
        listenerDialogDate = new ListenerDialogDate();
        listenerDialogPhoneValid = new ListenerDialogPhoneValid();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        initViews(view);
        presenterRegister.onCreateView();
        return view;
    }

    private void initViews(View view) {
        vContainerSMSCode = view.findViewById(R.id.vContainerSMSCode);
        vfLogin = view.findViewById(R.id.vfLogin);
        cbTerms = view.findViewById(R.id.cbTerms);
        etPhone = view.findViewById(R.id.etTelNum);
        etPhoneCode = view.findViewById(R.id.etTelCode);
        etCodeSMS = view.findViewById(R.id.etCodeSMS);
        cbAge = view.findViewById(R.id.cbAge);
        vpiLogin = view.findViewById(R.id.vpiLogin);

        cbTerms.setMovementMethod(new LinkMovementMethodInternal());

        view.findViewById(R.id.tvRegister).setOnClickListener(this);
        view.findViewById(R.id.tvSexFemale).setOnClickListener(this);
        view.findViewById(R.id.tvSexMale).setOnClickListener(this);
        view.findViewById(R.id.tvCodeSMSConfirm).setOnClickListener(this);
        view.findViewById(R.id.tvLoginTermsAgreement).setOnClickListener(this);
        view.findViewById(R.id.tvLoginPhoneVerify).setOnClickListener(this);
        view.findViewById(R.id.tvDateBirth).setOnClickListener(this);
        view.findViewById(R.id.ivBack).setOnClickListener(this);
        view.findViewById(R.id.ivPasteSMS).setOnClickListener(this);

        view.findViewById(R.id.ivPasteSMS).setOnLongClickListener(this);

        vpiLogin.setListener(new ListenerViewPhoneInput());

        etCodeSMS.addTextChangedListener(new SMSTextChangedListener());

        cbTerms.setOnCheckedChangeListener(new ListenerCheckedChangeTerms());
        cbAge.setOnCheckedChangeListener(new ListenerCheckedChangeAge());

        if (getArguments() != null && getArguments().getBoolean(ARG_PROFILE_UPDATE, false))
            vfLogin.setDisplayedChild(INDEX_PROFILE_UPDATE);
    }

    @Override
    public boolean onBackPressed() {
        if (vfLogin.getCurrentView().getId() != R.id.llLoginTerms) {
            showPrev();
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.tvLoginTermsAgreement) {
            presenterRegister.onClickLoginTermsAgreement(cbTerms.isChecked(), cbAge.isChecked());
        }

        if (view.getId() == R.id.tvLoginPhoneVerify) {
            if (!vpiLogin.isValid()) {
                if (etPhone.getText().length() > 0 && etPhoneCode.getText().length() > 0)
                    showDialogPhoneValid(etPhoneCode.getText().toString(), etPhone.getText().toString());
                return;
            }

            setPhone(true);
        }

        if (view.getId() == R.id.tvCodeSMSConfirm) {
            presenterRegister.onClickCodeSMSConfirm(etCodeSMS.getText().toString());
        }

        if (view.getId() == R.id.ivBack)
            showPrev();

        if (view.getId() == R.id.tvRegister)
            presenterRegister.onClickRegister();

        if (view.getId() == R.id.tvSexFemale)
            presenterRegister.onClickFemale();

        if (view.getId() == R.id.tvSexMale)
            presenterRegister.onClickMale();

        if (view.getId() == R.id.tvDateBirth)
            showDialogCalendar();

        if (view.getId() == R.id.ivPasteSMS) {
            pasteSMSFromCLipboard();
        }

    }

    private void pasteSMSFromCLipboard() {
        String result = ClipboardUtils.getString(getContext());

        if (TextUtils.isEmpty(result))
            return;

        result = result.replaceAll("[^0-9]", "");
        result = result.substring(0, Math.min(4, result.length()));

        if (TextUtils.isEmpty(result)) return;

        etCodeSMS.setText(result);
        etCodeSMS.setSelection(etCodeSMS.getText().length());
    }

    private void showDialogPhoneValid(String code, String phone) {
        if (dialogPhoneValid != null) dialogPhoneValid.cancel();
        dialogPhoneValid = new DialogPhoneValid(getContext(), code, phone, listenerDialogPhoneValid);
        dialogPhoneValid.show();
    }

    private void showPrev() {
        vfLogin.showPrevious();
        checkKeyboard();
    }

    private void checkKeyboard() {
        if (vfLogin.getCurrentView().getId() == R.id.llLoginTerms)
            hideKeyboard();

        if (vfLogin.getCurrentView().getId() == R.id.llLoginPhone)
            showKeyboard(etPhone);

        if (vfLogin.getCurrentView().getId() == R.id.llLoginSMS)
            showKeyboard(etCodeSMS);

        if (vfLogin.getCurrentView().getId() == R.id.llLoginInfo)
            hideKeyboard();
    }

    private void showNext() {
        vfLogin.showNext();
        checkKeyboard();
    }

    private void showDialogCalendar() {
        if (dialogDateBirth != null) dialogDateBirth.cancel();
        dialogDateBirth = new DialogDateBirth(getContext());
        dialogDateBirth.setListener(listenerDialogDate);
        dialogDateBirth.show();
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.ivPasteSMS) {
            Toast.makeText(getContext(), R.string.message_paste, Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void setPhone(boolean isValid) {
        presenterRegister.onClickLoginPhoneVerify(etPhoneCode.getText().toString(), etPhone.getText().toString(), isValid);
    }

    private class ListenerPresenter implements IPresenterRegisterListener {

        @Override
        public void onError(int stringId) {
            Toast.makeText(getContext(), stringId, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void navigateNext() {
            showNext();
        }

        @Override
        public void showDateBirth(long time) {
            ((TextView) getView().findViewById(R.id.tvDateBirth)).setText(DateFormat.getDateFormat(getContext()).format(new Date(time)));
        }

        @Override
        public void setGenderSelected(SEX sex) {
            getView().findViewById(R.id.tvSexFemale).setBackground(sex == SEX.FEMALE ? getResources().getDrawable(R.drawable.border_rounded_pink) : null);
            getView().findViewById(R.id.tvSexMale).setBackground(sex == SEX.MALE ? getResources().getDrawable(R.drawable.border_rounded_blue) : null);
        }

        @Override
        public void showToast(int stringRes) {
            Toast.makeText(getContext(), stringRes, Toast.LENGTH_SHORT).show();
        }

    }

    private class ListenerDialogDate implements IDialogDateCallback {
        @Override
        public void onResult(long timeInMillis) {
            presenterRegister.onDataBirthSet(timeInMillis);
        }
    }

    private class LinkMovementMethodInternal extends LinkMovementMethod {
        public boolean onTouchEvent(TextView widget, android.text.Spannable buffer, android.view.MotionEvent event) {
            int action = event.getAction();

            if (action == MotionEvent.ACTION_UP) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                x -= widget.getTotalPaddingLeft();
                y -= widget.getTotalPaddingTop();

                x += widget.getScrollX();
                y += widget.getScrollY();

                Layout layout = widget.getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);

                URLSpan[] link = buffer.getSpans(off, off, URLSpan.class);
                if (link.length != 0) {
                    String url = link[0].getURL();
                    navigator.navigateWebView(url);
                    return true;
                }
            }
            return super.onTouchEvent(widget, buffer, event);
        }

    }

    private class ListenerDialogPhoneValid implements IDialogPhoneValidListener {
        @Override
        public void onConfirm() {
            setPhone(false);
        }
    }

    private class ListenerViewPhoneInput implements IViewPhotoInputListener {
        @Override
        public void onDialogClose() {
            showKeyboard(etPhone);
        }
    }

    private class SMSTextChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            setDrawableState(s.length());

            if (s.length() <= 4) return;
            s.replace(0, s.length(), s, 0, 4);
        }

        private void setDrawableState(int length) {
            Context context = getContext();
            if (context == null) return;

            Resources resources = context.getResources();
            if (resources == null) return;

            boolean empty = TextUtils.isEmpty(etCodeSMS.getText());

            int drawableRes = empty
                    ? R.drawable.border_rounded_gray
                    : length == 4
                    ? R.drawable.border_rounded_green
                    : R.drawable.border_rounded_red;

            Drawable drawable = empty
                    ? null
                    : length == 4
                    ? resources.getDrawable(R.drawable.ic_check_green_16dp)
                    : resources.getDrawable(R.drawable.ic_error_red_16dp);

            int color = resources.getColor(
                    TextUtils.isEmpty(etCodeSMS.getText())
                            ? android.R.color.black
                            : length == 4
                            ? android.R.color.black
                            : R.color.colorAccent);

            vContainerSMSCode.setBackgroundResource(drawableRes);
            etCodeSMS.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            etCodeSMS.setTextColor(color);
        }
    }

    private class ListenerCheckedChangeTerms implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            presenterRegister.setCheckedTerms(isChecked);
        }
    }

    private class ListenerCheckedChangeAge implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            presenterRegister.setCheckedAge(isChecked);
        }
    }
}
