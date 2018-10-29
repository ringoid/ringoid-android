/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.fragment;

import android.support.v4.app.Fragment;
import android.widget.EditText;

import com.ringoid.view.INavigator;
import com.ringoid.view.PAGE_ENUM;
import com.ringoid.view.ui.util.KeyboardUtils;

import javax.inject.Inject;

public class FragmentBase extends Fragment {

    @Inject
    KeyboardUtils keyboardUtils;

    @Inject
    INavigator navigator;

    public boolean onBackPressed() {
        return false;
    }

    void showKeyboard(EditText editView) {
        if (getContext() == null) return;
        keyboardUtils.keyboardShow(getContext(), editView);
    }

    void hideKeyboard() {
        if (getContext() == null) return;
        keyboardUtils.keyboardHide(getContext(), getView());
    }

    @Override
    public void onResume() {
        super.onResume();
        navigator.onResume(this);
    }

    public PAGE_ENUM getPage() {
        return PAGE_ENUM.UNKNOWN;
    }
}
