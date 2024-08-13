package org.example.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.model.Card;
import org.example.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class TransferCardController {

    @Autowired
    CardRepository cardRepository;

    @PostMapping("/transferFunds")
    public String postMappingTransferFunds(@RequestParam String id, Model model) {
        Card card = cardRepository.findById(Integer.parseInt(id)).get();
        model.addAttribute("currency", card.getCurrency());
        model.addAttribute("balance", card.getBalance());
        model.addAttribute("id", id);
        return "transferFunds";
    }

    @PostMapping("/cardNumberForTransfer")
    public void postMapping(){

    }

//    @PostMapping("/transferFunds/{id}")
//    public String transferFunds(@RequestParam(name = "transferFunds") String transferFunds, @RequestParam(name = "id") String id,
//                           @RequestParam(name = "choice") String operationCurrency,
//                           Model model) throws IOException {
//        Card card = cardRepository.findById(Integer.parseInt(id)).get();
//        String cardCurrency = card.getCurrency().toString();
//        BigDecimal initialBalance = card.getBalance();
//        BigDecimal newBalance = transfer(cardCurrency,operationCurrency,new BigDecimal(transferFunds),initialBalance) ;
//        card.setBalance(newBalance);
//        cardRepository.save(card);
//        System.out.println(card.getBalance());
//        return "transferFunds";
//    }

//    public BigDecimal transfer(String cardCurrency, String operationCurrency, BigDecimal sumTransfer, BigDecimal initialBalance) throws IOException {
//        Map<String, BigDecimal> mapRates = rates();
//        BigDecimal sumFinal;
//        BigDecimal courseCard = mapRates.get(cardCurrency);
//        BigDecimal courseOperation = mapRates.get(operationCurrency);
//        BigDecimal newBalance;
//        if (cardCurrency.equals(operationCurrency)) {
//            newBalance = initialBalance.subtract(sumTransfer);
//            return newBalance;
//        } else   {
//            BigDecimal sumInUSD = sumTransfer.divide(courseOperation, MathContext.DECIMAL32);
//            BigDecimal sumInCardCurrency = sumInUSD.multiply(courseCard);
//            newBalance = initialBalance.subtract(sumInCardCurrency);
//            sumFinal = trimToTwoCharters(newBalance);
//            return sumFinal;
//
//        }
//
//    }
//
//    public BigDecimal trimToTwoCharters(BigDecimal sumFinal) {
//        BigDecimal newSumFinal;
//        String newSum;
//        String sumFinalString = sumFinal.toString();
//        String[] arrayStr = sumFinalString.split("\\.");
//        if (arrayStr[1].length() > 2) {
//            String numAfterPoint = arrayStr[1].substring(0, 2);
//            newSum = arrayStr[0] + "." + numAfterPoint;
//        } else
//            newSum = sumFinalString;
//        newSumFinal = new BigDecimal(newSum);
//        return newSumFinal;
//    }
//
//    public Map<String, BigDecimal> rates() throws IOException {
//        // Setting URL
//        String url_str = "https://v6.exchangerate-api.com/v6/1d5eaeba98d129201ba95916/latest/USD";
//
//// Making Request
//        URL url = new URL(url_str);
//        HttpURLConnection request = (HttpURLConnection) url.openConnection();
//        request.connect();
//
//// Convert to JSON
//        JsonParser jp = new JsonParser();
//        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
//        JsonObject jsonobj = root.getAsJsonObject();
//
//// Accessing object
//        JsonElement req_result = jsonobj.get("conversion_rates");
//
//        String[] conversion_rates = String.valueOf(req_result).replace("{", "").replace("}", "").replace("\"", "").split(",");
//        Map<String, BigDecimal> mapRates = new TreeMap<>();
//        for (int i = 0; i < conversion_rates.length; i++) {
//            String[] arrayRates = conversion_rates[i].split(":");
//            mapRates.put(arrayRates[0], new BigDecimal(arrayRates[1]));
//
//        }
//        return mapRates;
//
//    }



}
