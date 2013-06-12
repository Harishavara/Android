package com.asg6;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewStyle;
import com.jjoe64.graphview.LineGraphView;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class Graph extends Activity  {
	
	GestureDetector gestureDetector;
	GraphView graphView;
	LinearLayout layout;
	private int cadetblue = 0xff5F9EA0;
	private int red = 0xffFF0000;
	private int sequence = 1;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		gestureDetector = new GestureDetector(this, new MyListener());
		GraphViewStyle style = new GraphViewStyle(red, 3);
		
		makeGraph(this, style);
	}
	
	public void makeGraph(Context context, GraphViewStyle style){
		// sin curve
		int num = 150;
		GraphViewData[] data = new GraphViewData[num];
		double v=0;
		for (int i=0; i<num; i++) {
		   v += 0.2;
		   data[i] = new GraphViewData(i, Math.sin(v));
		}
		
		GraphViewSeries seriesSin = new GraphViewSeries("Sinus curve", style, data);

		//Create Graph
		graphView = new LineGraphView(this, "Double Tap to Change Colours");
		
		// Add data
		graphView.addSeries(seriesSin);
		
		// Set view port, start=2, size=10
		//graphView.setViewPort(2, 10);
		//graphView.setScalable(true);

		layout = (LinearLayout) findViewById(R.id.layout);
		layout.addView(graphView);
		
		setContentView(layout);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent me) {
		
		gestureDetector.onTouchEvent(me);
		return false;
	}
	
	class MyListener extends GestureDetector.SimpleOnGestureListener {

		GraphViewStyle style;

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			layout.removeView(graphView);
			context = getBaseContext();
			
			if (sequence == 1){
				style = new GraphViewStyle(cadetblue, 3);
				sequence = 2;
			}
			else if (sequence == 2) {
				style = new GraphViewStyle(red, 3);
				sequence = 1;
			}
			
			makeGraph(context, style);
			return false;
		}
	}
}
