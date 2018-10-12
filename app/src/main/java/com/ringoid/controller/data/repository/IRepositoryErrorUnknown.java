package com.ringoid.controller.data.repository;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.controller.data.repository.callback.IRepositoryErrorUnknownListener;

public interface IRepositoryErrorUnknown {
    void request(String url);

    void setListener(IRepositoryErrorUnknownListener listener);
}
