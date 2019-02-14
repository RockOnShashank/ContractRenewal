package com.residents.dubaiassetmanagement;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebviewFragment  extends Fragment {

private View view;
private WebView wv_instafeed;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.webview_layout, container, false);
        wv_instafeed = (WebView) view.findViewById(R.id.wv_instafeed);
        wv_instafeed.loadUrl("https://deloittecustomerportalscal-333870-cd.azurewebsites.net/InstagramFeed?communityCode="+"Remraam");
        WebSettings webSettings = wv_instafeed.getSettings();
        webSettings.setJavaScriptEnabled(true);
return view;
    }
}
