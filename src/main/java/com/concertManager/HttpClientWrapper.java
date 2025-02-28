package com.concertManager;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.IOException;

public class HttpClientWrapper {

    private final String baseUrl;
    private final CloseableHttpClient httpClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

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

    public String doPost(String path, Object body) {
        String url = baseUrl + path;
        try {
            // Convert body object to JSON string
            String jsonBody = objectMapper.writeValueAsString(body);
            HttpPost request = new HttpPost(url);
            request.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));

            HttpClientResponseHandler<String> handler = response ->
                    EntityUtils.toString(response.getEntity());
            return httpClient.execute(request, handler);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
