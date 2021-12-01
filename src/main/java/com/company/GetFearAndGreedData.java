package com.company;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GetFearAndGreedData {

    public static void main(String[] args) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.alternative.me/fng/?limit=0")).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(GetFearAndGreedData::parse)
                .join();
    }

    public static String parse(String responseBody) {
        JSONObject index = new JSONObject(responseBody);
        System.out.println(index);
        JSONArray array = index.getJSONArray("data");
        System.out.println(array);
        try {

            // Writing to a file
            FileWriter file = new FileWriter("C:\\Users\\3henr\\IdeaProjects\\crypto_fear_and_greed\\output.json");
            file.write(index.toString());
            file.close();
            System.out.println("Writing JSON object to file");
            System.out.println("-----------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
        }
}

