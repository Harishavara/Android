package com.ultivillage;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class UltiMain extends TabActivity implements OnTabChangeListener {
	private TabHost tabHost;

	// Called when the activity is first created.
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);

		this.tabHost = getTabHost();

		// TabHost will have Tabs
		TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);

		/**
		 * OnTabChangedListener will listen when user change tabs By then, it
		 * will change the tabs background colour
		 */
		tabHost.setOnTabChangedListener(this);

		/**
		 * TabSpec used to create a new tab. By using TabSpec, only we can able
		 * to setContent to the tab.
		 */
		TabSpec firstTabSpec = tabHost.newTabSpec("cotd");
		TabSpec secondTabSpec = tabHost.newTabSpec("uvtv");
		TabSpec thirdTabSpec = tabHost.newTabSpec("feeds");
		TabSpec fourthTabSpec = tabHost.newTabSpec("about");

		// TabSpec setIndicator() is used to set the name for the tab.
		firstTabSpec.setIndicator("COTD", getResources().getDrawable(
				R.drawable.ulticotd_tab));
		secondTabSpec.setIndicator("UVtv", getResources().getDrawable(
				R.drawable.ultiuvtv_tab));
		thirdTabSpec.setIndicator("Feeds", getResources().getDrawable(
				R.drawable.ultifeeds_tab));
		fourthTabSpec.setIndicator("About", getResources().getDrawable(
				R.drawable.ultiabout_tab));

		// TabSpec setContent() is used to set content for a particular tab.
		firstTabSpec.setContent(new Intent(this, UltiCOTDSub.class));
		secondTabSpec.setContent(new Intent(this, UltiUVTV.class));
		thirdTabSpec.setContent(new Intent(this, UltiFeeds.class));
		fourthTabSpec.setContent(new Intent(this, UltiAbout.class));

		// Add tabSpec to the TabHost to display.
		tabHost.addTab(firstTabSpec);
		tabHost.addTab(secondTabSpec);
		tabHost.addTab(thirdTabSpec);
		tabHost.addTab(fourthTabSpec);

		// Set the tabs colour at the start
		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			tabHost.getTabWidget().getChildAt(i).setBackgroundColor(
					Color.parseColor("#01359b"));
		}
		tabHost.getTabWidget().setCurrentTab(0);
		tabHost.getTabWidget().getChildAt(0).setBackgroundColor(
				Color.parseColor("#4189c4"));

	}

	// Changes tab colour when user clicks on a tab
	@Override
	public void onTabChanged(String tabId) {
		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			tabHost.getTabWidget().getChildAt(i).setBackgroundColor(
					Color.parseColor("#01359b"));
		}
		tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab())
				.setBackgroundColor(Color.parseColor("#4189c4"));
	}

}