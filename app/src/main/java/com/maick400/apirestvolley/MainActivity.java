package com.maick400.apirestvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText txtUser;
    EditText txtPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUser  = (EditText) findViewById(R.id.user);
        txtPass = (EditText) findViewById(R.id.password);
    }
    public void   btnIniciarSesion(View view){


        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.uealecpeterson.net/public/login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                       // textView.setText("Response is: "+ response);
                        try {
                            Bundle  bundle   = new Bundle();
                            Intent intent  = new Intent(MainActivity.this, Home.class);
                            String token;

                            JSONObject json = new JSONObject(response);

                            token = json.getString("access_token");

                            //Toast.makeText(MainActivity.this, token,Toast.LENGTH_LONG).show();

                            bundle.putString("token_access", token);

                            intent.putExtras(bundle);

                            startActivity(intent);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //textView.setText("That didn't work!");
                        Toast.makeText(MainActivity.this,   error.toString(),Toast.LENGTH_LONG).show();

                    }
                }

                ) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> mapita = new HashMap<String, String>();
                mapita.put("correo", txtUser.getText().toString());
                mapita.put("clave", txtPass.getText().toString());
                return mapita;
            }
        };


        queue.add(stringRequest);


    }
}