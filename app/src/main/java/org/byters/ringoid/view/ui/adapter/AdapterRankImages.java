package org.byters.ringoid.view.ui.adapter;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.view.presenter.IPresenterAdapterRankImages;
import org.byters.ringoid.view.ui.dialog.DialogMenuRank;

import javax.inject.Inject;

public class AdapterRankImages extends AdapterBase {

    @Inject
    IPresenterAdapterRankImages presenterAdapterRankImages;

    private int position;

    AdapterRankImages() {
        ApplicationRingoid.getComponent().inject(this);
    }

    @NonNull
    @Override
    public ViewHolderBase onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderItem(parent);
    }

    @Override
    public int getItemCount() {
        return presenterAdapterRankImages.getItemsNum(position);
    }

    public void setPosition(int position) {
        this.position = position;
        notifyDataSetChanged();
    }

    private class ViewHolderItem extends ViewHolderBase implements View.OnClickListener {
        private ImageView ivItem;
        private DialogMenuRank dialogMenuRank;

        ViewHolderItem(ViewGroup parent) {
            super(parent, R.layout.view_image);
            ivItem = itemView.findViewById(R.id.ivContent);
            itemView.findViewById(R.id.tvMenu).setOnClickListener(this);
        }

        @Override
        void setData(int position) {
            String url = presenterAdapterRankImages.getUrl(AdapterRankImages.this.position, position);
            if (TextUtils.isEmpty(url))
                ivItem.setImageDrawable(null);
            else
                Glide.with(itemView.getContext())
                        .load(url)
                        .into(ivItem);
        }

        @Override
        public void onClick(View v) {
            if (dialogMenuRank != null)
                dialogMenuRank.cancel();
            dialogMenuRank = new DialogMenuRank(itemView.getContext());
            dialogMenuRank.show();
        }
    }
}
