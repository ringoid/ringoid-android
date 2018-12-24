/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterAdapterProfile;
import com.ringoid.view.presenter.callback.IPresenterAdapterProfileListener;
import com.ringoid.view.ui.dialog.DialogImageRemove;
import com.ringoid.view.ui.dialog.callback.IDialogImageRemoveListener;
import com.ringoid.view.ui.util.GlideApp;

import javax.inject.Inject;

public class AdapterProfile extends AdapterBase {

    @Inject
    IPresenterAdapterProfile presenterAdapterProfile;

    private ListenerPresenter listenerPresenter;

    private ListenerDialogImageRemove listenerDialogImageRemove;
    private DialogImageRemove dialogImageRemove;

    public AdapterProfile() {
        ApplicationRingoid.getComponent().inject(this);
        presenterAdapterProfile.setListener(listenerPresenter = new ListenerPresenter());

        listenerDialogImageRemove = new ListenerDialogImageRemove();
    }

    @NonNull
    @Override
    public ViewHolderBase onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderItem(parent);
    }

    @Override
    public int getItemCount() {
        return presenterAdapterProfile.getItemsNum();
    }

    private void showDialogRemove(Context context, String imageId, String localId, String originId) {
        if (dialogImageRemove != null)
            dialogImageRemove.cancel();

        dialogImageRemove = new DialogImageRemove(context, imageId, localId, originId, listenerDialogImageRemove);
        dialogImageRemove.show();
    }

    class ViewHolderItem extends ViewHolderBase implements View.OnClickListener {
        private View ivRemove;
        private ImageView ivItem;
        private TextView tvLikes;

        ViewHolderItem(ViewGroup parent) {
            super(parent, R.layout.view_image_profile);

            ivItem = itemView.findViewById(R.id.ivContent);
            tvLikes = itemView.findViewById(R.id.tvLikes);
            ivRemove = itemView.findViewById(R.id.ivRemove);

            ivRemove.setOnClickListener(this);
            itemView.setOnClickListener(this);
            tvLikes.setOnClickListener(this);
        }

        @Override
        void setData(int position) {
            showImage(position);
            setLikes(position);
        }

        private void setLikes(int position) {
            int likes = presenterAdapterProfile.getLikesNum(position);
            tvLikes.setVisibility(likes > 0 ? View.VISIBLE : View.GONE);
            tvLikes.setText(String.valueOf(likes));
        }

        private void showImage(int position) {
            String url = presenterAdapterProfile.getUrl(position);
            String urlThumbnail = presenterAdapterProfile.getUrlThumbnail(position);
            if (TextUtils.isEmpty(url))
                ivItem.setImageDrawable(null);
            else
                imageLoad(position, url, urlThumbnail);
        }

        private void imageLoad(int position, String url, String urlThumbnail) {
            if (!TextUtils.isEmpty(urlThumbnail)) {
                loadImageGlide(urlThumbnail);

                GlideApp.with(itemView.getContext())
                        .load(url)
                        .into(new LoadingTarget(position));
                return;
            }

            loadImageGlide(url);
        }

        private void loadImageGlide(String url) {
            GlideApp.with(itemView.getContext())
                    .load(url)
                    .override(ivItem.getWidth(), ivItem.getHeight())
                    .centerCrop()
                    .into(ivItem);
        }

        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.tvLikes)
                onClickLikes();

            if (v.getId() == R.id.ivRemove) {
                showDialogRemove(itemView.getContext(),
                        presenterAdapterProfile.getImageId(getAdapterPosition()),
                        presenterAdapterProfile.getImageLocalId(getAdapterPosition()),
                        presenterAdapterProfile.getImageOriginId(getAdapterPosition())
                );
            }
        }

        private void onClickLikes() {
            presenterAdapterProfile.onClickLikes();
        }


        private class LoadingTarget extends SimpleTarget<Drawable> {

            private int position;

            LoadingTarget(int position) {
                this.position = position;
            }

            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                if (getAdapterPosition() != position) return;
                ivItem.setImageDrawable(resource);
            }
        }
    }

    private class ListenerPresenter implements IPresenterAdapterProfileListener {
        @Override
        public void onUpdate() {
            notifyDataSetChanged();
        }

        @Override
        public void onUpdateRemove(int position) {
            notifyItemRemoved(position);
        }

        @Override
        public void onUpdateAdd(int position) {
            notifyItemInserted(position);
        }
    }

    private class ListenerDialogImageRemove implements IDialogImageRemoveListener {
        @Override
        public void onSuccess(String imageId, String localId, String originId) {
            presenterAdapterProfile.onImageRemove(imageId, localId, originId);
        }
    }
}
