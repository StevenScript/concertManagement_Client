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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.core5.http.io.entity.EntityUtils;


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
        // Create instance with the mock HttpClient.
        httpClientWrapper = new HttpClientWrapper("http://localhost:8080", mockHttpClient);
    }

    @Test
    void testDoGet_ReturnsNonNull() throws Exception {
        // Expected response from fake HTTP call.
        String expectedResponse = "Test response";

        // Stub the execute method: when any HttpGet and any HttpClientResponseHandler is passed,
        // then invoke the handler with a fake HTTP response containing the expected response.
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

        // Call the doGet() method.
        String actualResponse = httpClientWrapper.doGet("/test");

        // Assert that the response is not null and equals the expected value.
        assertNotNull(actualResponse, "Response from doGet should not be null");
        assertEquals(expectedResponse, actualResponse);

        // Verify that the mocks execute method was called.
        verify(mockHttpClient).execute(any(HttpGet.class), any(HttpClientResponseHandler.class));
    }

    @Test
    void testDoPost_ReturnsExpectedResponse() throws Exception {
        String expectedResponse = "Post response";

        // Stub the execute method for HttpPost.
        when(mockHttpClient.execute(any(HttpPost.class), any(HttpClientResponseHandler.class)))
                .thenAnswer(invocation -> {
                    HttpClientResponseHandler<String> handler = invocation.getArgument(1);
                    BasicClassicHttpResponse fakeResponse = new BasicClassicHttpResponse(HttpStatus.SC_OK);
                    HttpEntity entity = new StringEntity(expectedResponse, ContentType.TEXT_PLAIN);
                    fakeResponse.setEntity(entity);
                    return handler.handleResponse(fakeResponse);
                });

        // Dummy object to send in the POST request.
        Dummy dummy = new Dummy();
        dummy.setName("testName");

        String actualResponse = httpClientWrapper.doPost("/dummy", dummy);
        assertNotNull(actualResponse, "Response from doPost should not be null");
        assertEquals(expectedResponse, actualResponse);

        verify(mockHttpClient).execute(any(HttpPost.class), any(HttpClientResponseHandler.class));
    }

    // Dummy class for testing POST body conversion.
    static class Dummy {
        private String name;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}
