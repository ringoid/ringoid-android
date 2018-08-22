/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.content.Context;

public interface IPresenterAdapterProfile {
    int getItemsNum();

    String getUrl(int pos);

    int getLikesNum(int position);

    void onClickItem(Context context, int position);

    void onClickAbout(boolean isShow);

    void onClickPrivacy(boolean isShow);

    void onClickLiked(boolean isShow);

    boolean isDialogShowLikes();
}
