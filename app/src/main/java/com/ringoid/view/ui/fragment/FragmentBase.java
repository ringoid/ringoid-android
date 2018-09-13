/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.fragment;

import android.support.v4.app.Fragment;
import android.widget.EditText;

import com.ringoid.view.ui.util.KeyboardUtils;

import javax.inject.Inject;

public class FragmentBase extends Fragment {

    @Inject
    KeyboardUtils keyboardUtils;

    public boolean onBackPressed() {
        return false;
    }

    void showKeyboard(EditText editView) {
        keyboardUtils.keyboardShow(getContext(), editView);
    }

    void hideKeyboard() {
        keyboardUtils.keyboardHide(getContext(), getView());
    }
}
