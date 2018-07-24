package org.byters.ringoid.view.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.view.presenter.IPresenterAdapterProfile;
import org.byters.ringoid.view.ui.dialog.DialogMenuProfile;
import org.byters.ringoid.view.ui.util.GlideApp;

import javax.inject.Inject;

public class AdapterProfile extends AdapterBase {

    @Inject
    IPresenterAdapterProfile presenterAdapterProfile;

    public AdapterProfile() {
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
        return presenterAdapterProfile.getItemsNum();
    }

    public void loadImage(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder == null) return;
        ((ViewHolderItem) viewHolder).loadImage();
    }

    class ViewHolderItem extends ViewHolderBase implements View.OnClickListener {
        private ImageView ivItem;
        private DialogMenuProfile dialogMenu;
        private TextView tvLikes;

        ViewHolderItem(ViewGroup parent) {
            super(parent, R.layout.view_image_profile);
            ivItem = itemView.findViewById(R.id.ivContent);
            itemView.findViewById(R.id.tvMenu).setOnClickListener(this);
            itemView.setOnClickListener(this);
            tvLikes = itemView.findViewById(R.id.tvLikes);
        }

        @Override
        void setData(int position) {
            if (position == 0)
                loadImage(position);

            int likes = presenterAdapterProfile.getLikesNum(position);
            tvLikes.setVisibility(likes > 0 ? View.VISIBLE : View.GONE);
            tvLikes.setText(String.format(itemView.getContext().getString(R.string.format_likes), likes));
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.tvMenu) {
                if (dialogMenu != null)
                    dialogMenu.cancel();
                dialogMenu = new DialogMenuProfile(itemView.getContext());
                dialogMenu.show();
            }
        }


        private void loadImage(int pos) {

            String url = presenterAdapterProfile.getUrl(pos);
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
