package org.byters.ringoid.view.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.EditText;

public abstract class ViewPhoneEditText extends ViewPhoneField {

    public ViewPhoneEditText(Context context) {
        this(context, null);
    }

    public ViewPhoneEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPhoneEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void updateLayoutAttributes() {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }
}
