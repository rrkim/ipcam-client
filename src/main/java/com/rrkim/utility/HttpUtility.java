package com.rrkim.utility;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpUtility {

    public static HttpURLConnection getHttpUrlConnection(String connectionUrl, String requestMethod) throws IOException {
        URL url = new URL(connectionUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(requestMethod);

        return connection;
    }

    public static String getHttpResponse(HttpURLConnection conn, Map<String, Object> paramMap) throws IOException {
        if (conn == null) { throw new NullPointerException("HttpUrlConnection is null"); }
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        String json = ObjectUtility.convertJson(paramMap);
        try(OutputStream outputStream = conn.getOutputStream()) {
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            outputStream.write(input, 0, input.length);
        }

        StringBuilder sb = new StringBuilder();
        String line = "";

        InputStream inputStream = conn.getInputStream();
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

        conn.connect();
        InputStream inputStream = conn.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int data;
        while ((data = inputStream.read()) != -1) {
            if (data == endChar) { break; }
            byteArrayOutputStream.write(data);
        }

        return byteArrayOutputStream.toString();
    }

}
