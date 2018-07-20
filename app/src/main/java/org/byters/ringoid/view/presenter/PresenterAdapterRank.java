package org.byters.ringoid.view.presenter;

public class PresenterAdapterRank implements IPresenterAdapterRank {
    @Override
    public int getItemsNum() {
        return 9;
    }

    @Override
    public String getRank(int position) {
        return String.valueOf(position + 1);
    }
}
