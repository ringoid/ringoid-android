package org.byters.ringoid.view;

import android.support.v4.app.FragmentManager;

public interface INavigatorPages {
    void set(FragmentManager childFragmentManager, int viewId);

    void navigateRank();

    void navigateLikes();

    void navigateProfile();

    void navigateMessages();

    void navigateExplore();
}
