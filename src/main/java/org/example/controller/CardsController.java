package org.example.controller;

import org.example.model.Card;
import org.example.model.Currency;
import org.example.model.User;
import org.example.repository.CardRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class CardsController {

    @Autowired
    CardRepository cardRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/cards")
    public String getMapping(Model model) {
        List<Currency> currencies = List.of(Currency.values());
        model.addAttribute("currencies", currencies);
        model.addAttribute("cardNumber", currentNumber());
        model.addAttribute("cvcNumber", cvcNumber());
        model.addAttribute("dateNow", dateNow());

        return "cards";
    }


    @PostMapping("/addCards")
    public String addCards(@RequestParam(name = "cardNumber") String cardNumber, @RequestParam(name = "cvcNumber") String cvcNumber,
                            @RequestParam(name = "choice") String currencies,@AuthenticationPrincipal User user, Model model) {
        Card card = new Card();
        Currency selectedCurrency = Currency.valueOf(currencies);
        card.setCurrency(selectedCurrency);
        card.setCvc(Integer.parseInt(cvcNumber));
        card.setNumberCard(cardNumber);
        card.setDateEnd(Calendar.getInstance().getTime());
        card.setUser(user);
        card.setBalance(BigDecimal.ZERO);
        cardRepository.save(card);
        return "redirect:/getCards";
    }

    public String currentNumber() {
        String str = randomCardNumber();
        while (!isValidLuhn(str)) {
            str = randomCardNumber();
        }
        return str;
    }

    public String randomCardNumber() {
        int randomNumb;
        String numCard = "";
        for (int i = 0; i < 16; i++) {
            randomNumb = (int) (Math.random() * 10);
            numCard = numCard + randomNumb;
        }
        char delimiter = ' ';
        String strCardNumber = numCard.replaceAll(".{4}(?!$)", "$0" + delimiter);
        return strCardNumber;
    }

    private boolean isValidLuhn(String value) {
        int sum = Character.getNumericValue(value.charAt(value.length() - 1));
        int parity = value.length() % 2;
        for (int i = value.length() - 2; i >= 0; i--) {
            int summand = Character.getNumericValue(value.charAt(i));
            if (i % 2 == parity) {
                int product = summand * 2;
                summand = (product > 9) ? (product - 9) : product;
            }
            sum += summand;
        }
        return (sum % 10) == 0;

    }

    public String cvcNumber() {
        int randomNumb;
        String cvcCard = "";
        for (int i = 0; i < 3; i++) {
            randomNumb = (int) (Math.random() * 10);
            cvcCard = cvcCard + randomNumb;
        }
        return cvcCard;

    }

    public String dateNow() {
        int month = LocalDate.now().getMonthValue();
        String year = String.valueOf(LocalDate.now().getYear());
        String dateNow;
        if (month < 10) {
            dateNow = "0" + month + "/" + year.substring(2);
        } else
            dateNow = month + "/" + year.substring(2);
        return dateNow;

    }

}
