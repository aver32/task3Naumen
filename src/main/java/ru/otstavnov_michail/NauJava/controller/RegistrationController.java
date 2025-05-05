package ru.otstavnov_michail.NauJava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otstavnov_michail.NauJava.modeldb.User;
import ru.otstavnov_michail.NauJava.service.UserService;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping
    public String registerUser(User user, Model model) {
        try {
            userService.registerUser(user);
            return "redirect:/login?registered";
        } catch (Exception e) {
            model.addAttribute("message", "Ошибка регистрации: " + e.getMessage());
            model.addAttribute("user", user);
            return "registration";
        }
    }
}