package com.concertManager;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HttpClientWrapperTest {

    @Test
    void testDoGet_ReturnsNonNull() {
        // Expect calling doGet with a valid path returns a non-null string.
        HttpClientWrapper client = new HttpClientWrapper("http://localhost:8080");
        String response = client.doGet("/test");
        assertNotNull(response, "Response from doGet should not be null");
    }
}
