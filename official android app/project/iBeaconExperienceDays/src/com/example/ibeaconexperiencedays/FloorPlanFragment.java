package com.example.ibeaconexperiencedays;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class FloorPlanFragment extends Fragment {

	WebView webView;
	Handler handler;
	
	public FloorPlanFragment()
	{
		super();
		handler = new Handler();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup bla, Bundle savedInstanceState) {
		View rootView2 = inflater.inflate(R.layout.floorplan, bla, false);

		webView = (WebView) rootView2.findViewById(R.id.webView3);
		webView.setBackgroundColor(0x323232);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("http://jon651.glimworm.com/ibeacon/api-expdays.php?action=floorplan");
		handler.postDelayed(refreshContent, 300000);
		return rootView2;
	}

	Runnable refreshContent = new Runnable() {
		@Override
		public void run() {
			if (webView != null)
				webView.loadUrl("http://jon651.glimworm.com/ibeacon/api-expdays.php?action=floorplan");
		}
	};
}