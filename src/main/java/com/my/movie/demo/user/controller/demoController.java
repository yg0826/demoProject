package com.my.movie.demo.user.controller;

import com.my.movie.demo.user.dto.UserDTO;
import com.my.movie.demo.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class demoController {

    private final UserServiceImpl userService;

    @GetMapping("/signup")
    public String signupForm(){
        return "signup.html";
    }

    @PostMapping("/signup")
    public String signup(UserDTO userDto){
        userService.signup(userDto);
        return "login.html";
    }

    @GetMapping("/login")
    public String login(){
        return"login.html";
    }

    @PostMapping("/login")
    public String loginEnd(){
        return "mypage.html";
    }

    @GetMapping("/mypage")
    public String myPageForm(){
        return "mypage.html";
    }

    @GetMapping("/movie/list")
    public String listForm(){
        return "list.html";
    }

}
