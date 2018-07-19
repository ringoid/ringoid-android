package org.byters.ringoid.controller.data.repository;

import com.google.gson.Gson;

import org.byters.mockserver.MockServer;
import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.controller.data.memorycache.ICacheRegisterReferral;
import org.byters.ringoid.controller.data.network.response.ResponseRegisterReferralDescription;

import javax.inject.Inject;

public class RepositoryRegisterReferralDescription implements IRepositoryRegisterReferralDescription {

    @Inject
    ICacheRegisterReferral cacheRegisterReferral;

    public RepositoryRegisterReferralDescription() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @Override
    public void request() {


        ResponseRegisterReferralDescription resposne =
                new Gson().fromJson(MockServer.requestRegisterReferralDescription(),
                        ResponseRegisterReferralDescription.class);

        cacheRegisterReferral.setData(resposne.getTitle(), resposne.getDescription());

    }
}
