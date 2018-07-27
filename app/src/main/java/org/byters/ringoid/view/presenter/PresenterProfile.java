package org.byters.ringoid.view.presenter;

import org.byters.ringoid.ApplicationRingoid;
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

}
