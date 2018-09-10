package com.ringoid.controller.data.repository;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

import com.ringoid.controller.data.repository.callback.IRepositoryPhotoUploadListener;

public interface IRepositoryPhotoUpload {
    void request();

    void setListener(IRepositoryPhotoUploadListener listener);
}
