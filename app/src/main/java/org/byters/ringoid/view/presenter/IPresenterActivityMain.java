package org.byters.ringoid.view.presenter;

import android.content.Intent;
import android.support.v4.app.FragmentManager;

public interface IPresenterActivityMain {
    void onCreateView(FragmentManager supportFragmentManager, int flContentRoot);

    boolean onBackPressed();

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
