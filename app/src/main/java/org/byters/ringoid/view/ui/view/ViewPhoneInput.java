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
import android.widget.Toast;

import com.hbb20.PhoneUtils;
import com.hbb20.PresenterCountry;
import com.hbb20.model.DataCountry;
import com.hbb20.model.Language;
import com.hbb20.view.CountryCodePicker;

import org.byters.ringoid.R;

public class ViewPhoneInput extends LinearLayout
        implements View.OnClickListener, View.OnLongClickListener {
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
        findViewById(R.id.ivPaste).setOnLongClickListener(this);


        etPhone.addTextChangedListener(new PhoneInputTextWatcher());
    }

    public boolean isValid() {
        if (TextUtils.isEmpty(etCode.getText()))
            Toast.makeText(getContext(), R.string.message_code_empty, Toast.LENGTH_SHORT).show();
        else if (TextUtils.isEmpty(etPhone.getText()))
            Toast.makeText(getContext(), R.string.message_phone_empty, Toast.LENGTH_SHORT).show();

        return ccp.isValidFullNumber();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivPaste)
            clipboardPaste();
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
        Toast.makeText(getContext(), R.string.message_paste, Toast.LENGTH_SHORT).show();
    }

    private void clipboardPaste() {
        ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = clipboardManager.getPrimaryClip();

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < clipData.getItemCount(); ++i) {
            result.append(clipData.getItemAt(i).getText());
        }
        if (TextUtils.isEmpty(result)) return;

        String phone = PhoneUtils.getPhone(getContext(), result.toString());
        String code = PhoneUtils.getCode(getContext(), result.toString());

        if (!TextUtils.isEmpty(code))
            etCode.setText(code);

        if (!TextUtils.isEmpty(phone))
            etPhone.setText(phone);
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
