package com.my.movie.demo.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class MainController {

    @GetMapping("/")
    public String main(){
        return "Main Controller";
    }
}
