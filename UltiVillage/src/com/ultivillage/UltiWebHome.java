package com.ultivillage;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class UltiWebHome extends Activity {

	private String homeUrl = "http://ultivillage.com/";

	WebView mWebview;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.webview);

		mWebview = (WebView) findViewById(R.id.webview);
		mWebview.getSettings().setJavaScriptEnabled(true);
		mWebview.loadUrl(homeUrl);

		mWebview.setWebViewClient(new newWebView());

	}

	private class newWebView extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebview.canGoBack()) {
			mWebview.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}