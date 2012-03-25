package ch.alman.android.onetouch.db;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import ch.alman.android.onetouch.db.DBProvider.UriTableMapping;
import ch.alman.android.onetouch.log.Logger;

public interface DB {

	public static final String AUTHORITY = "ch.alman.android.onetouch";
	public static final String DATABASE_NAME = "spectrumCache";

	public static final String NAME_ID = "_id";
	public static final int INDEX_ID = 0;

	public static final String[] PROJECTION_ID = new String[] { NAME_ID };
	public static final String SELECTION_BY_ID = NAME_ID + "=?";

	public class UriTableConfig {

		public static Map<Integer, UriTableMapping> map;

		private static final int ALARM = 1;

		static {
			map = new HashMap<Integer, UriTableMapping>();
			map.put(ALARM, Alarm.URI_TABLE_MAPPING);
		}

	}

	public class OpenHelper extends SQLiteOpenHelper {

		private static final int DATABASE_VERSION = 2;

		private static final String CREATE_ALARMS_TABLE = "create table if not exists " + Alarm.TABLE_NAME + " (" + DB.NAME_ID
				+ " integer primary key, "
				+ Alarm.NAME_SERVERITY + " int," + Alarm.NAME_OCCURENCES + " long, "
				+ Alarm.NAME_ACKNOWLEDGED + " int," + Alarm.NAME_TITLE + " text, "
				+ Alarm.NAME_CREATION_DATE + " long," + Alarm.NAME_MODEL_NAME + " text, " + Alarm.NAME_NETWORK_ADDRESS + " text, "
				+ Alarm.NAME_UPDATE_TIME + " long, "
				+ Alarm.NAME_ALARM_UID + " text);";


		public OpenHelper(Context context) {
			super(context, DB.DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_ALARMS_TABLE);
			db.execSQL("create index alarm_severity_idx on " + Alarm.TABLE_NAME + " (" + Alarm.NAME_SERVERITY + "); ");
			db.execSQL("create index alarm_date_idx on " + Alarm.TABLE_NAME + " (" + Alarm.NAME_CREATION_DATE + "); ");
			db.execSQL("create index alarm_date_idx on " + Alarm.TABLE_NAME + " (" + Alarm.NAME_ALARM_UID + "); ");
			//			db.execSQL("create unique index dayref_idx on " + Days.TABLE_NAME + " (" + Days.NAME_DAYREF + "); ");
			//			db.execSQL("create index ts_dayref_idx on " + Timestamps.TABLE_NAME + " (" + Timestamps.NAME_DAYREF + "); ");
			//			db.execSQL("create unique index months_monthref_idx on " + Months.TABLE_NAME + " (" + Months.NAME_MONTHREF + "); ");
			//			db.execSQL("create index days_monthref_idx on " + Days.TABLE_NAME + " (" + Days.NAME_MONTHREF + "); ");
			//			db.execSQL("create index days_weekref_idx on " + Days.TABLE_NAME + " (" + Days.NAME_WEEKREF + "); ");
			//			db.execSQL("create unique index weeks_weekref_idx on " + Weeks.TABLE_NAME + " (" + Weeks.NAME_WEEKREF + "); ");
			//			db.execSQL("create unique index timeoff_start_idx on " + Holidays.TABLE_NAME + " (" + Holidays.NAME_START + "); ");
			//			db.execSQL("create unique index timeoff_end_idx on " + Holidays.TABLE_NAME + " (" + Holidays.NAME_END + "); ");
			Logger.i("Created tables");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			switch (oldVersion) {
			case 1:
				Logger.w("Upgrading to DB Version 2...");
				db.execSQL("alter table " + Alarm.TABLE_NAME + " add column " + Alarm.NAME_ALARM_UID + " text;");
				// nobreak

			default:
				Logger.w("Finished DB upgrading!");
				break;
			}
		}

	}

	public interface Alarm {

		static final String TABLE_NAME = "alarm";
		public static final String CONTENT_ITEM_NAME = "alarm";

		public static final String NAME_SERVERITY = "SERVERITY";
		public static final String NAME_OCCURENCES = "OCCURENCES";
		public static final String NAME_ACKNOWLEDGED = "ACKNOWLEDGED";
		public static final String NAME_TITLE = "TITLE";
		public static final String NAME_CREATION_DATE = "CREATION_DATE";
		public static final String NAME_MODEL_NAME = "MODEL_NAME";
		public static final String NAME_NETWORK_ADDRESS = "NETWORK_ADDRESS";
		public static final String NAME_UPDATE_TIME = "UPDATE_TIME";
		public static final String NAME_ALARM_UID = "ALARM_UID";

		public static final int INDEX_SERVERITY = 1;
		public static final int INDEX_OCCURENCES = 2;
		public static final int INDEX_ACKNOWLEDGED = 3;
		public static final int INDEX_TITLE = 4;
		public static final int INDEX_DATE = 5;
		public static final int INDEX_MODEL_NAME = 6;
		public static final int INDEX_NETWORK_ADDRESS = 7;
		public static final int INDEX_UPDATE_TIME = 8;
		public static final int INDEX_ALARM_UID = 9;

		public static final String[] colNames = new String[] { NAME_ID, NAME_SERVERITY, NAME_OCCURENCES, NAME_ACKNOWLEDGED, NAME_TITLE, NAME_CREATION_DATE, NAME_MODEL_NAME,
				NAME_NETWORK_ADDRESS, NAME_UPDATE_TIME, NAME_ALARM_UID };
		public static final String[] PROJECTION_DEFAULT = colNames;
		public static final String[] PROJECTION_UID = new String[] { NAME_ALARM_UID };

		public static final String SORTORDER_DEFAULT = NAME_SERVERITY + " DESC";
		public static final String SORTORDER_REVERSE = NAME_SERVERITY + " ASC";

		static final String SELECTION_UPDATE_TIME = NAME_UPDATE_TIME + "<?";
		static final String SELECTION_BY_SEVERITY = NAME_SERVERITY + ">=?";
		static final String SELECTION_BY_UID = NAME_ALARM_UID + "=?";

		public static final String CONTENT_URI_STRING = "content://" + AUTHORITY + "/" + CONTENT_ITEM_NAME;
		public static final Uri CONTENT_URI = Uri.parse(CONTENT_URI_STRING);
		static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + AUTHORITY + "." + CONTENT_ITEM_NAME;
		static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + AUTHORITY + "." + CONTENT_ITEM_NAME;
		public static final UriTableMapping URI_TABLE_MAPPING = new UriTableMapping(CONTENT_URI, TABLE_NAME, CONTENT_ITEM_NAME, CONTENT_TYPE, CONTENT_ITEM_TYPE);


	}

}