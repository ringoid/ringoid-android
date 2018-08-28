package com.ringoid.view;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import android.view.View;

public interface IViewPopup {
    void setView(View view);

    void showToast(int messageRes);

    void showToast(String message);

    void showSnackbar(int messageRes, int messageAction, View.OnClickListener listener);
}
