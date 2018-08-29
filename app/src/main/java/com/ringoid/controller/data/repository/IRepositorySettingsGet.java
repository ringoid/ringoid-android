package com.ringoid.controller.data.repository;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.controller.data.repository.callback.IRepositoryListenerBase;

public interface IRepositorySettingsGet {
    void request();

    void setListener(IRepositoryListenerBase listenerRepositoryBase);
}
