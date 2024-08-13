package org.example;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
//        BigDecimal usdRub = convert("USD", "RUB", BigDecimal.valueOf(100));
//        BigDecimal rubUsd = convert("RUB", "USD", BigDecimal.valueOf(100));
//        BigDecimal usdEur = convert("USD", "EUR", BigDecimal.valueOf(100));
//        System.out.println(usdRub);
//        System.out.println(rubUsd);
//        System.out.println(usdEur);
    }

    public static Map<String, BigDecimal> rates() throws IOException {
        // Setting URL
        String url_str = "https://v6.exchangerate-api.com/v6/1d5eaeba98d129201ba95916/latest/USD";

// Making Request
        URL url = new URL(url_str);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

// Convert to JSON
        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject jsonobj = root.getAsJsonObject();

// Accessing object
        JsonElement req_result = jsonobj.get("conversion_rates");

        String[] conversion_rates = String.valueOf(req_result).replace("{", "").replace("}", "").replace("\"", "").split(",");
        Map<String, BigDecimal> mapRates = new TreeMap<>();
        for (int i = 0; i < conversion_rates.length; i++) {
            String[] arrayRates = conversion_rates[i].split(":");
            mapRates.put(arrayRates[0], new BigDecimal(arrayRates[1]));

        }
        return mapRates;

    }



}