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

import com.ringoid.R;

public class FragmentWebView extends FragmentBase
        implements View.OnClickListener {

    private static final String ARG_URL = "arg_url";

    public static Fragment getInstance(String url) {
        Bundle args = new Bundle();
        args.putString(ARG_URL, url);
        Fragment fragment = new FragmentWebView();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        WebView wvContent = view.findViewById(R.id.wvContent);
        wvContent.setWebViewClient(new WebViewClient());
        wvContent.setWebChromeClient(new WebChromeClient());
        wvContent.loadUrl(getArguments().getString(ARG_URL));
    }

    @Override
    public void onClick(View v) {
        if (getActivity() == null) return;
        getActivity().onBackPressed();
    }
}
