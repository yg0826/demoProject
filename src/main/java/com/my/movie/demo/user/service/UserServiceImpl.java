package com.my.movie.demo.user.service;

import com.my.movie.demo.user.domain.User;
import com.my.movie.demo.user.dto.UserDTO;
import com.my.movie.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder; // 비밀번호 암호화 처리

    public void join(UserDTO userDto){

        String username=userDto.getUsername();
        String email=userDto.getEmail();
        String password=userDto.getPassword();
        String nickname=userDto.getNickname();

        //이미 가입한 회원이면 return한다.
        Boolean isExists=userRepository.existsByEmail(userDto.getEmail());
        //System.out.println(isExists);
        if(isExists){
            return; // 메서드를 강제 종료한다.
        }

        //필수 값이 null이면 return한다.
        if(email.isEmpty()||username.isEmpty()||password.isEmpty()){
            return;
        }

        User user=User.builder()
                        .email(email).username(username).password(bCryptPasswordEncoder.encode(password))
                        .nickname(nickname)
                        .build();

        userRepository.save(user);
    }

    @Transactional
    public void userLogin(UserDTO userDto){
        if(userDto.getPassword()!=null){
            login(userDto);
        }else{
            naverLogin(userDto);
        }
    }

    public void login(UserDTO userDto){
        //기본 로그인 로직 작성
    }

    public void naverLogin(UserDTO userDto){

        Boolean isExists=userRepository.existsByEmail(userDto.getEmail());
        if(isExists){
            return;
        }

    }
}
