/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterActivityMain;
import com.ringoid.view.presenter.callback.IPresenterActivityMainListener;

import javax.inject.Inject;

public class ActivityMain extends AppCompatActivity {

    @Inject
    IPresenterActivityMain presenterActivityMain;
    private IPresenterActivityMainListener listenerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
        presenterActivityMain.setListener(listenerPresenter = new ListenerPresenter());
        presenterActivityMain.onCreate();

        setContentView(R.layout.activity_main);

        presenterActivityMain.onCreateView(this, findViewById(R.id.flContentRoot), getSupportFragmentManager(), R.id.flContentRoot);
    }

    @Override
    public void onBackPressed() {
        if (!presenterActivityMain.onBackPressed())
            super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenterActivityMain.onActivityResult(requestCode, resultCode, data);
    }

    private class ListenerPresenter implements IPresenterActivityMainListener {
        @Override
        public void setTheme(int style) {
            ActivityMain.this.setTheme(style);
        }
    }
}
