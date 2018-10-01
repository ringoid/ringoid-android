/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Layout;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.model.SEX;
import com.ringoid.view.INavigator;
import com.ringoid.view.presenter.IPresenterRegister;
import com.ringoid.view.presenter.callback.IPresenterRegisterListener;
import com.ringoid.view.ui.view.ViewPhoneInput;
import com.ringoid.view.ui.view.callback.IViewPhotoInputListener;
import com.ringoid.view.ui.view.utils.ClipboardUtils;

import java.util.Calendar;

import javax.inject.Inject;

public class FragmentLogin extends FragmentBase
        implements View.OnClickListener, View.OnLongClickListener {

    private static final String ARG_PROFILE_UPDATE = "arg_profile_update";
    private static final int INDEX_PHONE_INPUT = 0;
    private static final int INDEX_CODE_INPUT = 1;
    private static final int INDEX_PROFILE_UPDATE = 2;

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

        if (getArguments() != null && getArguments().getBoolean(ARG_PROFILE_UPDATE, false)) {
            setPage(INDEX_PROFILE_UPDATE);
        }
    }

    private void setPage(int index) {
        vfLogin.setDisplayedChild(index);
        checkKeyboard();
    }

    @Override
    public boolean onBackPressed() {
        if (!(vfLogin.getCurrentView().getId() == R.id.llLoginInfo)) {
            showPrev();
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.tvLoginPhoneVerify) {
            if (TextUtils.isEmpty(vpiLogin.getPhone()) || TextUtils.isEmpty(etPhoneCode.getText()))
                return;

            setPhone(vpiLogin.isValid());
        }

        if (view.getId() == R.id.tvCodeSMSConfirm) {
            presenterRegister.onClickCodeSMSConfirm(etCodeSMS.getText().toString());
        }

        if (view.getId() == R.id.tvCodeSMSResend) {
            presenterRegister.onClickCodeSMSResend();
        }

        if (view.getId() == R.id.tvCodePhoneError) {
            setPage(INDEX_PHONE_INPUT);
            etPhone.setSelection(etPhone.getText().length());
        }

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

    private void showPrev() {
        vfLogin.showPrevious();
        checkKeyboard();
    }

    private void checkKeyboard() {

        if (vfLogin.getCurrentView().getId() == R.id.llLoginPhone)
            showKeyboard(etPhone);

        if (vfLogin.getCurrentView().getId() == R.id.llLoginSMS)
            showKeyboard(etCodeSMS);

        if (vfLogin.getCurrentView().getId() == R.id.llLoginInfo)
            showKeyboard(etYearBirth);
    }

    private void showNext() {
        vfLogin.showNext();
        checkKeyboard();
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    private void setPhone(boolean isValid) {
        presenterRegister.onClickLoginPhoneVerify(etPhoneCode.getText().toString(), vpiLogin.getPhone(), isValid);
    }

    private void setButtonCodeConfirmState(int length) {
        boolean isValid = length == 4;
        vSMSConfirm.setEnabled(isValid);
        vSMSConfirm.setBackgroundResource(isValid ? R.drawable.rectangle_rounded_green : R.drawable.rectangle_rounded_gray);
    }

    private class ListenerPresenter implements IPresenterRegisterListener {

        @Override
        public void navigateNext() {
            showNext();
        }

        @Override
        public void showDateBirth(int time) {
            String text = time == 0 ? "" : String.valueOf(time);
            if (text.equals(etYearBirth.getText().toString())) return;
            etYearBirth.setText(text);
        }

        @Override
        public void hideKeyboard() {
            FragmentLogin.this.hideKeyboard();
        }

        @Override
        public void clearCodeInput() {
            etCodeSMS.setText("");
        }

        @Override
        public void showPhoneHint(String phone) {
            ((TextView) getView().findViewById(R.id.tvPhoneHint)).setText(phone);
        }

        @Override
        public void setPhoneInputEnabled(boolean isEnabled) {
            vPhoneConfirm.setVisibility(isEnabled ? View.VISIBLE : View.GONE);
            pbPhoneVerify.setVisibility(isEnabled ? View.GONE : View.VISIBLE);
        }

        @Override
        public void setSMSInputEnabled(boolean isEnabled) {
            vSMSConfirm.setVisibility(isEnabled ? View.VISIBLE : View.GONE);
            pbSMSConfirm.setVisibility(isEnabled ? View.GONE : View.VISIBLE);
        }

        @Override
        public void setSMSResendDisabled(int timeSeconds) {
            if (getContext() == null) return;
            tvSMSResend.setEnabled(false);
            tvSMSResend.setText(String.format(getResources().getString(R.string.message_SMS_resend_format), timeSeconds));
            tvSMSResend.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        @Override
        public void setSMSResendEnabled() {
            if (getContext() == null) return;
            tvSMSResend.setEnabled(false);
            tvSMSResend.setText(R.string.message_sms_resend);
            tvSMSResend.setTextColor(getResources().getColor(R.color.blue));
        }

        @Override
        public void showCodeInput() {
            setPage(INDEX_CODE_INPUT);
        }

        @Override
        public void setGenderSelected(SEX sex) {
            tvSexFemale.setBackground(sex == SEX.FEMALE ? getResources().getDrawable(R.drawable.border_rounded_green) : null);
            tvSexMale.setBackground(sex == SEX.MALE ? getResources().getDrawable(R.drawable.border_rounded_green) : null);
        }

        @Override
        public void showPhoneInput() {
            setPage(INDEX_PHONE_INPUT);
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
                            ? android.R.color.black
                            : length == 4
                            ? android.R.color.black
                            : R.color.colorAccent);

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
            etYearBirth.setTextColor(ContextCompat.getColor(getContext(), isValid ? android.R.color.black : R.color.colorAccent));

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
}
