package com.ultivillage;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UltiAbout extends Activity {

	TextView textUrl;
	TextView textEmail;
	String[] emailLink = {"rob@ultivillage.com"};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.about);

		textUrl = (TextView) findViewById(R.id.txtUrl);
		textEmail = (TextView) findViewById(R.id.txtEmail);

		textUrl.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("com.ultivillage.ultiWebHome");
				startActivity(intent);
			}

		});

		textEmail.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_SENDTO);
				Uri uri = Uri.parse("mailto:rob@ultivillage.com");
				intent.setData(uri);
				intent.putExtra(Intent.EXTRA_TEXT, "Email your text");
				intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
				startActivity(intent);

			}

		});
	}
}