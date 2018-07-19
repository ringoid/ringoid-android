package org.byters.ringoid.view.ui.adapter;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.view.presenter.IPresenterAdapterCountryList;
import org.byters.ringoid.view.presenter.callback.IPresenterAdapterCountryListListener;

import javax.inject.Inject;

public class AdapterCountryList extends AdapterBase {

    private final ListenerPresenter listenerPresenter;
    @Inject
    IPresenterAdapterCountryList presenterAdapterCountryList;

    public AdapterCountryList() {
        ApplicationRingoid.getComponent().inject(this);
        presenterAdapterCountryList.setLstener(listenerPresenter = new ListenerPresenter());
    }

    @NonNull
    @Override
    public ViewHolderBase onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderItem(parent);
    }

    @Override
    public int getItemCount() {
        return presenterAdapterCountryList.getItemsNum();
    }

    private class ListenerPresenter implements IPresenterAdapterCountryListListener {
        @Override
        public void onUpdate() {
            notifyDataSetChanged();
        }
    }

    private class ViewHolderItem extends ViewHolderBase implements View.OnClickListener {
        private TextView tvCountry;

        public ViewHolderItem(ViewGroup parent) {
            super(parent, R.layout.view_item_country);
            tvCountry = itemView.findViewById(R.id.tvCountry);
            itemView.setOnClickListener(this);
        }

        @Override
        void setData(int position) {
            String title = presenterAdapterCountryList.getItemTitle(position);
            tvCountry.setText(TextUtils.isEmpty(title) ? "" : title);
        }

        @Override
        public void onClick(View view) {
            presenterAdapterCountryList.onClickItem(getAdapterPosition());
        }
    }
}
