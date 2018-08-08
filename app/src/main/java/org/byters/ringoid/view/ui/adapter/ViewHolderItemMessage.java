package org.byters.ringoid.view.ui.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.view.presenter.IPresenterAdapterMessages;
import org.byters.ringoid.view.ui.dialog.DialogMenuImageOther;
import org.byters.ringoid.view.ui.util.IndicatorHelper;

import javax.inject.Inject;

public class ViewHolderItemMessage extends ViewHolderBase
        implements View.OnClickListener {

    @Inject
    IPresenterAdapterMessages presenterAdapterMessages;

    private DialogMenuImageOther dialogMenu;
    private FrameLayout flDots;
    private IndicatorHelper dotsIndicatorHelper;
    private RecyclerView rvItems;
    private AdapterMessagesImages adapter;
    private ImageView ivMessage;

    ViewHolderItemMessage(ViewGroup parent) {
        super(parent, R.layout.view_item_messages_images);
        ApplicationRingoid.getComponent().inject(this);

        flDots = itemView.findViewById(R.id.flDots);

        itemView.findViewById(R.id.tvMenu).setOnClickListener(this);
        ivMessage = itemView.findViewById(R.id.ivMessage);

        initList();
    }

    private void initList() {
        rvItems = itemView.findViewById(R.id.rvImages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        adapter = new AdapterMessagesImages();

        rvItems.setLayoutManager(layoutManager);
        rvItems.setAdapter(adapter);

        new PagerSnapHelper().attachToRecyclerView(rvItems);
        dotsIndicatorHelper = IndicatorHelper.getLinesAccentGreenHelper(flDots, rvItems, layoutManager);
    }

    @Override
    void setData(int position) {
        adapter.setPosition(position);
        dotsIndicatorHelper.updateData(presenterAdapterMessages.getItemsNum(position));
        ivMessage.setImageResource(presenterAdapterMessages.isMessagesNew(position)
                ? R.drawable.ic_message_full_green_24dp
                : presenterAdapterMessages.isMessagesExist(position)
                ? R.drawable.ic_message_dots_green_24dp
                : R.drawable.ic_message_border_green_24dp);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvMenu) {
            if (dialogMenu != null)
                dialogMenu.cancel();
            dialogMenu = new DialogMenuImageOther(itemView);
            dialogMenu.show();
        }

    }
}
