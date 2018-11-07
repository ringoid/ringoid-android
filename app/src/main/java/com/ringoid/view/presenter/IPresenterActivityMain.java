/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ringoid.view.presenter.callback.IPresenterActivityMainListener;

public interface IPresenterActivityMain {

    boolean onBackPressed();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onCreateView(AppCompatActivity activity, View view, FragmentManager supportFragmentManager, int flContentRoot);

    void setListener(IPresenterActivityMainListener listener);

    void onCreate();
}
