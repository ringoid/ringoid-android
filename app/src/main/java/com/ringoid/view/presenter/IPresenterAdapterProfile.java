/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.content.Context;

import com.ringoid.view.presenter.callback.IPresenterAdapterProfileListener;

public interface IPresenterAdapterProfile {
    int getItemsNum();

    String getUrl(int pos);

    int getLikesNum(int position);

    boolean onClickItem(Context context, int position);

    void onClickHiddenModeOK(boolean isShow);

    void onClickHiddenModePrivacy(boolean isShow);

    void onCLickLikes();

    void setListener(IPresenterAdapterProfileListener listener);

    String getImageId(int position);

    boolean isImageLast();

    void onImageRemove(String imageId);

    boolean isPhotoLocal(int position);

    boolean isPhotoUploading(int position);
}
