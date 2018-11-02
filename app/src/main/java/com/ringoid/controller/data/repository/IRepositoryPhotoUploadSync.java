package com.ringoid.controller.data.repository;
/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/

public interface IRepositoryPhotoUploadSync {
    void request();

    void cancel(String originId);
}
