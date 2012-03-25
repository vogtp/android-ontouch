package ch.alman.android.onetouch.access;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import ch.alman.android.onetouch.db.DB.Alarm;
import ch.alman.android.onetouch.log.Logger;
import ch.almana.spectrum.rest.model.GenericModel;
import ch.almana.spectrum.rest.model.SpectrumAttibute;

public class AlarmDBAccess {

	protected final Context ctx;
	private final ContentResolver resolver;

	public AlarmDBAccess(Context ctx) {
		super();
		this.ctx = ctx.getApplicationContext();
		resolver = ctx.getContentResolver();
	}

	/**
	 * Updates the existing alarms with a new timestamp, deletes the old alarms
	 * and returns a list of new alarms
	 * 
	 * @param alarmIds
	 *            Alarms in spectrum
	 * @param updateTime
	 *            time of current update
	 * @return alarms in spectrum but not in onetouch
	 */
	public Set<String> updateCurrentAlarms(Set<String> alarmIds, long updateTime) {
		Set<String> newAlarms = new TreeSet<String>();
		for (String alarmId : alarmIds) {
			Cursor c = null;
			try {
				c = resolver.query(Alarm.CONTENT_URI, Alarm.PROJECTION_UID, Alarm.SELECTION_BY_UID, new String[] { alarmId }, Alarm.SORTORDER_DEFAULT);
				if (c.moveToFirst()) {
					ContentValues values = new ContentValues();
					values.put(Alarm.NAME_ALARM_UID, alarmId);
					values.put(Alarm.NAME_UPDATE_TIME, updateTime);
					resolver.update(Alarm.CONTENT_URI, values, Alarm.SELECTION_BY_UID, new String[] { alarmId });
				} else {
					newAlarms.add(alarmId);
				}
			} finally {
				if (c != null) {
					c.close();
				}
			}
		}
		deleteOld(updateTime);
		return newAlarms;
	}

	public void insertNewAlarms(Set<String> newAlarmIds, Map<String, GenericModel> newAlarms, long updateTime) {
		for (String id : newAlarmIds) {
			GenericModel genericModel = newAlarms.get(id);
			if (genericModel != null) {
				Map<String, String> attrs = genericModel.getAttributes();

				ContentValues values = new ContentValues();
				values.put(Alarm.NAME_ALARM_UID, id);
				values.put(Alarm.NAME_SERVERITY, Integer.parseInt(attrs.get(SpectrumAttibute.SEVERITY)));
				values.put(Alarm.NAME_OCCURENCES, Long.parseLong(attrs.get(SpectrumAttibute.OCCURENCES)));
				values.put(Alarm.NAME_ACKNOWLEDGED, Boolean.parseBoolean(attrs.get(SpectrumAttibute.ACKNOWLEDGED)) ? 1 : 0);
				values.put(Alarm.NAME_TITLE, attrs.get(SpectrumAttibute.ALARM_TITLE));
				values.put(Alarm.NAME_CREATION_DATE, Long.parseLong(attrs.get(SpectrumAttibute.CREATION_DATE)));
				values.put(Alarm.NAME_MODEL_NAME, attrs.get(SpectrumAttibute.MODEL_NAME));
				values.put(Alarm.NAME_NETWORK_ADDRESS, attrs.get(SpectrumAttibute.NETWORK_ADDRESS));
				values.put(Alarm.NAME_UPDATE_TIME, Long.toString(updateTime));

				resolver.insert(Alarm.CONTENT_URI, values);
			}
		}
	}

	protected void updateAlarmInDb(JSONObject alarm) {
		try {
			String alarmId = alarm.getString("@id"); // string
			//			Map<String, String> attrs = parseAttributes(alarm);

			ContentValues values = new ContentValues();
			values.put(Alarm.NAME_ALARM_UID, alarmId);
			//			values.put(Alarm.NAME_SERVERITY, Integer.parseInt(attrs.get(SpectrumAttibute.SEVERITY)));
			//			values.put(Alarm.NAME_OCCURENCES, Long.parseLong(attrs.get(SpectrumAttibute.OCCURENCES)));
			//			values.put(Alarm.NAME_ACKNOWLEDGED, Boolean.parseBoolean(attrs.get(SpectrumAttibute.ACKNOWLEDGED)) ? 1 : 0);
			//			values.put(Alarm.NAME_TITLE, attrs.get(SpectrumAttibute.ALARM_TITLE));
			//			values.put(Alarm.NAME_CREATION_DATE, Long.parseLong(attrs.get(SpectrumAttibute.CREATION_DATE)));
			//			values.put(Alarm.NAME_MODEL_NAME, attrs.get(SpectrumAttibute.MODEL_NAME));
			//			values.put(Alarm.NAME_NETWORK_ADDRESS, attrs.get(SpectrumAttibute.NETWORK_ADDRESS));
			//			values.put(Alarm.NAME_UPDATE_TIME, updateTime);

			resolver.update(Alarm.CONTENT_URI, values, Alarm.SELECTION_BY_UID, new String[] { alarmId });
		} catch (JSONException e) {
			Logger.e("Cannot add alarm to DB", e);
		}
	}

	private void deleteOld(long updateTime) {
		resolver.delete(Alarm.CONTENT_URI, Alarm.SELECTION_UPDATE_TIME, new String[] { Long.toString(updateTime) });
	}

}