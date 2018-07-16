package org.byters.ringoid.controller.device;

import org.byters.ringoid.controller.data.FileEnum;

public interface ICacheStorage {

    <T> T readObject(FileEnum fileEnum, Class<T> tClass);

    void writeData(FileEnum fileEnum, Object data);
}
