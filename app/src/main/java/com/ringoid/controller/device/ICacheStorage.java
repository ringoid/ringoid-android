package com.ringoid.controller.device;

import com.ringoid.controller.data.FileEnum;

public interface ICacheStorage {

    <T> T readObject(FileEnum fileEnum, Class<T> tClass);

    void writeData(FileEnum fileEnum, Object data);
}
