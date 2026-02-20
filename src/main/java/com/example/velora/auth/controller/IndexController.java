package com.example.velora.auth.controller;

import com.example.velora.auth.dto.LoginRequest;
import com.example.velora.auth.dto.RegisterRequest;
import com.example.velora.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final UserService userService;


    @GetMapping("/")
    public String getIndexPage() {
        return "index";
    }

    @GetMapping("/auth/login")
    public ModelAndView getLoginPage() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        mav.addObject("loginRequest", new LoginRequest());
        return mav;
    }

    @GetMapping("/auth/register")
    public ModelAndView getRegisterPage() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("register");
        mav.addObject("registerRequest", new RegisterRequest());

        return mav;
    }

    @PostMapping("/auth/register")
    public String registerUser(@Valid RegisterRequest registerRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        userService.registerUser(registerRequest);

        return "redirect:/auth/login";
    }
}
