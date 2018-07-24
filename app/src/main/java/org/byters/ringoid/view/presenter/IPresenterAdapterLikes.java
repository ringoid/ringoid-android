package org.byters.ringoid.view.presenter;

import android.content.Context;

public interface IPresenterAdapterLikes {
    int getItemsNum();

    int getItemHeight(Context context, int position);

    int getItemsNum(int position);
}
