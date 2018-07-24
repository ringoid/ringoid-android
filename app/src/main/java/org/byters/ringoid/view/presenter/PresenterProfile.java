package org.byters.ringoid.view.presenter;

import android.content.Context;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.controller.data.memorycache.ICacheProfile;

import javax.inject.Inject;

public class PresenterProfile implements IPresenterProfile {

    @Inject
    ICacheProfile cacheProfile;

    public PresenterProfile() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public int getItemsNum() {
        return cacheProfile.getItemsNum();
    }

    @Override
    public int getItemHeight(Context context, int position) {
        float ratio = cacheProfile.getItemRatio(position);
        return (int) (ratio * context.getResources().getDisplayMetrics().widthPixels + context.getResources().getDimension(R.dimen.profile_footer));
    }
}
