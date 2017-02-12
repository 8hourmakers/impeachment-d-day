package com.impeachmenteightdday.impeachmenteightdday.util;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;


public class Screen {
	Context context;

	public Screen( Context context ) {

		this.context = context;
	}

	public int getStatusBarHeight() {

		int result = 0;
		int resourceId = context.getResources().getIdentifier( "status_bar_height", "dimen",
				"android" );
		if ( resourceId > 0 )
			result = context.getResources().getDimensionPixelSize( resourceId );

		return result;
	}

	public Point getNavigationBarSize() {

		Point appUsableSize = getAppUsableScreenSize();
		Point realScreenSize = getRealScreenSize();

		if ( appUsableSize.x < realScreenSize.x ) // navigation bar on the right
			return new Point( realScreenSize.x - appUsableSize.x, appUsableSize.y );
		else if ( appUsableSize.y < realScreenSize.y ) // navigation bar at the bottom
			return new Point( appUsableSize.x, realScreenSize.y - appUsableSize.y );
		else // navigation bar is not present
			return new Point();
	}

	public boolean isNavigationBarOnRight() {
		return getNavigationBarOrientation() < 0;
	}

	public boolean isNavigationBarOnBottom() {
		return getNavigationBarOrientation() > 0;
	}

	private int getNavigationBarOrientation() {

		Point appUsableSize = getAppUsableScreenSize();
		Point realScreenSize = getRealScreenSize();

		if ( appUsableSize.x < realScreenSize.x )
			return -1; // navigation bar on the right
		else if ( appUsableSize.y < realScreenSize.y )
			return 1; // navigation bar at the bottom
		else
			return 0;
	}

	@TargetApi( Build.VERSION_CODES.HONEYCOMB_MR2 )
	private Point getAppUsableScreenSize() {

		WindowManager windowManager = (WindowManager) context.getSystemService(
				Context.WINDOW_SERVICE );
		Display display = windowManager.getDefaultDisplay();
		Point size = new Point();

		if ( Build.VERSION_CODES.HONEYCOMB_MR2 <= Build.VERSION.SDK_INT )
			display.getSize( size );

		return size;
	}

	@SuppressWarnings( { "EmptyCatchBlock", "TryWithIdenticalCatches" } )
	private Point getRealScreenSize() {

		WindowManager windowManager = (WindowManager) context.getSystemService(
				Context.WINDOW_SERVICE );
		Display display = windowManager.getDefaultDisplay();
		Point size = new Point();

		if ( Build.VERSION.SDK_INT >= 17 ) {

			display.getRealSize( size );
		}
		else if ( Build.VERSION.SDK_INT >= 14 ) {

			try {

				size.x = (Integer) Display.class.getMethod( "getRawWidth" ).invoke( display );
				size.y = (Integer) Display.class.getMethod( "getRawHeight" ).invoke( display );
			}
			catch ( Exception e ) {

			}
		}

		return size;
	}

	public double dipToPixel( double dip ) {

		return dip * getDisplayMetrics( context ).densityDpi / 160.0;
	}

	private DisplayMetrics getDisplayMetrics( Context context ) {

		return context.getResources().getDisplayMetrics();
	}

}