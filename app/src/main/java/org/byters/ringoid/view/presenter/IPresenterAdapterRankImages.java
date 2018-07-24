package org.byters.ringoid.view.presenter;

import android.content.Context;

public interface IPresenterAdapterRankImages {
    int getItemsNum(int position);

    String getUrl(int position, int imagePosition);

    int getItemHeight(Context context, int position, int adapterPosition);
}
