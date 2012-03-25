package ch.alman.android.onetouch.view.activity;

import java.util.Date;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;
import ch.alman.android.onetouch.R;
import ch.alman.android.onetouch.access.AlarmLoaderTask;
import ch.alman.android.onetouch.access.AlarmLoaderTask.AlarmLoaderCallback;
import ch.alman.android.onetouch.db.DB.Alarm;
import ch.alman.android.onetouch.log.Logger;
import ch.alman.android.onetouch.utils.Settings;
import ch.alman.android.onetouch.view.preference.OneTouchPreferenceActivity;

public class OneTouchActivity extends ListActivity implements AlarmLoaderCallback {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Settings setting = Settings.getInstance(this);// initialise
		final java.text.DateFormat dateFormat = DateFormat.getDateFormat(this);
		final java.text.DateFormat timeFormat = DateFormat.getTimeFormat(this);

		AlarmLoaderTask alt = new AlarmLoaderTask(this, this);
		alt.execute();

		Cursor c = managedQuery(Alarm.CONTENT_URI, Alarm.PROJECTION_DEFAULT, Alarm.SELECTION_BY_SEVERITY, new String[] { "1" }, Alarm.SORTORDER_DEFAULT);
		String[] from = new String[] { Alarm.NAME_TITLE, Alarm.NAME_MODEL_NAME };
		int[] to = new int[] { android.R.id.text1, android.R.id.text2 };
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, c, from, to);
		getListView().setAdapter(adapter);
		adapter.setViewBinder(new ViewBinder() {

			public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
				if (columnIndex == Alarm.INDEX_MODEL_NAME) {
					StringBuilder sb = new StringBuilder();
					sb.append(cursor.getString(Alarm.INDEX_MODEL_NAME)).append("\n");
					sb.append("Severity: ").append(cursor.getString(Alarm.INDEX_SERVERITY)).append("\n");
					long updateTime = cursor.getLong(Alarm.INDEX_UPDATE_TIME);
					sb.append("Last updated: ").append(dateFormat.format(new Date(updateTime)));
					sb.append(" - ").append(timeFormat.format(new Date(updateTime))).append("\n");
					((TextView) view).setText(sb.toString());
					return true;
				}
				return false;
			}
		});

    }

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		//		return pagerAdapter.onPrepareOptionsMenu(menu);
		menu.clear();
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.gerneral_options_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.itemSettings:
			Intent i = new Intent(this, OneTouchPreferenceActivity.class);
			startActivity(i);
			return true;

		case R.id.itemRefresh:
			Logger.notImplemented(this);
			return true;
		default:
			return false;

		}
	}

	public void showLoadError(String message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
}