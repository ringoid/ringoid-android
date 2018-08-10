package org.byters.ringoid.view.ui.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.hbb20.CountryCodePicker;

import org.byters.ringoid.R;

public class ViewPhoneInput extends LinearLayout {
    CountryCodePicker ccp;
    EditText etCode;
    private ListenerCountry listenerCountry;

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
        etCode.addTextChangedListener(new ListenerCode());
        ccp = findViewById(R.id.ccp);
        ccp.setOnCountryChangeListener(listenerCountry = new ListenerCountry());
        ccp.registerCarrierNumberEditText((EditText)findViewById(R.id.etPhone));
        listenerCountry.onCountrySelected();
    }

    public boolean isValid() {
        return ccp.isValidFullNumber();
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
}
