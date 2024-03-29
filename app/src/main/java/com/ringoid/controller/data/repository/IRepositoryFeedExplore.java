package com.ringoid.controller.data.repository;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.controller.data.repository.callback.IRepositoryNewPhotosListener;

public interface IRepositoryFeedExplore {
    void request();

    void setListener(IRepositoryNewPhotosListener listener);

    void removeListener();
}
