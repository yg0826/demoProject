package com.my.movie.demo.user.controller;

import com.my.movie.demo.user.dto.UserDTO;
import com.my.movie.demo.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/join")
    public String joinProcess(@RequestParam("username")String username, @RequestParam("nickname") String nickname,
                              @RequestParam("password") String password, @RequestParam("email") String email){

        UserDTO userDto=new UserDTO();
        userDto.setUsername(username);
        userDto.setPassword(password);
        userDto.setEmail(email);
        userDto.setNickname(nickname);

        //회원가입을 진행한다.
        userService.join(userDto);
        //성공한다고 가정한다.
        return "login";
    }
}
