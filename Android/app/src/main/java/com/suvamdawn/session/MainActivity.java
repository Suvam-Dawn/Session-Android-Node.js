package com.suvamdawn.session;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView detailName,id;
    Button setSession,get;
    EditText userName;
    public static String baseURL="http://192.168.0.101:3000";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName=findViewById(R.id.userName);
        setSession=findViewById(R.id.setSession);
        get=findViewById(R.id.get);
        detailName=findViewById(R.id.userName);
        id=findViewById(R.id.sid);
        setSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, baseURL+"/setSession", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SessionResponse:",response);
                        try {
                            JSONObject res=new JSONObject(response);
                            Log.d("SessionResponse:",res.getString("id"));
                            Log.d("SessionResponse:",res.getString("name"));
                            if(res.getString("id")!=""){
                                get.setEnabled(true);
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
                MySingleton.getmInstance(MainActivity.this).addTorequestque(stringRequest);
            }
        });
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, baseURL+"/getSession", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SessionResponse:",response);
                        try {
                            JSONObject res=new JSONObject(response);
                            Log.d("SessionResponse:",res.getString("id"));
                            Log.d("SessionResponse:",res.getString("name"));
                            if(res.getString("id")!=""){
                                id.setText(res.getString("id"));
                            }
                            if(res.getString("name")!=""){
                                detailName.setText(res.getString("name"));
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
                MySingleton.getmInstance(MainActivity.this).addTorequestque(stringRequest);
            }
        });
    }
}
