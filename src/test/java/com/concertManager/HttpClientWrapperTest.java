package com.concertManager;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.message.BasicClassicHttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import org.apache.hc.client5.http.classic.methods.HttpPost;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HttpClientWrapperTest {

    @Mock
    private CloseableHttpClient mockHttpClient;

    private HttpClientWrapper httpClientWrapper;

    @BeforeEach
    void setUp() {
        httpClientWrapper = new HttpClientWrapper("http://localhost:8080", mockHttpClient);
    }

    @Test
    void testDoGet_ReturnsNonNull() throws Exception {
        String expectedResponse = "Test response";

        when(mockHttpClient.execute(any(HttpGet.class), any(HttpClientResponseHandler.class)))
                .thenAnswer(invocation -> {
                    // Retrieve the response handler.
                    HttpClientResponseHandler<String> handler =
                            invocation.getArgument(1);
                    // Create a fake response with status 200 and contains expectedResponse.
                    BasicClassicHttpResponse fakeResponse = new BasicClassicHttpResponse(HttpStatus.SC_OK);
                    HttpEntity entity = new StringEntity(expectedResponse, ContentType.TEXT_PLAIN);
                    fakeResponse.setEntity(entity);
                    // Simulate processing the response.
                    return handler.handleResponse(fakeResponse);
                });

        String actualResponse = httpClientWrapper.doGet("/test");

        assertNotNull(actualResponse, "Response from doGet should not be null");
        assertEquals(expectedResponse, actualResponse);

        verify(mockHttpClient).execute(any(HttpGet.class), any(HttpClientResponseHandler.class));
    }

    @Test
    void testDoPost_ReturnsExpectedResponse() throws Exception {
        String expectedResponse = "Post response";

        when(mockHttpClient.execute(any(HttpPost.class), any(HttpClientResponseHandler.class)))
                .thenAnswer(invocation -> {
                    HttpClientResponseHandler<String> handler = invocation.getArgument(1);
                    BasicClassicHttpResponse fakeResponse = new BasicClassicHttpResponse(HttpStatus.SC_OK);
                    HttpEntity entity = new StringEntity(expectedResponse, ContentType.TEXT_PLAIN);
                    fakeResponse.setEntity(entity);
                    return handler.handleResponse(fakeResponse);
                });

        Dummy dummy = new Dummy();
        dummy.setName("testName");

        String actualResponse = httpClientWrapper.doPost("/dummy", dummy);
        assertNotNull(actualResponse, "Response from doPost should not be null");
        assertEquals(expectedResponse, actualResponse);

        verify(mockHttpClient).execute(any(HttpPost.class), any(HttpClientResponseHandler.class));
    }

    static class Dummy {
        private String name;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}
