package org.byters.ringoid.view.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.view.presenter.IPresenterAdapterMessagesImages;
import org.byters.ringoid.view.ui.dialog.DialogMenuMessages;
import org.byters.ringoid.view.ui.util.GlideApp;

import javax.inject.Inject;

public class ViewHolderItemMessagesImages extends ViewHolderBase
        implements View.OnClickListener {

    @Inject
    IPresenterAdapterMessagesImages presenterAdapterMessagesImages;

    private ImageView ivItem;
    private DialogMenuMessages dialogMenu;

    private int adapterPosition;

    public ViewHolderItemMessagesImages(ViewGroup parent) {
        super(parent, R.layout.view_image_messages);
        ApplicationRingoid.getComponent().inject(this);

        ivItem = itemView.findViewById(R.id.ivContent);
        itemView.findViewById(R.id.tvMenu).setOnClickListener(this);
    }

    @Override
    void setData(int position) {

    }

    public void setData(int adapterPosition, int position) {
        this.adapterPosition = adapterPosition;

        String url = presenterAdapterMessagesImages.getUrl(adapterPosition, position);

        ivItem.setImageDrawable(null);

        if (!TextUtils.isEmpty(url))
            GlideApp.with(itemView.getContext())
                    .load(url)
                    // .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .override(ivItem.getWidth(), ivItem.getHeight())
                    .centerCrop()
                    .into(ivItem);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvMenu) {
            if (dialogMenu != null)
                dialogMenu.cancel();
            dialogMenu = new DialogMenuMessages(itemView.getContext());
            dialogMenu.show();
        }
    }
}
