package org.byters.ringoid.view.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
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
    public int getItemCount() {
        return presenterAdapterRankImages.getItemsNum(position);
    }

    public void setPosition(int position) {
        this.position = position;
        notifyDataSetChanged();
    }

    public void loadImage(RecyclerView.ViewHolder viewHolder, int height) {
        if (viewHolder == null) return;
        ((ViewHolderItem) viewHolder).loadImage();
    }

    public void onAttached(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder == null) return;
        ((ViewHolderItem) viewHolder).hideLikeAnimation();
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
            if (v == itemView)
                showLikeAnimation();
        }

        private void loadImage(int pos) {

            hideLikeAnimation();

            String url = presenterAdapterRankImages.getUrl(AdapterRankImages.this.position, pos);

            ivItem.setImageDrawable(null);
            if (!TextUtils.isEmpty(url))
                GlideApp.with(itemView.getContext())
                        .load(url)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(ivItem);
        }

        void onDetached() {
            ivItem.setImageDrawable(null);
            GlideApp.get(itemView.getContext()).clearMemory();
        }

        void loadImage() {
            loadImage(Math.max(getAdapterPosition(), 0));
        }

        void hideLikeAnimation() {
            if (animation != null) animation.cancel();
            ivLikeAnimated.clearAnimation();
            ivLikeAnimated.setVisibility(View.GONE);
        }

        private void showLikeAnimation() {

            int size = (int) itemView.getContext().getResources().getDimension(R.dimen.like_animation);
            int x = itemView.getContext().getResources().getDisplayMetrics().widthPixels / 2 - size / 2;
            int y = presenterAdapterRankImages.getItemHeight(itemView.getContext(), position, getAdapterPosition()) / 2 - size / 2;
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) ivLikeAnimated.getLayoutParams();
            params.setMargins(x, y, 0, 0);
            ivLikeAnimated.setLayoutParams(params);

            if (animation == null) {
                ivLikeAnimated.setVisibility(View.VISIBLE);
                Animation animationAlphaIn = new AlphaAnimation(0.5f, 0.9f);
                animationAlphaIn.setDuration(250);

                Animation animationResize = new ScaleAnimation(0.5f, 1f, 0.5f, 1f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                animationResize.setDuration(250);
                animationResize.setInterpolator(new OvershootInterpolator());

                Animation animationAlphaOut = new AlphaAnimation(0.9f, 0f);
                animationAlphaOut.setDuration(50);
                animationAlphaOut.setStartOffset(500);
                animationAlphaOut.setInterpolator(new DecelerateInterpolator());

                animation = new AnimationSet(false);
                animation.addAnimation(animationAlphaIn);
                animation.addAnimation(animationResize);
                animation.addAnimation(animationAlphaOut);
                animation.setFillAfter(true);

            }
            animation.reset();
            ivLikeAnimated.startAnimation(animation);
        }
    }
}
