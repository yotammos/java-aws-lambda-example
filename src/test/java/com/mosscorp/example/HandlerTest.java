package com.mosscorp.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.amazonaws.services.lambda.runtime.Context;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class HandlerTest {

    @Test
    void invokeTest() {
        Map<String, String> event = new HashMap<>();
        Context context = new TestContext();
        Handler handler = new Handler();
        String result = handler.handleRequest(event, context);
        assertEquals(result, "Hello World!");
    }
}
