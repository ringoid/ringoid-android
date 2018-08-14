package org.byters.ringoid.view.ui.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.view.ViewCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.hbb20.PresenterCountry;
import com.hbb20.model.DataCountry;
import com.hbb20.model.Language;
import com.hbb20.view.CountryCodePicker;

import org.byters.ringoid.R;

public class ViewPhoneInput extends LinearLayout implements View.OnClickListener {
    CountryCodePicker ccp;
    EditText etCode;
    private ListenerCountry listenerCountry;
    private EditText etPhone;

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
        etCode = findViewById(R.id.etPhoneCode);
        etPhone = findViewById(R.id.etPhone);
        etCode.addTextChangedListener(new ListenerCode());
        ccp = findViewById(R.id.ccp);
        ccp.setOnCountryChangeListener(listenerCountry = new ListenerCountry());
        ccp.registerCarrierNumberEditText((EditText) findViewById(R.id.etPhone));
        listenerCountry.onCountrySelected();


        findViewById(R.id.ivPaste).setOnClickListener(this);


        etPhone.addTextChangedListener(new PhoneInputTextWatcher());
    }

    public boolean isValid() {
        return ccp.isValidFullNumber();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivPaste)
            clipboardPaste();
    }

    private void clipboardPaste() {
        ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = clipboardManager.getPrimaryClip();

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < clipData.getItemCount(); ++i) {
            result.append(clipData.getItemAt(i).getText());
        }
        if (TextUtils.isEmpty(result)) return;

        String phone = getClipboardPhone(result.toString());
        String code = getClipboardCode(result.toString());

        if (!TextUtils.isEmpty(code))
            etCode.setText(code);

        if (!TextUtils.isEmpty(phone))
            etPhone.setText(phone);
    }

    private String getClipboardPhone(String data) {
        Phonenumber.PhoneNumber number = getNumber(data);
        return number == null ? data : String.valueOf(number.getNationalNumber());
    }

    private String getClipboardCode(String data) {
        Phonenumber.PhoneNumber number = getNumber(data);
        return number == null ? null : String.valueOf(number.getCountryCode());
    }

    private Phonenumber.PhoneNumber getNumber(String data) {
        Phonenumber.PhoneNumber number;
        try {
            number = PhoneNumberUtil.getInstance().parse(data, null);
        } catch (NumberParseException e) {
            return null;
        }
        return number;
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
            if (ccpCountry == null) return;

            ccp.setCountryForPhoneCode(s.toString());
        }
    }

    private class ListenerCountry implements CountryCodePicker.OnCountryChangeListener {
        @Override
        public void onCountrySelected() {
            if (etCode.getText().toString().equals(ccp.getSelectedCountryCode())) return;
            etCode.setText(ccp.getSelectedCountryCode());
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
            ColorStateList color = ColorStateList.valueOf(getContext().getResources().getColor(
                    TextUtils.isEmpty(etPhone.getText()) ? android.R.color.white
                            : ccp.isValidFullNumber()
                            ? R.color.colorAccentGreen
                            : R.color.colorAccent));
            ViewCompat.setBackgroundTintList(etPhone, color);

        }
    }
}
