package org.byters.ringoid.view.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.view.presenter.IPresenterActivityMain;

import javax.inject.Inject;

public class ActivityMain extends AppCompatActivity {

    @Inject
    IPresenterActivityMain presenterActivityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApplicationRingoid.getComponent().inject(this);

        presenterActivityMain.onCreateView();
    }
}
