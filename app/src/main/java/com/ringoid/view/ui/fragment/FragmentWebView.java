/*Copyright (c) Ringoid Ltd, 2018. All Rights Reserved*/
package com.ringoid.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.ringoid.ApplicationRingoid;
import com.ringoid.R;
import com.ringoid.view.INavigator;

import javax.inject.Inject;

public class FragmentWebView extends FragmentBase
        implements View.OnClickListener {

    private static final String ARG_URL = "arg_url";
    private static final String ARG_SUBTITLE = "arg_subtitle";

    @Inject
    INavigator navigator;

    public static Fragment getInstance(String url, String subtitle) {
        Bundle args = new Bundle();
        args.putString(ARG_URL, url);
        args.putString(ARG_SUBTITLE, subtitle);
        Fragment fragment = new FragmentWebView();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationRingoid.getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.ivBack).setOnClickListener(this);
        view.findViewById(R.id.ivBrowser).setOnClickListener(this);

        ((TextView) view.findViewById(R.id.tvSubtitle)).setText(getArguments().getString(ARG_SUBTITLE));

        WebView wvContent = view.findViewById(R.id.wvContent);
        wvContent.setWebViewClient(new WebViewClient());
        wvContent.setWebChromeClient(new WebChromeClient());
        wvContent.loadUrl(getArguments().getString(ARG_URL));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivBack) {
            if (getActivity() == null) return;
            getActivity().onBackPressed();
        }
        if (v.getId() == R.id.ivBrowser) {
            navigator.navigateUrlExternal(getContext(), getArguments().getString(ARG_URL));
        }
    }

}
