package org.byters.ringoid.view.ui.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.view.presenter.IPresenterPagesContainer;
import org.byters.ringoid.view.presenter.callback.IPresenterPagesContainerListener;

import javax.inject.Inject;

public class FragmentFeed extends FragmentBase
        implements View.OnClickListener {

    @Inject
    IPresenterPagesContainer presenterPagesContainer;

    private TextView tvWallet;
    private AlertDialog dialogInvite;
    private ListenerPresenter listenerPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
        presenterPagesContainer.setListener(listenerPresenter = new ListenerPresenter());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initViews(view);

        return view;
    }

    private void initViews(View view) {
        presenterPagesContainer.onViewCreate(getChildFragmentManager(), R.id.flContent);

        tvWallet = view.findViewById(R.id.tvWallet);

        view.findViewById(R.id.ivMenuRank).setOnClickListener(this);
        view.findViewById(R.id.ivMenuLikes).setOnClickListener(this);
        view.findViewById(R.id.ivMenuProfile).setOnClickListener(this);
        view.findViewById(R.id.ivMenuMessages).setOnClickListener(this);
        view.findViewById(R.id.ivMenuExplore).setOnClickListener(this);
        tvWallet.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.tvWallet)
            presenterPagesContainer.onClickWallet();

        if (view.getId() == R.id.ivMenuRank)
            presenterPagesContainer.onClickPageRank();

        if (view.getId() == R.id.ivMenuLikes)
            presenterPagesContainer.onClickPageLikes();

        if (view.getId() == R.id.ivMenuProfile)
            presenterPagesContainer.onClickPageProfile();

        if (view.getId() == R.id.ivMenuMessages)
            presenterPagesContainer.onClickPageMessages();

        if (view.getId() == R.id.ivMenuExplore)
            presenterPagesContainer.onClickPageExplore();

    }

    private class ListenerPresenter implements IPresenterPagesContainerListener {
        @Override
        public void showDialogInvite() {
            if (dialogInvite != null)
                dialogInvite.dismiss();

            //todo implement invite
            dialogInvite = new AlertDialog.Builder(getContext()).create();
            dialogInvite.setView(LayoutInflater.from(getContext()).inflate(R.layout.view_invite, null));
            dialogInvite.show();
        }

        @Override
        public void setWallet(int coinsNum) {
            tvWallet.setText(String.valueOf(coinsNum));
        }
    }
}
