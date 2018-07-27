package org.byters.ringoid.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.byters.ringoid.ApplicationRingoid;
import org.byters.ringoid.R;
import org.byters.ringoid.view.presenter.IPresenterChat;
import org.byters.ringoid.view.presenter.callback.IPresenterChatListener;
import org.byters.ringoid.view.ui.dialog.DialogMenuMessages;
import org.byters.ringoid.view.ui.util.GlideApp;
import org.byters.ringoid.view.ui.util.TransformationAlpha;

import javax.inject.Inject;

public class FragmentChat extends FragmentBase implements View.OnClickListener {

    @Inject
    IPresenterChat presenterChat;

    private ImageView ivUser;
    private ListenerPresenter listenerPresenter;
    private DialogMenuMessages dialogMenu;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
        presenterChat.setListener(listenerPresenter = new ListenerPresenter());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        intiViews(view);
        presenterChat.onCreateView();
        return view;
    }

    private void intiViews(View view) {
        ivUser = view.findViewById(R.id.ivUser);
        view.findViewById(R.id.ivBack).setOnClickListener(this);
        view.findViewById(R.id.ivMenu).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivBack)
            getActivity().onBackPressed();
        if (v.getId() == R.id.ivMenu)
            showMenu();
    }

    private void showMenu() {
        if (dialogMenu != null) dialogMenu.cancel();
        dialogMenu = new DialogMenuMessages(getContext());
        dialogMenu.show();
    }

    private class ListenerPresenter implements IPresenterChatListener {

        @Override
        public void setImage(String url) {

            if (TextUtils.isEmpty(url))
                ivUser.setImageDrawable(null);
            else
                GlideApp.with(getContext())
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .transform(new TransformationAlpha())
                        .into(ivUser);

        }
    }

}
