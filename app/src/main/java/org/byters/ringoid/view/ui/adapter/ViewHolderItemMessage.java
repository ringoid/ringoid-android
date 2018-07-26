package org.byters.ringoid.view.ui.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.view.presenter.IPresenterAdapterMessages;
import org.byters.ringoid.view.ui.dialog.DialogMenuMessages;
import org.byters.ringoid.view.ui.util.DotsIndicatorHelper;

import javax.inject.Inject;

public class ViewHolderItemMessage extends ViewHolderBase
        implements View.OnClickListener {

    @Inject
    IPresenterAdapterMessages presenterAdapterMessages;

    private DialogMenuMessages dialogMenu;
    private FrameLayout flDots;
    private DotsIndicatorHelper dotsIndicatorHelper;
    private RecyclerView rvItems;
    private AdapterMessagesImages adapter;

    ViewHolderItemMessage(ViewGroup parent) {
        super(parent, R.layout.view_item_messages_images);
        ApplicationRingoid.getComponent().inject(this);

        flDots = itemView.findViewById(R.id.flDots);

        itemView.findViewById(R.id.tvMenu).setOnClickListener(this);
        initList();
    }

    private void initList() {
        rvItems = itemView.findViewById(R.id.rvImages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        adapter = new AdapterMessagesImages();

        rvItems.setLayoutManager(layoutManager);
        rvItems.setAdapter(adapter);

        new PagerSnapHelper().attachToRecyclerView(rvItems);
        dotsIndicatorHelper = new DotsIndicatorHelper(flDots, rvItems, layoutManager);
    }

    @Override
    void setData(int position) {
        adapter.setPosition(position);
        dotsIndicatorHelper.updateData(presenterAdapterMessages.getItemsNum(position));
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
