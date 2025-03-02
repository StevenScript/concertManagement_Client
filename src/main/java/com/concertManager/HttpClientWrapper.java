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

    // Injection of a mocked HttpClient.
    public HttpClientWrapper(String baseUrl, CloseableHttpClient httpClient) {
        this.baseUrl = baseUrl;
        this.httpClient = httpClient;
    }

    public String doGet(String endpoint) throws IOException {
        HttpGet request = new HttpGet(baseUrl + endpoint);
        return httpClient.execute(request, response -> {
            int statusCode = response.getCode();
            // If needed, handle 404 or 500, etc.:
            if (statusCode >= 400) {
                // Optionally throw or return some fallback
                System.err.println("Error from server. Status code: " + statusCode);
                return "[]"; // or throw new RuntimeException("Something bad happened");
            }

            // Check if there's an entity
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                // Return empty string or handle how you like
                return "[]";
            }
            // Otherwise parse
            return EntityUtils.toString(entity);
        });
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
