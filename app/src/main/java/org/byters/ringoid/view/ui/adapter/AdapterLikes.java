package org.byters.ringoid.view.ui.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.view.presenter.IPresenterAdapterLikes;

import javax.inject.Inject;

public class AdapterLikes extends AdapterBase {

    @Inject
    IPresenterAdapterLikes presenterAdapterLikes;

    public AdapterLikes() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @NonNull
    @Override
    public ViewHolderBase onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderItemLikes(parent);
    }

    @Override
    public int getItemCount() {
        return presenterAdapterLikes.getItemsNum();
    }
}
