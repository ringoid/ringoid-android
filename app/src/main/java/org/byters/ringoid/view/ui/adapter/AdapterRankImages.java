package org.byters.ringoid.view.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.view.presenter.IPresenterAdapterRankImages;
import org.byters.ringoid.view.ui.dialog.DialogMenuRank;
import org.byters.ringoid.view.ui.util.GlideApp;

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
    public void onViewDetachedFromWindow(@NonNull ViewHolderBase holder) {
        super.onViewDetachedFromWindow(holder);

        ((ViewHolderItem) holder).onDetached();
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolderBase holder) {
        super.onViewRecycled(holder);
        android.util.Log.v("some", "recycled");
    }

    @Override
    public int getItemCount() {
        return presenterAdapterRankImages.getItemsNum(position);
    }

    public void setPosition(int position) {
        this.position = position;
        notifyDataSetChanged();
    }

    public void loadImage(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder == null) return;
        ((ViewHolderItem) viewHolder).loadImage();
    }


    class ViewHolderItem extends ViewHolderBase implements View.OnClickListener {
        private View ivLikeAnimated;
        private ImageView ivItem;
        private DialogMenuRank dialogMenuRank;
        private AnimationSet animation;

        ViewHolderItem(ViewGroup parent) {
            super(parent, R.layout.view_image);
            ivItem = itemView.findViewById(R.id.ivContent);
            itemView.findViewById(R.id.tvMenu).setOnClickListener(this);
            itemView.setOnClickListener(this);
            ivLikeAnimated = itemView.findViewById(R.id.ivLikeAnimated);
        }

        @Override
        void setData(int position) {
            if (position == 0)
                loadImage(position);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.tvMenu) {
                if (dialogMenuRank != null)
                    dialogMenuRank.cancel();
                dialogMenuRank = new DialogMenuRank(itemView.getContext());
                dialogMenuRank.show();
            }
        }


        private void loadImage(int pos) {

            String url = presenterAdapterRankImages.getUrl(AdapterRankImages.this.position, pos);
            if (TextUtils.isEmpty(url))
                ivItem.setImageDrawable(null);
            else {
                if (ivItem.getDrawable() == null)
                    GlideApp.with(itemView.getContext())
                            .load(url)
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(ivItem);
            }
        }

        void onDetached() {
            ivItem.setImageDrawable(null);
            GlideApp.get(itemView.getContext()).clearMemory();
        }

        public void loadImage() {
            loadImage(Math.max(getAdapterPosition(), 0));
        }
    }
}
