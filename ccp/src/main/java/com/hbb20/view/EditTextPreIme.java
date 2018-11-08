package com.hbb20.view;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

import com.hbb20.view.callback.IEditTextPreImeListener;

public class EditTextPreIme extends android.support.v7.widget.AppCompatEditText {

    private IEditTextPreImeListener listener;

    public EditTextPreIme(Context context) {
        super(context);
    }

    public EditTextPreIme(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextPreIme(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (listener.onKeyPreIme(keyCode, event))
            return true;
        return super.onKeyPreIme(keyCode, event);
    }

    public void setListener(IEditTextPreImeListener listener) {
        this.listener = listener;
    }
}
