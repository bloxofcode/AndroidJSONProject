package com.bloxofcode.droidapi;

import android.os.AsyncTask;

import com.bloxofcode.droidapi.Model.HttpCall;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest extends AsyncTask<HttpCall, String, String> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        onResponse(s);
    }

    @Override
    protected String doInBackground(HttpCall... params) {
        HttpURLConnection urlConnection = null;
        HttpCall httpCall = params[0];
        StringBuilder response = new StringBuilder();
        try{
            String dataParams = getDataString(httpCall.getParams(), httpCall.getMethodType());
            URL url = new URL(httpCall.getMethodType() == HttpCall.GET ? httpCall.getUrl()+dataParams:httpCall.getUrl());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(httpCall.getMethodType() == HttpCall.GET ? "GET":"POST");

            //This is for POST Method
            if(httpCall.getParams() != null && httpCall.getMethodType() == httpCall.POST){
                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter bufWriter = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                bufWriter.append(dataParams);
                bufWriter.flush();
                bufWriter.close();
                os.close();
            }

            int responseCode = urlConnection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                String line;
                BufferedReader bufReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                while((line = bufReader.readLine()) != null){
                    response.append(line);
                }
            }
        }catch (UnsupportedEncodingException e){

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    public void onResponse(String response){

    }

    private String getDataString(HashMap<String, String> params, int methodType) throws UnsupportedEncodingException {
        /**
         * This function is for GET result to
         * return the JSONObject in String type.
         */
        StringBuilder result = new StringBuilder();
        boolean isFirst = true;

        for(Map.Entry<String, String> entry : params.entrySet()){
            if(isFirst){
                isFirst = false;
                if(methodType == HttpCall.GET){
                    result.append('?');
                }
            }
            else{
                result.append('&');
            }
            result.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
        }

        return result.toString();


    }
}
