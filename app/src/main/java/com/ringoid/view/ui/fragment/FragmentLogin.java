/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Layout;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.model.SEX;
import com.ringoid.view.INavigator;
import com.ringoid.view.PAGE_ENUM;
import com.ringoid.view.presenter.IPresenterRegister;
import com.ringoid.view.presenter.PresenterRegister;
import com.ringoid.view.presenter.callback.IPresenterRegisterListener;
import com.ringoid.view.ui.view.ViewPhoneInput;
import com.ringoid.view.ui.view.callback.IViewPhotoInputListener;
import com.ringoid.view.ui.view.utils.ClipboardUtils;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

public class FragmentLogin extends FragmentBase
        implements View.OnClickListener, View.OnLongClickListener {

    @Inject
    IPresenterRegister presenterRegister;

    @Inject
    INavigator navigator;

    private ViewFlipper vfLogin;
    private EditText etPhone, etCodeSMS;
    private ListenerPresenter listenerPresenter;

    private ViewPhoneInput vpiLogin;
    private EditText etPhoneCode;
    private View vContainerSMSCode;
    private EditText etYearBirth;
    private View tvSexFemale, tvSexMale;
    private View pbPhoneVerify, pbSMSConfirm;
    private View vPhoneConfirm, vSMSConfirm;
    private TextView tvSMSResend;
    private TextView tvPhoneHint;

    public PAGE_ENUM getPage() {
        return PAGE_ENUM.LOGIN;
    }

    public static Fragment getInstanceCodeConfirm() {
        FragmentLogin fragment = new FragmentLogin();
        Bundle args = new Bundle();
        args.putInt(PresenterRegister.ARG_PAGE, PresenterRegister.INDEX_CODE_INPUT);
        fragment.setArguments(args);
        return fragment;
    }


    public static FragmentLogin getInstanceProfileUpdate() {
        FragmentLogin fragment = new FragmentLogin();
        Bundle args = new Bundle();
        args.putInt(PresenterRegister.ARG_PAGE, PresenterRegister.INDEX_PROFILE_UPDATE);
        fragment.setArguments(args);
        return fragment;
    }

    public static FragmentLogin getInstancePhoneInput() {
        FragmentLogin fragment = new FragmentLogin();
        Bundle args = new Bundle();
        args.putInt(PresenterRegister.ARG_PAGE, PresenterRegister.INDEX_PHONE_INPUT);
        fragment.setArguments(args);
        return fragment;
    }

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
        presenterRegister.onCreateView(getArguments());
        return view;
    }

    private void initViews(View view) {
        vContainerSMSCode = view.findViewById(R.id.vContainerSMSCode);
        vfLogin = view.findViewById(R.id.vfLogin);
        etPhone = view.findViewById(R.id.etTelNum);
        etPhoneCode = view.findViewById(R.id.etTelCode);
        etCodeSMS = view.findViewById(R.id.etCodeSMS);
        vpiLogin = view.findViewById(R.id.vpiLogin);
        etYearBirth = view.findViewById(R.id.etYearBirth);
        tvSexFemale = view.findViewById(R.id.tvSexFemale);
        tvSexMale = view.findViewById(R.id.tvSexMale);
        pbPhoneVerify = view.findViewById(R.id.pbPhoneVerify);
        pbSMSConfirm = view.findViewById(R.id.pbSMSConfirm);
        vPhoneConfirm = view.findViewById(R.id.tvLoginPhoneVerify);
        vSMSConfirm = view.findViewById(R.id.tvCodeSMSConfirm);
        tvSMSResend = view.findViewById(R.id.tvCodeSMSResend);
        tvPhoneHint = view.findViewById(R.id.tvPhoneHint);

        ((TextView) view.findViewById(R.id.tvTerms)).setMovementMethod(new LinkMovementMethodInternal());

        view.findViewById(R.id.tvRegister).setOnClickListener(this);
        view.findViewById(R.id.tvSexFemale).setOnClickListener(this);
        view.findViewById(R.id.tvSexMale).setOnClickListener(this);
        view.findViewById(R.id.tvCodeSMSConfirm).setOnClickListener(this);
        view.findViewById(R.id.tvLoginPhoneVerify).setOnClickListener(this);
        view.findViewById(R.id.ivPasteSMS).setOnClickListener(this);
        view.findViewById(R.id.tvCodeSMSResend).setOnClickListener(this);
        view.findViewById(R.id.tvCodePhoneError).setOnClickListener(this);

        view.findViewById(R.id.ivPasteSMS).setOnLongClickListener(this);

        vpiLogin.setListener(new ListenerViewPhoneInput());
        vpiLogin.setCountryLocal();

        etCodeSMS.addTextChangedListener(new SMSTextChangedListener());
        etYearBirth.addTextChangedListener(new DateTextChangedListener());

        etCodeSMS.setOnEditorActionListener(new ListenerSMSCode());
        etYearBirth.setOnEditorActionListener(new ListenerYearBirth());
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.tvLoginPhoneVerify) {
            onClickPhoneConfirm();
        }

        if (view.getId() == R.id.tvCodeSMSConfirm) {
            presenterRegister.onClickCodeSMSConfirm(etCodeSMS.getText().toString());
        }

        if (view.getId() == R.id.tvCodeSMSResend) {
            presenterRegister.onClickCodeSMSResend();
        }

        if (view.getId() == R.id.tvCodePhoneError)
            presenterRegister.onClickWrongPhone();

        if (view.getId() == R.id.tvRegister)
            presenterRegister.onClickRegister();

        if (view.getId() == R.id.tvSexFemale)
            presenterRegister.onClickFemale();

        if (view.getId() == R.id.tvSexMale)
            presenterRegister.onClickMale();

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

    private void checkKeyboard() {

        if (vfLogin.getCurrentView().getId() == R.id.llLoginPhone)
            etPhone.post(new RunnableKeyboard(etPhone));

        if (vfLogin.getCurrentView().getId() == R.id.llLoginSMS)
            etCodeSMS.post(new RunnableKeyboard(etCodeSMS));

        if (vfLogin.getCurrentView().getId() == R.id.llLoginInfo)
            etYearBirth.post(new RunnableKeyboard(etYearBirth));
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    private void onClickPhoneConfirm() {
        if (TextUtils.isEmpty(vpiLogin.getPhone()) || TextUtils.isEmpty(etPhoneCode.getText()))
            return;

        presenterRegister.onClickLoginPhoneVerify(etPhoneCode.getText().toString(), vpiLogin.getPhone(), vpiLogin.isValid());
    }

    private void setButtonCodeConfirmState(int length) {
        boolean isValid = length == 4;
        vSMSConfirm.setEnabled(isValid);
        vSMSConfirm.setBackgroundResource(isValid ? R.drawable.rectangle_rounded_green : R.drawable.rectangle_rounded_gray);
    }

    private class ListenerPresenter implements IPresenterRegisterListener {

        @Override
        public void showDateBirth(int time) {
            if (getContext() == null) return;
            String text = time == 0 ? "" : String.valueOf(time);
            if (text.equals(etYearBirth.getText().toString())) return;
            etYearBirth.setText(text);
        }

        @Override
        public void hideKeyboard() {
            if (getContext() == null) return;
            FragmentLogin.this.hideKeyboard();
        }

        @Override
        public void clearCodeInput() {
            if (getContext() == null) return;
            etCodeSMS.setText("");
        }

        @Override
        public void showPhoneHint(String phone) {
            if (getContext() == null) return;
            tvPhoneHint.setText(phone);
        }

        @Override
        public void setPhoneInputEnabled(boolean isEnabled) {
            if (getContext() == null) return;
            vPhoneConfirm.setVisibility(isEnabled ? View.VISIBLE : View.GONE);
            pbPhoneVerify.setVisibility(isEnabled ? View.GONE : View.VISIBLE);
        }

        @Override
        public void setSMSInputEnabled(boolean isEnabled) {
            if (getContext() == null) return;
            vSMSConfirm.setVisibility(isEnabled ? View.VISIBLE : View.GONE);
            pbSMSConfirm.setVisibility(isEnabled ? View.GONE : View.VISIBLE);
        }

        @Override
        public void setSMSResendDisabled(int timeSeconds) {
            if (getContext() == null) return;
            tvSMSResend.setEnabled(false);
            int secInMinute = (int) TimeUnit.MINUTES.toSeconds(1);
            tvSMSResend.setText(String.format(getResources().getString(R.string.message_SMS_resend_format),
                    TimeUnit.SECONDS.toMinutes(timeSeconds),
                    timeSeconds % secInMinute));
            tvSMSResend.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        @Override
        public void setSMSResendEnabled() {
            if (getContext() == null) return;
            tvSMSResend.setEnabled(true);
            tvSMSResend.setText(R.string.message_sms_resend);
            tvSMSResend.setTextColor(getResources().getColor(R.color.blue));
        }

        @Override
        public void setPage(int index) {
            if (getContext() == null) return;
            vfLogin.setDisplayedChild(index);
            checkKeyboard();
        }

        @Override
        public void setPhone(int phoneCode, String phone) {
            if (getContext() == null) return;
            vpiLogin.setPhone(phone);
            vpiLogin.setCountryCode(phoneCode);
        }

        @Override
        public void setPhoneSelectionEnd() {
            if (getContext() == null) return;
            etPhone.setSelection(etPhone.getText().length());
        }

        @Override
        public void setGenderSelected(SEX sex) {
            if (getContext() == null) return;
            tvSexFemale.setBackground(sex == SEX.FEMALE ? getResources().getDrawable(R.drawable.border_rounded_green) : null);
            tvSexMale.setBackground(sex == SEX.MALE ? getResources().getDrawable(R.drawable.border_rounded_green) : null);
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
                    hideKeyboard();
                    navigator.navigateWebView(url, getContext().getString(url.equals(getString(R.string.url_terms)) ? R.string.subtitle_terms : R.string.subtitle_privacy));
                    return true;
                }
            }
            return super.onTouchEvent(widget, buffer, event);
        }

    }

    private class ListenerViewPhoneInput implements IViewPhotoInputListener {
        @Override
        public void onDialogClose() {
            etPhone.post(new RunnableKeyboard(etPhone));
        }

        @Override
        public void onPhoneDone() {
            onClickPhoneConfirm();
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
            setButtonCodeConfirmState(s.length());

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
                            ? android.R.color.white
                            : length == 4
                            ? android.R.color.white
                            : R.color.colorWarning);

            vContainerSMSCode.setBackgroundResource(drawableRes);
            etCodeSMS.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            etCodeSMS.setTextColor(color);
        }
    }

    private class DateTextChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            if (s.length() > 4)
                s.replace(0, s.length(), s, 0, 4);

            int year = getYear(s.toString());
            boolean isValid = isValid(year);

            etYearBirth.setBackgroundResource(isValid ? R.drawable.border_rounded_green : R.drawable.border_rounded_red);
            etYearBirth.setTextColor(ContextCompat.getColor(getContext(), isValid ? android.R.color.white : R.color.colorWarning));

            Drawable drawable = ContextCompat.getDrawable(getContext(), isValid ? R.drawable.ic_check_green_16dp : R.drawable.ic_error_red_16dp);
            etYearBirth.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

            presenterRegister.onDataBirthSet(isValid ? year : 0);
        }

        private int getYear(String s) {
            if (!TextUtils.isDigitsOnly(s) || s.length() != 4)
                return 0;

            int year;
            try {
                year = Integer.valueOf(s);
            } catch (NumberFormatException e) {
                return 0;
            }

            return year;
        }

        private boolean isValid(int year) {
            return year >= 1938 && year <= Calendar.getInstance().get(Calendar.YEAR) - 18;
        }
    }

    private class ListenerSMSCode implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                presenterRegister.onClickCodeSMSConfirm(etCodeSMS.getText().toString());
                return true;
            }
            return false;
        }
    }

    private class ListenerYearBirth implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard();
                return true;
            }
            return false;
        }
    }

    private class RunnableKeyboard implements Runnable {
        private EditText editText;

        public RunnableKeyboard(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void run() {
            showKeyboard(editText);
        }
    }
}
