package com.concertManager;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;

public class HttpClientWrapper {

    private final String baseUrl;
    private final CloseableHttpClient httpClient;

    // Injection of a mocked HttpClient.
    public HttpClientWrapper(String baseUrl, CloseableHttpClient httpClient) {
        this.baseUrl = baseUrl;
        this.httpClient = httpClient;
    }

    public String doGet(String path) {
        String url = baseUrl + path;
        try {
            HttpGet request = new HttpGet(url);
            return httpClient.execute(request, response ->
                    EntityUtils.toString(response.getEntity())
            );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
