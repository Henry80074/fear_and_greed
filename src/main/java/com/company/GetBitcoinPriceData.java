package com.company;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GetBitcoinPriceData {

    public static void main(String[] args) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.coingecko.com/api/v3/coins/bitcoin/market_chart?vs_currency=usd&days=1365&interval=daily")).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(GetBitcoinPriceData::parse)
                .join();
    }

    public static String parse(String responseBody) {
        JSONObject index = new JSONObject(responseBody);
        System.out.println(index);
        JSONArray array = index.getJSONArray("prices");
        System.out.println(array);
        try {

            // Writing to a file
            FileWriter file = new FileWriter("C:\\Users\\3henr\\IdeaProjects\\crypto_fear_and_greed\\bitcoin_price_chart.json");
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