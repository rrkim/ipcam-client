package com.rrkim.utility;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtility {

    public static HttpURLConnection getHttpUrlConnection(String connnectionUrl, String requestMethod) throws IOException {
        URL url = new URL(connnectionUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(requestMethod);
        connection.connect();

        return connection;
    }

    public static String getHttpResponse(HttpURLConnection conn) throws IOException {
        if (conn == null) { throw new NullPointerException("HttpUrlConnection is null"); }

        InputStream inputStream = conn.getInputStream();
        if(inputStream == null) { throw new NullPointerException("inputStream is null"); }

        StringBuilder sb = new StringBuilder();
        String line = "";

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        while((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }

        bufferedReader.close();
        inputStreamReader.close();

        return sb.toString();
    }

    public static String getHttpResponse(HttpURLConnection conn, int endChar) throws IOException {
        if (conn == null) { throw new NullPointerException("HttpUrlConnection is null"); }

        InputStream inputStream = conn.getInputStream();
        if(inputStream == null) { throw new NullPointerException("inputStream is null"); }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int data;
        while ((data = inputStream.read()) != -1) {
            if (data == endChar) { break; }
            byteArrayOutputStream.write(data);
        }

        return byteArrayOutputStream.toString();
    }

}
