package org.byters.ringoid.view.ui.view;

import android.content.Context;
import android.util.AttributeSet;

import org.byters.ringoid.R;

public class ViewPhoneInput extends ViewPhoneEditText {
    public ViewPhoneInput(Context context) {
        super(context);
    }

    public ViewPhoneInput(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPhoneInput(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.view_phone_input;
    }

    @Override
    protected void prepareView() {


        super.prepareView();
    }
}
