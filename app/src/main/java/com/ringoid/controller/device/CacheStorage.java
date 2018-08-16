/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.controller.device;

import android.content.Context;

import com.ringoid.controller.data.FileEnum;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;

public class CacheStorage implements ICacheStorage {

    private WeakReference<Context> refContext;

    public CacheStorage(Context context) {
        this.refContext = new WeakReference<>(context);
    }

    @Override
    public <T> T readObject(FileEnum fileEnum, Class<T> tClass) {
        if (fileEnum.getClassname() != tClass)
            throw new ClassCastException("can't get this enum item type value. Check type in FileEnum");
        return readObjectFromFile(fileEnum.getFilename(), tClass);
    }

    @Override
    public void writeData(FileEnum fileEnum, Object data) {
        writeObjectToFile(data, fileEnum);
    }

    private synchronized void writeObjectToFile(Object data, FileEnum fileEnum) {
        if (refContext == null || refContext.get() == null) return;

        if (!fileEnum.getClassname().isInstance(data)) return;

        File file = new File(refContext.get().getFilesDir(), fileEnum.getFilename());

        ObjectOutputStream objectOutputStream = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(data);
        } catch (IOException e) {
        }

        if (objectOutputStream == null) return;
        try {
            objectOutputStream.close();
        } catch (IOException e) {
        }
    }

    private synchronized <T> T readObjectFromFile(String filename, Class<T> class_) {

        if (refContext == null || refContext.get() == null) return null;

        File file = new File(refContext.get().getFilesDir(), filename);
        if (!file.exists()) return null;

        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            return null;
        }

        ObjectInputStream objectStream;
        try {
            objectStream = new ObjectInputStream(fileInputStream);
        } catch (IOException e) {
            return null;
        }

        Object object;
        try {
            object = objectStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            file.delete();
            return null;
        }

        try {
            objectStream.close();
        } catch (IOException e) {
        }

        if (!class_.isInstance(object)) {
            file.delete();
            return null;
        }


        return (T) object;
    }
}
