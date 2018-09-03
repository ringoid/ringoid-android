/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.adapter;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.presenter.IPresenterAdapterProfile;
import com.ringoid.view.ui.dialog.DialogHiddenMode;
import com.ringoid.view.ui.dialog.DialogProfileLikes;
import com.ringoid.view.ui.dialog.callback.IDialogHiddenModeListener;
import com.ringoid.view.ui.dialog.callback.IDialogProfileLikesListener;
import com.ringoid.view.ui.util.GlideApp;

import javax.inject.Inject;

public class AdapterProfile extends AdapterBase {

    @Inject
    IPresenterAdapterProfile presenterAdapterProfile;

    private DialogHiddenMode dialogHiddenMode;
    private DialogProfileLikes dialogProfileLikes;

    private ListenerDialogHidden listenerDialogHidden;
    private IDialogProfileLikesListener listenerDialogProfileLikes;

    public AdapterProfile() {
        ApplicationRingoid.getComponent().inject(this);

        listenerDialogProfileLikes = new ListenerDialogProfileLikes();
        listenerDialogHidden = new ListenerDialogHidden();
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

    class ViewHolderItem extends ViewHolderBase implements View.OnClickListener {
        private ImageView ivItem;
        private TextView tvLikes;

        ViewHolderItem(ViewGroup parent) {
            super(parent, R.layout.view_image_profile);
            ivItem = itemView.findViewById(R.id.ivContent);
            itemView.findViewById(R.id.ivRemove).setOnClickListener(this);
            itemView.setOnClickListener(this);
            tvLikes = itemView.findViewById(R.id.tvLikes);
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
        }

        private void showDialogHidden() {
            if (dialogHiddenMode != null)
                dialogHiddenMode.cancel();
            dialogHiddenMode = new DialogHiddenMode(itemView.getContext(), listenerDialogHidden);
            dialogHiddenMode.show();
        }

        private void onClickLikes() {
            if (dialogProfileLikes != null)
                dialogProfileLikes.cancel();
            if (!presenterAdapterProfile.isDialogShowLikes()) return;
            dialogProfileLikes = new DialogProfileLikes(itemView.getContext(), listenerDialogProfileLikes);
            dialogProfileLikes.show();
        }

    }

    private class ListenerDialogProfileLikes implements IDialogProfileLikesListener {
        @Override
        public void onSelectOK(boolean isShow) {
            presenterAdapterProfile.onClickOK(isShow);
        }

        @Override
        public void onSelectLiked(boolean isShow) {
            presenterAdapterProfile.onClickLiked(isShow);
        }
    }

    private class ListenerDialogHidden implements IDialogHiddenModeListener {
        @Override
        public void onSelectAbout(boolean isShow) {
            presenterAdapterProfile.onClickAboutHiddenMode(isShow);
        }

        @Override
        public void onSelectOK(boolean isShow) {
            presenterAdapterProfile.onClickHiddenModeOK(isShow);
        }

        @Override
        public void onSelectPrivacy(boolean isShow) {
            presenterAdapterProfile.onClickHiddenModePrivacy(isShow);
        }
    }
}
