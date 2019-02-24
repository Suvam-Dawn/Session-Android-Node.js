package com.suvamdawn.session.helper;

import android.app.Activity;
import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import java.util.HashMap;

import static com.suvamdawn.session.MainActivity.baseURL;

public class Cookie {
    public Cookie(Context context){
        CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();
        HashMap<String, String> cookieheader = new HashMap<String, String>();
        GlobalApplication.get((Activity) context).addSessionCookie(cookieheader);
        cookieManager.setCookie(baseURL, cookieheader.get(GlobalApplication.get((Activity) context).COOKIE_KEY));
        cookieSyncManager.sync();
    }
}
