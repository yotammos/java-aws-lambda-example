package com.mosscorp.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class HandlerStream implements RequestStreamHandler {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        LambdaLogger logger = context.getLogger();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.US_ASCII));
        PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.US_ASCII)));
        try {
            HashMap event = gson.fromJson(reader, HashMap.class);
            logger.log("STREAM TYPE: " + input.getClass());
            logger.log("EVENT TYPE: " + event.getClass());
            writer.write(gson.toJson(event));
            if (writer.checkError()) {
                logger.log("WARNING: Writer encountered an error.");
            }
        } catch (IllegalStateException | JsonSyntaxException exception) {
            logger.log(exception.toString());
        } finally {
            reader.close();
            writer.close();
        }
    }
}
