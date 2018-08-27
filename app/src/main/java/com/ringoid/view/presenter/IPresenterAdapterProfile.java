/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.presenter;

import android.content.Context;

public interface IPresenterAdapterProfile {
    int getItemsNum();

    String getUrl(int pos);

    int getLikesNum(int position);

    boolean onClickItem(Context context, int position);

    void onClickOK(boolean isShow);

    void onClickLiked(boolean isShow);

    boolean isDialogShowLikes();

    void onClickAboutHiddenMode(boolean isShow);

    void onClickHiddenModeOK(boolean isShow);

    void onClickHiddenModePrivacy(boolean isShow);
}
