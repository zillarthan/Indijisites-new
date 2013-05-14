package crackerjack.education.Indijisites;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.support.v4.app.FragmentActivity;

public class StartUp extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.application_start_up);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start_up, menu);
		return true;
	}
}