package adv.brand.com.lavanya.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;

import java.util.UUID;

public class PrefHandler {
	Context mContext;
	SharedPreferences preferences;

	public PrefHandler(Context appContext) {
		mContext = appContext;
		preferences = appContext.getSharedPreferences("Brand_Prefs",
				Context.MODE_PRIVATE);

	}

	public int getInt(String key) {
		return preferences.getInt(key, 0);
	}

	public long getLong(String key) {
		return preferences.getLong(key, 0l);
	}

	public String getString(String key) {
		return preferences.getString(key, "");
	}

	public double getDouble(String key) {
		String number = getString(key);
		try {
			double value = Double.parseDouble(number);
			return value;
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public void putInt(String key, int value) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(key, value);
		editor.apply();
	}

	public void putLong(String key, long value) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putLong(key, value);
		editor.apply();
	}

	public void putDouble(String key, double value) {
		putString(key, String.valueOf(value));
	}

	public void putString(String key, String value) {

		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.apply();
	}

	/*public void putEncryptedString(String key, String value) {
		

			SharedPreferences.Editor editor = preferences.edit();
			try {
			editor.putString(key,DESEncryptor.encrypt(getPsuedoUniqueID(), value));
			editor.apply();
		} catch (Exception e) {
			// TODO: handle exception
			editor.putString(key, value);
			editor.apply();
		}
		
	}*/
	
	/*public String getDecryptedString(String key) {
		

		try {
			if (!TextUtils.isEmpty(preferences.getString(key, ""))) {
				return DESEncryptor.decrypt(getPsuedoUniqueID(),
						preferences.getString(key, ""));
			}else{
				return "";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";

	
}*/

	public void putBoolean(String key, boolean value) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(key, value);
		editor.apply();
	}

	public boolean getBoolean(String key) {
		return preferences.getBoolean(key, false);
	}

	public void putFloat(String key, float value) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putFloat(key, value);
		editor.apply();
	}

	public float getFloat(String key) {
		return preferences.getFloat(key, 0f);
	}

	public void remove(String key) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.remove(key);
		editor.apply();
	}

	public void clear() {
		SharedPreferences.Editor editor = preferences.edit();
		editor.clear();
		editor.apply();
	}

	
	public  void clearPrefs(){
		SharedPreferences.Editor editor = preferences.edit();
		editor.clear() ;
		editor.apply() ;
	}
}
