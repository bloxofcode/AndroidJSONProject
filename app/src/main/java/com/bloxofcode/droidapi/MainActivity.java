package com.bloxofcode.droidapi;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bloxofcode.droidapi.Model.HttpCall;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    EditText etHostName;
    Button btn,btnPost;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = (TextView) findViewById(R.id.textView);
        etHostName = (EditText) findViewById(R.id.etHostName);
        btn = (Button) findViewById(R.id.button);
        btnPost = (Button) findViewById(R.id.btnPost);
        pDialog = new ProgressDialog(this);


    }

    public void getData(View v){
        HttpCall httpCall = new HttpCall();
        httpCall.setMethodType(HttpCall.GET);
        httpCall.setUrl("http://192.168.1.92:8089/restphp/index.php/sampleapi/testerinfo_all");
        HashMap<String, String> params = new HashMap<>();
        params.put("name","John Doe");
        httpCall.setParams(params);
        new HttpRequest(){
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                textView.setText(response);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog.setMessage("Getting API");
                pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pDialog.setIndeterminate(true);
                pDialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pDialog.dismiss();
            }
        }.execute(httpCall);
    }

    public void postData(View v){
        HttpCall httpCallPost = new HttpCall();
        httpCallPost.setMethodType(HttpCall.POST);
        httpCallPost.setUrl("http://192.168.1.92:8089/restphp/index.php/sampleapi/hostname");
        HashMap<String, String> paramPost = new HashMap<>();
        paramPost.put("hostname",etHostName.getText().toString());
        httpCallPost.setParams(paramPost);
        new HttpRequest(){
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                textView.setText(response);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog.setMessage("Posting to API");
                pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pDialog.setIndeterminate(true);
                pDialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pDialog.dismiss();
            }
        }.execute(httpCallPost);
    }
}
