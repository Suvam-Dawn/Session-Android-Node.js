package com.suvamdawn.session.helper;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.nsd.NsdServiceInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GlobalApplication {
    public final String SET_COOKIE_KEY = "Set-Cookie";
    public final String COOKIE_KEY = "Cookie";
    public final String SESSION_COOKIE = "connect.sid";
    private static GlobalApplication _instance;
    private RequestQueue _requestQueue;
    private SharedPreferences _preferences;
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    public synchronized static GlobalApplication get(Activity activity) {
        if (_instance == null) {
            _instance = new GlobalApplication(activity);
        }
        return _instance;
    }
    List<NsdServiceInfo> connectedServices;
    private GlobalApplication(Activity activity) {
        _instance = this;
        _preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        _requestQueue = Volley.newRequestQueue(activity.getApplicationContext());
        connectedServices = new ArrayList<>();

    }
    public RequestQueue getRequestQueue() {
        return _requestQueue;
    }
    public final void checkSessionCookie(Map<String, String> headers) {
        Log.d("TAG", "checkSessionCookie: " + headers);
        if (headers.containsKey(SET_COOKIE_KEY)
                && headers.get(SET_COOKIE_KEY).startsWith(SESSION_COOKIE)) {
            String cookie = headers.get(SET_COOKIE_KEY);
            if (cookie.length() > 0) {
                String[] splitCookie = cookie.split(";");
                String[] splitSessionId = splitCookie[0].split("=");
                cookie = splitSessionId[1];
                SharedPreferences.Editor prefEditor = _preferences.edit();
                prefEditor.putString(SESSION_COOKIE, cookie);
                prefEditor.apply();
            }
        }
    }
    public final void addSessionCookie(Map<String, String> headers) {
        String sessionId = _preferences.getString(SESSION_COOKIE, "");
        Log.d("TAG", "addSessionCookie: " + headers);
        if (sessionId.length() > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append(SESSION_COOKIE);
            builder.append("=");
            builder.append(sessionId);
            if (headers.containsKey(COOKIE_KEY)) {
                builder.append("; ");
                builder.append(headers.get(COOKIE_KEY));
            }
            headers.put(COOKIE_KEY, builder.toString());
        }
    }
}