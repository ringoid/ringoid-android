/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import com.ringoid.view.presenter.callback.IPresenterAdapterProfileListener;

public interface IPresenterAdapterProfile {
    int getItemsNum();

    String getUrl(int pos);

    int getLikesNum(int position);

    void onClickLikes();

    void setListener(IPresenterAdapterProfileListener listener);

    String getImageId(int position);

    void onImageRemove(String imageId, String localId, String originId);

    String getUrlThumbnail(int position);

    String getImageLocalId(int position);

    String getImageOriginId(int position);
}
