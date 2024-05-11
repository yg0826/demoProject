package com.my.movie.demo.user.service;

import com.my.movie.demo.user.domain.User;
import com.my.movie.demo.user.dto.UserDTO;
import com.my.movie.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepository;

    public void signup(UserDTO userDto){
        //유효성 검사
        Boolean isExists=userRepository.existsByEmail(userDto.getEmail());
        if(isExists){
            return;
        }

        String email=userDto.getEmail();
        String username=userDto.getUsername();
        String password=userDto.getPassword();

        if(email.isEmpty()||username.isEmpty()||password.isEmpty()){
            return;
        }

        User user=User.builder()
                        .email(email).username(username).password(password).build();
        userRepository.save(user);
    }
}
