package org.byters.ringoid.view.presenter;

import android.content.Context;

public interface IPresenterAdapterRank {
    int getItemsNum();

    String getRank(int position);

    int getItemHeight(Context context, int adapterPosition, int itemPos);

    int getItemsNum(int position);
}
