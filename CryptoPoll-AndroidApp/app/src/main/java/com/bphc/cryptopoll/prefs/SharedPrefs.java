package com.bphc.cryptopoll.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {

    private static final String PREFS_NAME = "Shared_prefs";
    private static SharedPreferences _sharedPreferences = null;

    private SharedPrefs() {

    }

    public static SharedPreferences getInstance(Context context) {
        if (_sharedPreferences == null) {
            _sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        }
        return _sharedPreferences;
    }

    public static SharedPreferences.Editor clearPrefsEditor(Context context) {
        return getInstance(context).edit().clear();
    }

    public static void removeKey(Context context, String paramKey) {
        getInstance(context).edit().remove(paramKey).apply();
    }

    public static int getIntParams(Context context, String paramKey) {
        return getInstance(context).getInt(paramKey, -1);
    }

    public static void setIntParams(Context context, String paramKey, int paramValue) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putInt(paramKey, paramValue);
        editor.apply();
    }

    public static String getStringParams(Context context, String paramKey, String defaultValue) {
        return getInstance(context).getString(paramKey, defaultValue);
    }

    public static void setStringParams(Context context, String paramKey, String paramValue) {

        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putString(paramKey, paramValue);
        editor.apply();

    }

    public static boolean getBooleanParams(Context context, String paramKey) {
        return getInstance(context).getBoolean(paramKey, false);
    }

    public static void setBooleanParams(Context context, String paramKey, boolean paramValue) {

        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putBoolean(paramKey, paramValue);
        editor.apply();

    }



}
