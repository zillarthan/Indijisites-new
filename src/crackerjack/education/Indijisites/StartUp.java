// I appologise if you are refactoring this or maintaining this or even editing this.
// There is a lot of useless code in here I have done my best to comment it.
// If it's too difficult to manage, restarting and getting to this point should take
// A seasoned programmer 30 minutes to 1 hour
// If you're learning it'll take 2 weeks.


package crackerjack.education.Indijisites;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;




//Begining of marker set methods clean this into one file.
public class StartUp extends FragmentActivity
		implements OnMarkerClickListener, OnInfoWindowClickListener {
	private static final LatLng BRISBANE = new LatLng(-27.47093, 153.0235); //Comment this out once you have a good basis of points
	private static final LatLng MURRAY_RIVER = new LatLng(-30, 135);
	private static final LatLng PURNULULU = new LatLng(-25.3926, 115.8251);
	private static final LatLng SANDY_NATIONAL = new LatLng(-25.3926, 153.0295);
	private static final LatLng RED_HANDS = new LatLng(-33.7672, 150.6218);
	private static final LatLng BIRRIGAI = new LatLng(-35.3081, 149.1244);
	private static final LatLng CHILLAGOE = new LatLng(-17.1553, 144.5243);
	private static final LatLng MTCAMERON = new LatLng(-42, 146.5);
	private static final LatLng INNAMINCKA = new LatLng(-27.7076, 140.7392);
	private static final LatLng NULLPLAIN = new LatLng(-31.6877, 128.8674);
	private static final LatLng ULURU = new LatLng(-25.3517, 131.0307);
	
	/** Information that is displayed when the marker is clicked. */
	
//new code
	
	PopupWindow popUp;
	LinearLayout layout;
	TextView tv;
	LayoutParams params;
	LinearLayout mainLayout;
	Button but;
	boolean click = true;
	
	
	
	class CustomInfoWindowAdapter implements InfoWindowAdapter {
		CustomInfoWindowAdapter() {
			mWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);
			mContents = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
		}
		
		private final View mWindow;
		private final View mContents;
		
		@Override
		public View getInfoContents(Marker marker) {
			render(marker, mContents);
			return mContents;
		}

		@Override
		public View getInfoWindow(Marker marker) {
			render(marker, mWindow);
			return mWindow;
		}
	}
	
	//You must declare all marker locations here
	private GoogleMap mMap;
	private TextView mTopText;
	private Marker mBrisbane, mMurray_River, mPurnululu, mSandy_National, mRed_Hands, mBirrigai,
	mChillagoe,	mMtCameron,	mInnamincka, mNullPlain, mUluru; //Declare similary
	
	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
	}

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }
	
	private void setUpMap() { //This method will initialise all map markers, there will be a lot, this could be refactored
							  // by someone with more time than I.	
		
		// Adds custom markers to the map.
		addMarkersToMap();
		
		// Setting an info window adapter allows us to change the contents and the look of the info window
		// However I like the way it is currently.
		mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
		
		// Set Listeners for marker events.
		mMap.setOnMarkerClickListener(this);
		mMap.setOnInfoWindowClickListener(this);
		
		//Pan to see all markers in view.
		//Cannot zoom to bounds until map has a size.
		final View mapView = getSupportFragmentManager().findFragmentById(R.id.map).getView();
		if (mapView.getViewTreeObserver().isAlive()) {
			mapView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
				@SuppressWarnings({"deprecation" })
				@SuppressLint("NewApi")
				@Override
				public void onGlobalLayout() {
					LatLngBounds bounds = new LatLngBounds.Builder()
					.include(BRISBANE) //To add a new location simply use this line, and add the declared location below above build.
					.include(MURRAY_RIVER) // Note: You must fill the strings file with the corresponding information about the location. (values > strings.xml)
					.include(PURNULULU)
					.include(SANDY_NATIONAL)
					.include(RED_HANDS)
					.include(BIRRIGAI)
					.include(CHILLAGOE)
					.include(MTCAMERON)
					.include(INNAMINCKA)
					.include(NULLPLAIN)
					.include(ULURU)
					.build();
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
					mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				} else {
					mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				}
				mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
				}
			});
		}
	}
	
	
	//This method is where you will call all the map markers, when you add a new location you should update this accordingly.
	private void addMarkersToMap() {
		//Default Icon
		mBrisbane = mMap.addMarker(new MarkerOptions()
			.position(BRISBANE)
			.title("Brisbane")
			.snippet("population: 2,074,200")); //Begin adding locations after this location.
		
		mMurray_River = mMap.addMarker(new MarkerOptions()
			.position(MURRAY_RIVER)
			.title("Murray River")
			.snippet("Blanchetown, South Australia"));
		
		mSandy_National = mMap.addMarker(new MarkerOptions()
			.position(SANDY_NATIONAL)
			.title("Great Sandy National Park Fraser Island")
			.snippet("Fraser Island, Queensland"));
		
		mRed_Hands = mMap.addMarker(new MarkerOptions()
			.position(RED_HANDS)
			.title("Red Hands Cave, Blue Mountains National Park")
			.snippet("Glenbrook, New South Wales"));
		
		mBirrigai = mMap.addMarker(new MarkerOptions()
			.position(BIRRIGAI)
			.title("Birrigai Rockshelter")
			.snippet("Canberra, Australian Capital Territory"));
		
		mChillagoe = mMap.addMarker(new MarkerOptions()
			.position(CHILLAGOE)
			.title("Chillagoe")
			.snippet("Chillagoe, Queensland"));
		
		mMtCameron = mMap.addMarker(new MarkerOptions()
			.position(MTCAMERON)
			.title("Mount Cameron West")
			.snippet("Mount Cameron West, Tasmania"));
		
		mInnamincka = mMap.addMarker(new MarkerOptions()
			.position(INNAMINCKA)
			.title("Innamincka Stone Quarry Sites")
			.snippet("Innamincka, South Australia"));
		
		mNullPlain = mMap.addMarker(new MarkerOptions()
			.position(NULLPLAIN)
			.title("Nullarbor Plain - Allen's Cave")
			.snippet("Eucla, Western Australia"));
		
		mUluru = mMap.addMarker(new MarkerOptions()
			.position(ULURU)
			.title("Uluru ")
			.snippet("Uluru, Northern Territory"));
	}
	
	
	//renders the markers as advertised
	private void render(Marker marker, View view) {
		int badge;
		if (marker.equals(mBrisbane)) {
			badge = R.drawable.badge_qld;
		} else {
			badge = 0;
		}
		((ImageView) view.findViewById(R.id.badge)).setImageResource(badge);
		
		String title = marker.getTitle();
		TextView titleUi = ((TextView) view.findViewById(R.id.title));
		if (title != null) {
			// Spannable string allows us to edit the formatting of the text.
			SpannableString titleText = new SpannableString(title);
			titleText.setSpan(new ForegroundColorSpan(Color.RED), 0, titleText.length(), 0);
			titleUi.setText(titleText);
		} else {
			titleUi.setText("");
		}
		
		String snippet = marker.getSnippet();
		TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
		if (snippet != null && snippet.length() > 12) {
			SpannableString snippetText = new SpannableString(snippet);
			snippetText.setSpan(new ForegroundColorSpan(Color.BLUE), 0, snippet.length(), 0);
			snippetUi.setText(snippetText);
		} else {
			snippetUi.setText("");			
		}
	}
	
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.markers);
		
		mTopText = (TextView) findViewById(R.id.top_text);
		//new code here - 12.25am
		popUp = new PopupWindow(this);
		layout = new LinearLayout(this);
		mainLayout = new LinearLayout(this);
		tv = new TextView(this);
		
		
		setUpMapIfNeeded();
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start_up, menu);
		return true;
	}
	
	// This class displays the Mandatory Legal Notices in the drop down menu in the top bar or in the settings pane
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_legalnotices:
			String LicenseInfo = GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(getApplicationContext());
			AlertDialog.Builder LicenseDialog = new AlertDialog.Builder(StartUp.this);
			LicenseDialog.setTitle("Legal Notices");
			LicenseDialog.setMessage(LicenseInfo);
			LicenseDialog.show();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onInfoWindowClick(Marker marker) { 
		//Change this to affect what happens when you click an info window.
		//We want this method to bring up more information.
		//Currently it only displays a toast.
		//This method implements a popup dialog for the onInforWindowClick Method. (I hope)
		
		//New code, this code freezes app and phone
		

							popUp.showAtLocation(mainLayout, Gravity.BOTTOM, 10, 10);
							popUp.update(50, 50, 300, 80);
							click = false;

						
						
				params = new LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT);
				layout.setOrientation(LinearLayout.VERTICAL);
				tv.setText("This is a sample window");
				layout.addView(tv, params);
				setContentView(mainLayout);
	}



	@Override
	public boolean onMarkerClick(final Marker marker) {
		// This method makes the marker bounce when you click on it. It's not really needed, but it's a neat feature.
		// Will expand this if offered more time.
		if (marker.equals(mBrisbane)) {
			final Handler handler = new Handler();
			final long start = SystemClock.uptimeMillis();
			Projection proj = mMap.getProjection();
			Point startPoint = proj.toScreenLocation(BRISBANE);
			startPoint.offset(0, -100);
			final LatLng startLatLng = proj.fromScreenLocation(startPoint);
			final long duration = 1500;
			
			final Interpolator interpolator = new BounceInterpolator();
			
			handler.post(new Runnable() {
				@Override
				public void run() {
					long elapsed = SystemClock.uptimeMillis() - start;
					float t = interpolator.getInterpolation((float) elapsed / duration);
					double lng = t * BRISBANE.longitude + (1 - t) * startLatLng.longitude; // This allows the marker to pinpoint if offscreen (I think =/)
					double lat = t * BRISBANE.latitude + (1 - t) * startLatLng.latitude;
					marker.setPosition(new LatLng(lat, lng));
					
					if (t < 1.0) {
						// Post again 16ms later
						handler.postDelayed(this, 16);
					}
				}
			});
		}
		//False is returned to indicate we have not consumed the event and that we wish
		//for the default behaviour to occur (move to marker location).
		return false;
	}
}