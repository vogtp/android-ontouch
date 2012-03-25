package ch.alman.android.onetouch.access;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.os.AsyncTask;
import ch.alman.android.onetouch.log.Logger;
import ch.alman.android.onetouch.net.RestfullRequestHandler;
import ch.almana.spectrum.rest.access.AlarmModelAccess;
import ch.almana.spectrum.rest.model.GenericModel;
import ch.almana.spectrum.rest.net.IRequestHandler;

public class AlarmLoaderTask extends AsyncTask {

	public interface AlarmLoaderCallback {

		void showLoadError(String message);

	}

	private final Context ctx;
	private IRequestHandler requestHandler;
	private final AlarmLoaderCallback callback;
	private String errorMessage;

	public AlarmLoaderTask(Context ctx, AlarmLoaderCallback callback) {
		super();
		this.ctx = ctx;
		this.callback = callback;
	}

	@Override
	protected Object doInBackground(Object... params) {
		//		requestHandler = new HttpClientRequestHandler();
		requestHandler = new RestfullRequestHandler();
		AlarmModelAccess modelAccess = new AlarmModelAccess(requestHandler);
		AlarmDBAccess alarmDBAccess = new AlarmDBAccess(ctx);
		errorMessage = null;
		try {
			//DBProvider.setNotifyChange(false);
			Set<String> alarmIds = modelAccess.getList();
			long updateTime = modelAccess.getUpdateTime();
			Set<String> newAlarmIds = alarmDBAccess.updateCurrentAlarms(alarmIds, updateTime);
			//DBProvider.setNotifyChange(true);
			Map<String, GenericModel> newAlarms = modelAccess.getEntities(newAlarmIds);
			alarmDBAccess.insertNewAlarms(newAlarmIds, newAlarms, updateTime);
		} catch (IOException e) {
			Logger.e("Error loading data: ", e);
			errorMessage = e.getMessage();
		} finally {
			//DBProvider.setNotifyChange(true);
			Logger.i("FINISHED UPDATING ALARMS");
		}
		return null;
	}

	@Override
	protected void onPostExecute(Object result) {
		super.onPostExecute(result);
		if (errorMessage != null) {
			callback.showLoadError(errorMessage);
		}
	}
}
