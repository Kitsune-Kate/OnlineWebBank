package org.example.controller;

import org.example.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping("/testCardBlackRed")
    public String getMapping(Model model, @AuthenticationPrincipal User user) {


        return "testCardBlackRed";

    }
}
