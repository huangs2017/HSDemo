package util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class SPUtil {

	private static SharedPreferences sp;

	private static void init(Context context) {
		if (sp == null) {
			sp = context.getSharedPreferences("ZYLHsp", Context.MODE_PRIVATE);
		}
	}
	
	public static void putString(Context context, String key, String value) {
		init(context);
		sp.edit().putString(key, value).commit();
	}
	public static String getString(Context context, String key, String value) {
		init(context);
		return sp.getString(key, value);
	}
	
	public static void putInt(Context context, String key, int value) {
		init(context);
		sp.edit().putInt(key, value).commit();
	}
	public static int getInt(Context context, String key, int value) {
		init(context);
		return sp.getInt(key, value);
	}

	public static void putLong(Context context, String key, long value) {
		init(context);
		sp.edit().putLong(key, value).commit();
	}
	public static long getLong(Context context, String key) {
		init(context);
		return sp.getLong(key, 0l);
	}

	public static void putFloat(Context context, String key, float value) {
		init(context);
		sp.edit().putFloat(key, value).commit();
	}
	public static Float getFloat(Context context, String key) {
		init(context);
		return sp.getFloat(key, 0f);
	}
	
	public static void putBoolean(Context context, String key, boolean value) {
		init(context);
		sp.edit().putBoolean(key, value).commit();
	}
	public static Boolean getBoolean(Context context, String key, boolean value) {
		init(context);
		return sp.getBoolean(key, value);
	}
	
	@SuppressLint("NewApi") 
	public static void putStringSet(Context context, String key, Set<String> value) {
		init(context);
		sp.edit().putStringSet(key, value).commit();
	}
	@SuppressLint("NewApi") 
	public static Set<String> getStringSet(Context context, String key) {
		init(context);
		return sp.getStringSet(key, null);
	}
	
	public static boolean remove(Context context, String key) {
		init(context);
		return sp.edit().remove(key).commit();
	}

}
