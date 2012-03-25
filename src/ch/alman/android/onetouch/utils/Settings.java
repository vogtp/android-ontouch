package ch.alman.android.onetouch.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import ch.almana.spectrum.rest.net.IRequestConfig;

public class Settings implements IRequestConfig {

	private static Settings instance;


	public static Settings getInstance(Context ctx) {
		if (instance == null) {
			instance = new Settings(ctx.getApplicationContext());
		}
		return instance;
	}

	public static Settings getInstance() {
		return instance;
	}

	private final Context ctx;

	public Settings(Context ctx) {
		super();
		this.ctx = ctx;
	}

	protected SharedPreferences getPreferences() {
		return PreferenceManager.getDefaultSharedPreferences(ctx);
	}

	public String getUsername() {
		return getPreferences().getString("prefKeyUsername", "");
	}

	public String getPassword() {
		return getPreferences().getString("prefKeyPassword", "");
	}

	public String getSpectroServerProtocoll() {
		boolean isSecure = getPreferences().getBoolean("prefKeySecureConnection", true);
		return isSecure ? "https" : "http";
	}

	public String getSpectroServerName() {
		return getPreferences().getString("prefSpectroServerName", "spectrum.urz.unibas.ch"); // FIXME try spectrum.unibas.ch
	}

	public String getSpectroServerUrlPath() {
		return getPreferences().getString("prefKeySpectumBasePath", "spectrum");
	}

	public int getThrottlesize() {
		// TODO Auto-generated method stub
		return -1;
	}

}
