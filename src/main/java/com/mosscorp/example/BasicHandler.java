package com.mosscorp.example;

import com.amazonaws.services.lambda.runtime.Context;
import software.amazon.awssdk.services.lambda.LambdaAsyncClient;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.lambda.model.GetAccountSettingsRequest;
import software.amazon.awssdk.services.lambda.model.GetAccountSettingsResponse;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class BasicHandler implements RequestHandler<Map<String, String>, String> {
    private static final Logger logger = LoggerFactory.getLogger(BasicHandler.class);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final LambdaAsyncClient lambdaClient = LambdaAsyncClient.create();

    public BasicHandler() {
        CompletableFuture<GetAccountSettingsResponse> accountSettings = lambdaClient.getAccountSettings(
                GetAccountSettingsRequest.builder().build()
        );
    }

    @Override
    public String handleRequest(Map<String, String> event, Context context) {
        String response = "200 OK";

        logger.info("Getting account settings");
        CompletableFuture<GetAccountSettingsResponse> accountSettings =
                lambdaClient.getAccountSettings(GetAccountSettingsRequest.builder().build());

        logger.info("ENV: " + gson.toJson(System.getenv()));
        logger.info("CONTEXT: " + gson.toJson(context));

        logger.info("EVENT: " + gson.toJson(event));
        logger.info("EVENT TYPE: " + event.getClass());

        try {
            GetAccountSettingsResponse settings = accountSettings.get();
            response = gson.toJson(settings.accountUsage());
            logger.info("Account usage: {}", response);
        } catch (Exception e) {
            e.getStackTrace();
        }

        return response;
    }
}
