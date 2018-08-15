package org.byters.ringoid.view.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.support.v4.view.ViewCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hbb20.PhoneUtils;
import com.hbb20.PresenterCountry;
import com.hbb20.model.DataCountry;
import com.hbb20.model.Language;
import com.hbb20.view.CountryCodePicker;

import org.byters.ringoid.R;
import org.byters.ringoid.view.ui.view.callback.IViewPhotoInputListener;
import org.byters.ringoid.view.ui.view.utils.ClipboardUtils;

public class ViewPhoneInput extends LinearLayout
        implements View.OnClickListener, View.OnLongClickListener {
    CountryCodePicker ccp;
    EditText etCode;
    private ListenerCountry listenerCountry;
    private EditText etPhone;
    private IViewPhotoInputListener listener;

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
    }

    public boolean isValid() {
        if (TextUtils.isEmpty(etCode.getText()))
            Toast.makeText(getContext(), R.string.message_code_empty, Toast.LENGTH_SHORT).show();
        else if (TextUtils.isEmpty(etPhone.getText()))
            Toast.makeText(getContext(), R.string.message_phone_empty, Toast.LENGTH_SHORT).show();

        return ccp.isValidFullNumber() && !TextUtils.isEmpty(etCode.getText()) && !TextUtils.isEmpty(etPhone.getText());
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
        Toast.makeText(getContext(), R.string.message_paste, Toast.LENGTH_SHORT).show();
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
}
