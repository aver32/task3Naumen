package ru.otstavnov_michail.NauJava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("myProfileController")
public class ProfileController {

    @GetMapping("/profile")
    public String profilePage() {
        return "profile";
    }
}
