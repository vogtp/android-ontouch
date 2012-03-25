package ch.alman.android.onetouch.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import ch.alman.android.onetouch.db.DB.OpenHelper;

public class DBProvider extends ContentProvider {

	public static class UriTableMapping {

		public final Uri contenUri;
		public final String tableName;
		public final String contentItemName;
		public final String contentType;
		public final String contentItemType;

		public UriTableMapping(Uri contenUri, String tableName, String contentItemName, String contentType, String contentItemType) {
			super();
			this.contenUri = contenUri;
			this.tableName = tableName;
			this.contentItemName = contentItemName;
			this.contentType = contentType;
			this.contentItemType = contentItemType;
		}
	}


	private static final int CONTENT = 1;
	private static final int CONTENT_ITEM = 2;

	private static final UriMatcher uriTableMatcher;

	private static UriMatcher uriContentTypeMatcher;

	private OpenHelper openHelper;
	private static boolean notifyChange = true;

	@Override
	public boolean onCreate() {
		openHelper = new OpenHelper(getContext());
		return true;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count = 0;
		UriTableMapping utm = getUriTableMap(uri);
		SQLiteDatabase db = openHelper.getWritableDatabase();
		switch (uriContentTypeMatcher.match(uri)) {
		case CONTENT:
			count = db.delete(utm.tableName, selection, selectionArgs);
			break;

		case CONTENT_ITEM:
			String id = uri.getPathSegments().get(1);
			count = db.delete(utm.tableName, DB.NAME_ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		notifyChange(uri);
		return count;
	}

	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		Uri ret;
		// Validate the requested uri
		if (uriContentTypeMatcher.match(uri) != CONTENT) {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		UriTableMapping utm = getUriTableMap(uri);
		ContentValues values;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			values = new ContentValues();
		}
		SQLiteDatabase db = openHelper.getWritableDatabase();
		long rowId = db.insert(utm.tableName, DB.NAME_ID, values);
		if (rowId > 0) {
			ret = ContentUris.withAppendedId(uri, rowId);
		}else {
			throw new SQLException("Failed to insert row into " + uri);
		}

		notifyChange(uri);
		return ret;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		UriTableMapping utm = getUriTableMap(uri);
		qb.setTables(utm.tableName);
		switch (uriContentTypeMatcher.match(uri)) {
		case CONTENT:
			break;

		case CONTENT_ITEM:
			qb.appendWhere(DB.NAME_ID + "=" + uri.getPathSegments().get(1));
			break;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		// Get the database and run the query
		SQLiteDatabase db = openHelper.getReadableDatabase();
		Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		// Tell the cursor what uri to watch, so it knows when its source data
		// changes
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		int count = 0;
		UriTableMapping utm = getUriTableMap(uri);
		SQLiteDatabase db = openHelper.getWritableDatabase();
		switch (uriContentTypeMatcher.match(uri)) {
		case CONTENT:
			count = db.update(utm.tableName, values, selection, selectionArgs);
			break;

		case CONTENT_ITEM:
			String id = uri.getPathSegments().get(1);
			count = db.update(utm.tableName, values, DB.NAME_ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		notifyChange(uri);
		return count;
	}

	private void notifyChange(Uri uri) {
		if (isNotifyChange()) {
			getContext().getContentResolver().notifyChange(uri, null);
		}
	}

	@Override
	public String getType(Uri uri) {
		UriTableMapping uriTableMapping = getUriTableMap(uri);
		if (uriTableMapping == null) {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		switch (uriContentTypeMatcher.match(uri)) {
		case CONTENT:
			return uriTableMapping.contentType;
		case CONTENT_ITEM:
			return uriTableMapping.contentItemType;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	private UriTableMapping getUriTableMap(Uri uri) {
		return DB.UriTableConfig.map.get(uriTableMatcher.match(uri));
	}

	public static boolean isNotifyChange() {
		return notifyChange;
	}

	public static void setNotifyChange(boolean notifyChange) {
		DBProvider.notifyChange = notifyChange;
	}

	static {

		uriTableMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriContentTypeMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		for (Integer type : DB.UriTableConfig.map.keySet()) {
			String contentItemName = DB.UriTableConfig.map.get(type).contentItemName;
			uriTableMatcher.addURI(DB.AUTHORITY, contentItemName, type);
			uriTableMatcher.addURI(DB.AUTHORITY, contentItemName + "/#", type);
			uriContentTypeMatcher.addURI(DB.AUTHORITY, contentItemName, CONTENT);
			uriContentTypeMatcher.addURI(DB.AUTHORITY, contentItemName + "/#", CONTENT_ITEM);
		}

	}

}
