package org.byters.ringoid.view.ui.adapter;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.target.Target;

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
    public int getItemCount() {
        return presenterAdapterProfile.getItemsNum();
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
            loadImage(position);
            setLikes(position);
        }

        private void setLikes(int position) {
            int likes = presenterAdapterProfile.getLikesNum(position);
            tvLikes.setVisibility(likes > 0 ? View.VISIBLE : View.GONE);
            tvLikes.setText(String.format(itemView.getContext().getString(R.string.format_likes), likes));
        }

        private void loadImage(int position) {
            String url = presenterAdapterProfile.getUrl(position);
            if (TextUtils.isEmpty(url))
                ivItem.setImageDrawable(null);
            else {
                if (ivItem.getDrawable() == null)
                    GlideApp.with(itemView.getContext())
                            .load(url)
                            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .into(ivItem);
            }
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
    }
}
