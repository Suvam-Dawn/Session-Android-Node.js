package com.suvamdawn.session;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.suvamdawn.session.helper.Cookie;
import com.suvamdawn.session.helper.GlobalApplication;
import com.suvamdawn.session.helper.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView detailName,sid;
    Button setSession,get;
    EditText userName;
    public static String baseURL="http://192.168.0.101:3000";
    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Cookie(MainActivity.this);
        userName=findViewById(R.id.userName);
        setSession=findViewById(R.id.setSession);
        get=findViewById(R.id.get);
        detailName=findViewById(R.id.detailName);
        sid=findViewById(R.id.sid);
        setSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, baseURL+"/setSession", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SessionResponse:",response);
                        try {
                            JSONObject res=new JSONObject(response);
                            if(res.getString("id")!=""){
                                get.setEnabled(true);
                                setSession.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        return super.getHeaders();
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("name",userName.getText().toString());
                        return params;
                    }
                };
                GlobalApplication.get(MainActivity.this).getRequestQueue().add(stringRequest);
            }
        });
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, baseURL+"/getSession", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject res=new JSONObject(response);
                            if(res.getString("id")!=""){
                                sid.setText("Session ID : "+res.getString("id"));
                            }
                            if(res.getString("name")!=""){
                                detailName.setText("Name : "+res.getString("name"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        return super.getHeaders();
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("name",userName.getText().toString());
                        return params;
                    }
                };
                GlobalApplication.get(MainActivity.this).getRequestQueue().add(stringRequest);
            }
        });
    }
}
