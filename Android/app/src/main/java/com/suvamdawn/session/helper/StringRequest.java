package com.suvamdawn.session.helper;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StringRequest extends com.android.volley.toolbox.StringRequest {
    private final Map<String, String> _params;
    public StringRequest(int method, String url, Map<String, String> params, Response.Listener<String> listener,
                         Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);

        _params = params;
    }
    public StringRequest(int method, String url, Response.Listener<String> listener,
                         Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);

        _params = null;
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return _params;
    }
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        GlobalApplication.get(null).checkSessionCookie(response.headers);
        return super.parseNetworkResponse(response);
    }
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();
        if (headers == null
                || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }
        GlobalApplication.get(null).addSessionCookie(headers);
        return headers;
    }
}