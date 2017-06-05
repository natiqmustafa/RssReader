package com.natodriod.kotlin.rssreader.Common;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by natiqmustafa on 05.06.2017.
 */

public class HTTPDataHandler {
    static String stream = "";

    public HTTPDataHandler() {
    }

    public String GetHTTPDataHandler(String urlString)
    {
        try{
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null)
                    sb.append(line);

                stream = sb.toString();
                urlConnection.disconnect();

            }



        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return stream;
    }
}
