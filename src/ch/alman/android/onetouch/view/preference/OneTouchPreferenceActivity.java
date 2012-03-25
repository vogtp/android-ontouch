package ch.alman.android.onetouch.view.preference;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import ch.alman.android.onetouch.R;

public class OneTouchPreferenceActivity extends PreferenceActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
		addPreferencesFromResource(R.xml.onetouch_preferences);
	}

}
