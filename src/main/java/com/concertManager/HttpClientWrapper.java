package com.concertManager;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.IOException;

public class HttpClientWrapper {

    private final String baseUrl;
    private final CloseableHttpClient httpClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public HttpClientWrapper(String baseUrl, CloseableHttpClient httpClient) {
        this.baseUrl = baseUrl;
        this.httpClient = httpClient;
    }

    public String doGet(String endpoint) throws IOException {
        HttpGet request = new HttpGet(baseUrl + endpoint);
        return httpClient.execute(request, response -> {
            int statusCode = response.getCode();
            if (statusCode >= 400) {
                System.err.println("Error from server. Status code: " + statusCode);
                return "[]";
            }

            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return "[]";
            }
            return EntityUtils.toString(entity);
        });
    }

    public String doPost(String path, Object body) {
        String url = baseUrl + path;
        try {
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
