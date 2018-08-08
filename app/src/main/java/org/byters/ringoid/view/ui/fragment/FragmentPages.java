package org.byters.ringoid.view.ui.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.view.presenter.IPresenterPagesContainer;
import org.byters.ringoid.view.presenter.callback.IPresenterPagesContainerListener;

import javax.inject.Inject;

public class FragmentPages extends FragmentBase
        implements View.OnClickListener {

    @Inject
    IPresenterPagesContainer presenterPagesContainer;

    private TextView tvWallet, tvSubtitle;
    private AlertDialog dialogInvite;
    private ListenerPresenter listenerPresenter;
    private View flToolbar;
    private ViewGroup llBottomAppBar;
    private ImageView ivPrivacy;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
        presenterPagesContainer.setListener(listenerPresenter = new ListenerPresenter());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pages, container, false);

        initViews(view);

        presenterPagesContainer.onViewCreate(getChildFragmentManager(), R.id.flContent);
        return view;
    }

    private void initViews(View view) {

        tvWallet = view.findViewById(R.id.tvWallet);
        tvSubtitle = view.findViewById(R.id.tvSubtitle);

        flToolbar = view.findViewById(R.id.flToolbar);
        llBottomAppBar = view.findViewById(R.id.llBottomAppBar);

        view.findViewById(R.id.ivMenuLikes).setOnClickListener(this);
        view.findViewById(R.id.ivMenuProfile).setOnClickListener(this);
        view.findViewById(R.id.ivMenuMessages).setOnClickListener(this);
        view.findViewById(R.id.ivMenuExplore).setOnClickListener(this);
        tvWallet.setOnClickListener(this);
        view.findViewById(R.id.tvSettings).setOnClickListener(this);

        ivPrivacy = view.findViewById(R.id.ivPrivacy);
        ivPrivacy.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.tvWallet)
            presenterPagesContainer.onClickWallet();

        if (view.getId() == R.id.tvSettings)
            presenterPagesContainer.onClickSettings();

        if (view.getId() == R.id.ivMenuLikes)
            presenterPagesContainer.onClickPageLikes();

        if (view.getId() == R.id.ivMenuProfile)
            presenterPagesContainer.onClickPageProfile();

        if (view.getId() == R.id.ivMenuMessages)
            presenterPagesContainer.onClickPageMessages();

        if (view.getId() == R.id.ivMenuExplore)
            presenterPagesContainer.onClickPageExplore();

        if (view.getId() == R.id.ivPrivacy)
            presenterPagesContainer.onClickPrivacy();

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

        @Override
        public void setPosition(int topPos, int bottomPos, float alpha) {
            flToolbar.setTranslationY(topPos);
            llBottomAppBar.setTranslationY(bottomPos);

            flToolbar.setAlpha(alpha);
            llBottomAppBar.setAlpha(alpha);
        }

        @Override
        public void setPageSelected(int num, int backgroundColorRes, int subtitleColorRes) {
            if (llBottomAppBar == null) return;
            for (int i = 0; i < llBottomAppBar.getChildCount(); ++i) {
                View view = llBottomAppBar.getChildAt(i);
                if (view == null) continue;

                if (i == num)
                    view.setBackgroundColor(getContext().getResources().getColor(backgroundColorRes));
                else
                    view.setBackground(null);

            }

            String subtitle = getContext().getResources().getStringArray(R.array.pages_titles)[num];
            tvSubtitle.setText(TextUtils.isEmpty(subtitle) ? "" : subtitle);
            tvSubtitle.setTextColor(getContext().getResources().getColor(subtitleColorRes));
        }

        @Override
        public void setViewPrivacy(int drawableId) {
            ivPrivacy.setImageResource(drawableId);
        }

        @Override
        public void setBottomSheetDrawables(int profile, int likes, int messages, int explore) {
            if (llBottomAppBar == null || llBottomAppBar.getChildCount() != 4) return;
            ((ImageView) llBottomAppBar.getChildAt(0)).setImageResource(profile);
            ((ImageView) llBottomAppBar.getChildAt(1)).setImageResource(likes);
            ((ImageView) llBottomAppBar.getChildAt(2)).setImageResource(messages);
            ((ImageView) llBottomAppBar.getChildAt(3)).setImageResource(explore);

        }
    }
}
