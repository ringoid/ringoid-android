/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;

public interface IPresenterActivityMain {
    void onCreateView(Context context, FragmentManager supportFragmentManager, int flContentRoot);

    boolean onBackPressed();

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
