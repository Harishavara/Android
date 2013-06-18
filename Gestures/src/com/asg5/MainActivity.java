package com.asg5;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	GestureDetector gestureDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		gestureDetector = new GestureDetector(this, new SwipeListener());
		
		setContentView(R.layout.main);
		
	}
	
	private void onLeftSwipe() {
		Toast.makeText(MainActivity.this, "Left Swipe", Toast.LENGTH_SHORT).show();
	}

	private void onRightSwipe() {
		Toast.makeText(MainActivity.this, "Right Swipe", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent me) {
		
		gestureDetector.onTouchEvent(me);
		return false;
	}
	
	class SwipeListener extends GestureDetector.SimpleOnGestureListener {

		private static final int SWIPE_MIN_DISTANCE = 120;
	    private static final int SWIPE_MAX_OFF_PATH = 250;
	    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
		
	    @Override
	    public boolean onFling(MotionEvent e1, MotionEvent e2,
	                         float velocityX, float velocityY) {
	    	try {
	    		final float xDistance = e1.getX() - e2.getX();
	    		final float yDistance = Math.abs(e1.getY() - e2.getY());

	            if (yDistance > SWIPE_MAX_OFF_PATH)
	            	return false;
	           
	            // Left swipe
	            if (xDistance > SWIPE_MIN_DISTANCE
	            && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	            	MainActivity.this.onLeftSwipe();
	            }
	            
	            // Right swipe
	            else if (-xDistance > SWIPE_MIN_DISTANCE
	            && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	            	MainActivity.this.onRightSwipe();
	            }
	    	} 
	    	catch (Exception e) {
	    		Log.e("Asg5", "Error on gestures");
	    	}
	    	return false;
	    }
	}
}
