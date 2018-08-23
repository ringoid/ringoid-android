/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.device;

import com.ringoid.controller.data.FileEnum;

public interface ICacheStorage {

    <T> T readObject(FileEnum fileEnum, Class<T> tClass);

    void writeData(FileEnum fileEnum, Object data);

    void removeData(FileEnum user);
}
