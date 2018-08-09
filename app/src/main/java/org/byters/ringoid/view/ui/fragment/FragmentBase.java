package org.byters.ringoid.view.ui.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class FragmentBase extends Fragment {
    public boolean onBackPressed() {
        return false;
    }

    void showKeyboard(EditText editView) {
        editView.requestFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editView, InputMethodManager.SHOW_IMPLICIT);
    }

    void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }
}
