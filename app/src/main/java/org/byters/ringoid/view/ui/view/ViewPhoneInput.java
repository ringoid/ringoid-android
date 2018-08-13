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

import com.hbb20.CCPCountry;
import com.hbb20.CountryCodePicker;

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
        android.content.ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = clipboardManager.getPrimaryClip();


        StringBuilder result = new StringBuilder();

        for (int i = 0; i < clipData.getItemCount(); ++i) {
            result.append(clipData.getItemAt(i).getText());
        }
        if (TextUtils.isEmpty(result)) return;

        etPhone.setText(String.format("%s%s", etPhone.getText().toString(), result));
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

            int num;

            try {
                num = Integer.valueOf(s.toString());
            } catch (NumberFormatException e) {
                return;
            }

            CCPCountry ccpCountry = CCPCountry.getCountryForCode(getContext(), CountryCodePicker.Language.ENGLISH, null, s.toString()); //xml stores data in string format, but want to allow only numeric value to country code to user.
            if (ccpCountry == null) return;

            ccp.setCountryForPhoneCode(num);
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
