package com.example.ibeaconexperiencedays;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class InfoFragment extends Fragment {

	WebView webView;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup bla, Bundle savedInstanceState) {
        View rootView2 = inflater.inflate(R.layout.info, bla,false);

		webView = (WebView) rootView2.findViewById(R.id.webView2);
		webView.setBackgroundColor(0x323232);
		webView.getSettings().setJavaScriptEnabled(true);
		String url =  "file:///android_asset/info.html";
		webView.loadUrl(url);
        return rootView2;
    }
}