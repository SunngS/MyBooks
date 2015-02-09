package com.example.alex.mybooks.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.alex.mybooks.entities.LoginResult;
import com.example.alex.mybooks.entities.User;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by v0cn115 on 2015/1/22.
 */
public class NetworkStorageHelper {

    private Context callContext;
    private final String SERVER_URL = "http://10.0.2.2:20000";

    public NetworkStorageHelper(Context context){
        this.callContext = context;
    }

    public boolean IsNetworkAvailable(){
        boolean isAvailable =false;
        ConnectivityManager connManager = (ConnectivityManager)callContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkState = connManager.getActiveNetworkInfo();
        if(networkState != null && networkState.isConnected()){
            isAvailable = true;
        }
        return isAvailable;
    }

    public LoginResult AuthenticateUser(User user){
        InputStream stream = null;
        LoginResult result = null;
        XmlParseHelper xmlParseHelper = null;
        try {
            String authenticateUserUrl = SERVER_URL + "/user/login";
            URL serverUrl = new URL(authenticateUserUrl);
            HttpURLConnection conn = (HttpURLConnection)serverUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(50000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            List<NameValuePair> postData = new ArrayList<NameValuePair>();
            postData.add(new BasicNameValuePair("username",user.getUsername()));
            postData.add(new BasicNameValuePair("password",user.getPassword()));
            OutputStream postDataOutputStream = conn.getOutputStream();
            BufferedWriter postDataOutputWriter = new BufferedWriter(
                    new OutputStreamWriter(postDataOutputStream, "UTF-8"));
            postDataOutputWriter.write(CreatePostString(postData));
            postDataOutputWriter.flush();
            postDataOutputWriter.close();
            postDataOutputStream.close();
            conn.connect();
            int responseCode = conn.getResponseCode();
            if(responseCode != 200){
                throw new Exception("Failed to connect to server for authentication");
            }
            stream = conn.getInputStream();

            xmlParseHelper = new XmlParseHelper();
            result = xmlParseHelper.ParseLoginResultXml(stream);

        } catch (Exception e) {
            e.printStackTrace();
            result = null;
        }
        finally {
         if (stream != null){
             try {
                 stream.close();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
        }
        return result;
    }

    private String CreatePostString(List<NameValuePair> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }

}
