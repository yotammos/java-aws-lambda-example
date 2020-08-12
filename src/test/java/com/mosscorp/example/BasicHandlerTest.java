package com.mosscorp.example;

import com.amazonaws.services.lambda.runtime.Context;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicHandlerTest {

    @Test
    public void invokeTest() {
        Map<String, String> event = new HashMap<>();
        Context context = new TestContext();
        Handler handler = new Handler();
        String result = handler.handleRequest(event, context);
        assertEquals(result, "200 OK");
    }
}
