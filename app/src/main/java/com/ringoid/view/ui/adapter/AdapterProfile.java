/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterAdapterProfile;
import com.ringoid.view.presenter.callback.IPresenterAdapterProfileListener;
import com.ringoid.view.ui.dialog.DialogHiddenMode;
import com.ringoid.view.ui.dialog.DialogImageRemove;
import com.ringoid.view.ui.dialog.callback.IDialogHiddenModeListener;
import com.ringoid.view.ui.dialog.callback.IDialogImageRemoveListener;
import com.ringoid.view.ui.util.GlideApp;

import javax.inject.Inject;

public class AdapterProfile extends AdapterBase {

    @Inject
    IPresenterAdapterProfile presenterAdapterProfile;

    private ListenerPresenter listenerPresenter;

    private DialogHiddenMode dialogHiddenMode;

    private ListenerDialogHidden listenerDialogHidden;
    private ListenerDialogImageRemove listenerDialogImageRemove;
    private DialogImageRemove dialogImageRemove;

    public AdapterProfile() {
        ApplicationRingoid.getComponent().inject(this);
        presenterAdapterProfile.setListener(listenerPresenter = new ListenerPresenter());

        listenerDialogHidden = new ListenerDialogHidden();
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

    private void showDialogRemove(Context context, String imageId, int likesNum, boolean imageLast) {
        if (dialogImageRemove != null)
            dialogImageRemove.cancel();

        dialogImageRemove = new DialogImageRemove(context, imageId, likesNum, listenerDialogImageRemove, imageLast);
        dialogImageRemove.show();
    }

    class ViewHolderItem extends ViewHolderBase implements View.OnClickListener {
        private ImageView ivItem;
        private TextView tvLikes;

        ViewHolderItem(ViewGroup parent) {
            super(parent, R.layout.view_image_profile);

            ivItem = itemView.findViewById(R.id.ivContent);
            tvLikes = itemView.findViewById(R.id.tvLikes);

            itemView.findViewById(R.id.ivRemove).setOnClickListener(this);
            itemView.setOnClickListener(this);
            tvLikes.setOnClickListener(this);
        }

        @Override
        void setData(int position) {
            loadImage(position);
            setLikes(position);
        }

        private void setLikes(int position) {
            int likes = presenterAdapterProfile.getLikesNum(position);
            tvLikes.setVisibility(likes > 0 ? View.VISIBLE : View.GONE);
            tvLikes.setText(String.valueOf(likes));
        }

        private void loadImage(int position) {
            String url = presenterAdapterProfile.getUrl(position);
            if (TextUtils.isEmpty(url))
                ivItem.setImageDrawable(null);
            else {
                if (ivItem.getDrawable() == null)
                    GlideApp.with(itemView.getContext())
                            .load(url)
                            //.override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .override(ivItem.getWidth(), ivItem.getHeight())
                            .centerCrop()
                            .into(ivItem);
            }
        }

        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.tvLikes)
                onClickLikes();

            if (v == itemView)
                if (presenterAdapterProfile.onClickItem(itemView.getContext(), getAdapterPosition())) {
                    showDialogHidden();
                }

            if (v.getId() == R.id.ivRemove) {
                showDialogRemove(itemView.getContext(),
                        presenterAdapterProfile.getImageId(getAdapterPosition()),
                        presenterAdapterProfile.getLikesNum(getAdapterPosition()),
                        presenterAdapterProfile.isImageLast());
            }
        }

        private void showDialogHidden() {
            if (dialogHiddenMode != null)
                dialogHiddenMode.cancel();
            dialogHiddenMode = new DialogHiddenMode(itemView.getContext(), listenerDialogHidden);
            dialogHiddenMode.show();
        }

        private void onClickLikes() {
            presenterAdapterProfile.onCLickLikes();
        }

    }

    private class ListenerDialogHidden implements IDialogHiddenModeListener {

        @Override
        public void onSelectOK(boolean isShow) {
            presenterAdapterProfile.onClickHiddenModeOK(isShow);
        }

        @Override
        public void onSelectPrivacy(boolean isShow) {
            presenterAdapterProfile.onClickHiddenModePrivacy(isShow);
        }
    }

    private class ListenerPresenter implements IPresenterAdapterProfileListener {
        @Override
        public void onUpdate() {
            notifyDataSetChanged();
        }
    }

    private class ListenerDialogImageRemove implements IDialogImageRemoveListener {
        @Override
        public void onSuccess(String imageId) {
            presenterAdapterProfile.onImageRemove(imageId);
        }
    }
}
