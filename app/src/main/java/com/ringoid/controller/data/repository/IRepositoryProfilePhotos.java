package com.ringoid.controller.data.repository;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.controller.data.repository.callback.IRepositoryProfilePhotosListener;

public interface IRepositoryProfilePhotos {
    void request();

    void setListener(IRepositoryProfilePhotosListener listener);

    void removeListener();
}
