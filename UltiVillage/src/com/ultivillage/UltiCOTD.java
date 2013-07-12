package com.ultivillage;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ultivillage.Message;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.ListView;

public class UltiCOTD extends ListActivity {

	private List<Message> messages;
	List<String> vpCol;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.listact);
		loadFeed();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		// Get the format for the video
		String videoForm = "";
		String youtubeLink = "";
		
		//String videoForm = videoFormat(vpCol.get(position));
		
		if (vpCol.get(position).contains("embed/")) {
			youtubeLink = vpCol.get(position).replace("embed/", "watch?v=");
		}
		else {
			youtubeLink = vpCol.get(position);
		}

		videoForm = "video/*";
		//videoForm = "video/" + videoForm;

		Intent intent = new Intent(Intent.ACTION_VIEW);
		// intent.setDataAndType( Uri.parse(vpCol.get(position)), "video/mp4");
		intent.setDataAndType(Uri.parse(youtubeLink), videoForm);
		

		startActivityForResult(intent, 1);

		super.onListItemClick(l, v, position, id);
	}

	protected void loadFeed() {
		try {
			BaseFeedParser parser = new BaseFeedParser();
			messages = parser.parse();
			List<String> titles = new ArrayList<String>(messages.size());

			vpCol = new ArrayList<String>(messages.size());
			// vpCol =messages;

			for (Message msg : messages) {
				String vp = videoPath(msg.getDescription());
				vpCol.add(vp);
			}

			for (Message msg : messages) {
				titles.add(msg.getTitle());
			}

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					R.layout.row, titles);

			this.setListAdapter(adapter);
		} catch (Throwable t) {
			Log.e("AndroidNews", t.getMessage(), t);
		}
	}

	// To know the format of the video
	private String videoFormat(String s) {

		int myEnd = s.length();
		int myStart = myEnd - 4;
		String temp = s.substring(myStart, myEnd);

		if (temp.equalsIgnoreCase(".mp4")) {
			temp = "mp4";
		} else if (temp.equalsIgnoreCase(".mov")) {
			temp = "mov";
		}

		return temp;
	}

	private String videoPath(String desc) {
		String path = "";
		
		String[] arr = desc.split("src=\"");
		for (String s : arr) {
			
			int position = s.indexOf("\"");
			path = s.substring(0, position);
		}
		
		/**
		String path = "http://www.ultivillage.com/images/";
		String[] arr = desc.split("/");
		boolean found = false;
		for (String s : arr) {
			if ((found == true) || (s.equalsIgnoreCase("stories"))) {

				int i = s.indexOf(".");
				if (i == -1) {
					path = path.concat(s);
					path = path.concat("/");
				} else {
					String tmp = s.substring(0, i + 4);
					path = path.concat(tmp);
					break;
				}

				found = true;

			}
		}
		**/
		return path;
	}
}