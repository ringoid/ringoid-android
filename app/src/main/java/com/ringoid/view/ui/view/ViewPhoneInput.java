/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbb20.PhoneUtils;
import com.hbb20.PresenterCountry;
import com.hbb20.model.DataCountry;
import com.hbb20.model.Language;
import com.hbb20.view.CountryCodePicker;
import com.ringoid.R;
import com.ringoid.view.ui.view.callback.IViewPhotoInputListener;
import com.ringoid.view.ui.view.utils.ClipboardUtils;

public class ViewPhoneInput extends LinearLayout
        implements View.OnClickListener, View.OnLongClickListener {
    CountryCodePicker ccp;
    EditText etCode;
    private ListenerCountry listenerCountry;
    private EditText etPhone;
    private IViewPhotoInputListener listener;
    private View vContainerCode, vContainerNumber;

    public ViewPhoneInput(Context context) {
        super(context);
        addView(context);
    }

    public ViewPhoneInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        addView(context);
    }

    public ViewPhoneInput(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addView(context);
    }

    private void addView(Context context) {
        inflate(context, R.layout.view_phone_input, this);
        initViews();
    }

    private void initViews() {
        vContainerCode = findViewById(R.id.vContainerCode);
        vContainerNumber = findViewById(R.id.vContainerNumber);
        etCode = findViewById(R.id.etTelCode);
        etPhone = findViewById(R.id.etTelNum);
        etCode.addTextChangedListener(new ListenerCode());
        ccp = findViewById(R.id.ccp);
        ccp.setOnCountryChangeListener(listenerCountry = new ListenerCountry());
        ccp.registerCarrierNumberEditText((EditText) findViewById(R.id.etTelNum));
        ccp.setDialogEventsListener(new DialogCountyEventListener());
        listenerCountry.onCountrySelected();

        findViewById(R.id.tvCodePrefix).setOnClickListener(this);

        findViewById(R.id.ivPaste).setOnClickListener(this);
        findViewById(R.id.ivPaste).setOnLongClickListener(this);


        etPhone.addTextChangedListener(new PhoneInputTextWatcher());

        etCode.setOnEditorActionListener(new ListenedCode());
        etPhone.setOnEditorActionListener(new ListenerPhone());
    }

    public boolean setCountryLocal() {
        String code = ccp.getCountryCodeLocal();
        if (TextUtils.isEmpty(code)) return false;
        etCode.setText(code);
        return true;
    }

    public boolean setCountryCode(int code) {
        etCode.setText(String.valueOf(code));
        return true;
    }

    public boolean isValid() {
        if (TextUtils.isEmpty(etCode.getText()))
            ;//Toast.makeText(getContext(), R.string.message_code_empty, Toast.LENGTH_SHORT).show();
        else if (TextUtils.isEmpty(etPhone.getText()))
            ;//Toast.makeText(getContext(), R.string.message_phone_empty, Toast.LENGTH_SHORT).show();

        return ccp.isValidFullNumber()
                && !TextUtils.isEmpty(etCode.getText())
                && !TextUtils.isEmpty(etPhone.getText())
                && etCode.getText().toString().equals(ccp.getSelectedCountryCode());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivPaste)
            clipboardPaste();
        if (v.getId() == R.id.tvCodePrefix)
            showKeyboard(etCode);
    }

    void showKeyboard(EditText editView) {
        editView.requestFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editView, InputMethodManager.SHOW_IMPLICIT);
    }


    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.ivPaste) {
            showHintPaste();
            return true;
        }
        return false;
    }

    private void showHintPaste() {
        //Toast.makeText(getContext(), R.string.message_paste, Toast.LENGTH_SHORT).show();
    }

    private void clipboardPaste() {
        String result = ClipboardUtils.getString(getContext());
        if (TextUtils.isEmpty(result)) return;

        String phone = PhoneUtils.getPhone(getContext(), result.toString());
        String code = PhoneUtils.getCode(getContext(), result.toString());

        if (!TextUtils.isEmpty(code))
            etCode.setText(code);

        if (!TextUtils.isEmpty(phone))
            etPhone.setText(phone);
    }

    public void setListener(IViewPhotoInputListener listener) {
        this.listener = listener;
    }

    private Drawable getCheckDrawable(boolean empty, boolean valid) {
        return empty
                ? null
                : valid
                ? getContext().getResources().getDrawable(R.drawable.ic_check_green_16dp)
                : getContext().getResources().getDrawable(R.drawable.ic_error_red_16dp);
    }

    private int getTextColor(boolean empty, boolean valid) {
        return getContext().getResources().getColor(empty
                ? android.R.color.white
                : valid
                ? android.R.color.white
                : R.color.colorAccent);
    }

    public String getPhone() {
        return removeLeadZero(etPhone.getText().toString());
    }

    public void setPhone(String phone) {
        etPhone.setText(phone);
    }

    private String removeLeadZero(String input) {
        if (TextUtils.isEmpty(input)) return input;
        while (input.startsWith("0")) {
            input = input.substring(1, input.length());
        }
        return input;
    }

    private void notifyListenerPhoneDone() {
        if (listener == null) return;
        listener.onPhoneDone();

    }

    private class ListenerCode implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            DataCountry ccpCountry = PresenterCountry.getCountryForPhoneCode(getContext(), Language.ENGLISH, s.toString()); //xml stores data in string format, but want to allow only numeric value to country code to user.

            int color = getTextColor(TextUtils.isEmpty(etCode.getText()), ccpCountry != null);
            Drawable drawable = getCheckDrawable(TextUtils.isEmpty(etCode.getText()), ccpCountry != null);

            etCode.setTextColor(color);
            etCode.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            if (ccpCountry != null)
                ccp.setCountryForPhoneCode(s.toString());

            vContainerCode.setBackgroundResource(
                    TextUtils.isEmpty(etCode.getText())
                            ? R.drawable.border_rounded_left_gray
                            : ccpCountry != null
                            ? R.drawable.border_rounded_left_green
                            : R.drawable.border_rounded_left_red);

        }
    }

    private class ListenerCountry implements CountryCodePicker.OnCountryChangeListener {
        @Override
        public void onCountrySelected() {
            if (etCode.getText().toString().equals(ccp.getSelectedCountryCode())) return;

            String code = ccp.getSelectedCountryCode();

            etCode.setText(code);
            etCode.setSelection(code.length());
        }
    }

    private class PhoneInputTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            boolean isValid = ccp.isValidFullNumber();
            int color = getTextColor(TextUtils.isEmpty(etPhone.getText()), isValid);
            Drawable drawable = getCheckDrawable(TextUtils.isEmpty(etPhone.getText()), isValid);

            etPhone.setTextColor(color);
            etPhone.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

            vContainerNumber.setBackgroundResource(TextUtils.isEmpty(etPhone.getText())
                    ? R.drawable.border_rounded_right_gray
                    : isValid
                    ? R.drawable.border_rounded_right_green
                    : R.drawable.border_rounded_right_red);
        }
    }

    private class DialogCountyEventListener implements CountryCodePicker.DialogEventsListener {
        @Override
        public void onCcpDialogOpen(Dialog dialog) {

        }

        @Override
        public void onCcpDialogDismiss(DialogInterface dialogInterface) {
            if (listener != null) listener.onDialogClose();
        }

        @Override
        public void onCcpDialogCancel(DialogInterface dialogInterface) {
            if (listener != null) listener.onDialogClose();
        }
    }

    private class ListenedCode implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                etPhone.requestFocus();
                return true;
            }
            return false;
        }
    }

    private class ListenerPhone implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                notifyListenerPhoneDone();
                return true;
            }
            return false;
        }
    }
}
