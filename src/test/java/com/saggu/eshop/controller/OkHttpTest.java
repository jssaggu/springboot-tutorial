package com.saggu.eshop.controller;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Optional.empty;
import static java.util.concurrent.TimeUnit.SECONDS;

public class OkHttpTest {

    private final OkHttpClient client;

    public OkHttpTest() {
        ConnectionPool connectionPool = new ConnectionPool(10, 100, SECONDS);
        this.client = new OkHttpClient.Builder()
                .connectionPool(connectionPool)
                .callTimeout(4, SECONDS)
//                .readTimeout(20, SECONDS)
//                .connectTimeout(1, SECONDS)
                .build();
    }

    public static void main(String[] args) {
        new OkHttpTest().process();
    }

    void callService(int i) {
        Date start = new Date();
        Request request = new Request.Builder().url("http://localhost:8080/v1/products").build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println("Pass " + i + " " +
                    "Start[" + start + "]" +
                    "End[" + new Date() + "] " +
                    "Code: " + response.code() + "; " +
                    response.isSuccessful() + " " +
                    "ConCount: " + client.connectionPool().connectionCount()
            );
        } catch (IOException e) {
            System.err.println("Pass " + i + " " +
                    "Start[" + start + "]" +
                    "End[" + new Date() + "] " +
                    "Code: " + e.getMessage()
            );
        }
    }

    void process() {
        ExecutorService service = Executors.newFixedThreadPool(10);

        final AtomicInteger counter = new AtomicInteger();
        for (int i = 0; i < 100; i++) {
            service.execute(() -> callService(counter.incrementAndGet()));
        }

        service.shutdown();

        System.out.println("Done...");
    }
}
