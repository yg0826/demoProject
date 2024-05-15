package com.my.movie.demo.user.service;

import com.my.movie.demo.user.domain.OAuthAttributes;
import com.my.movie.demo.user.domain.User;
import com.my.movie.demo.user.dto.UserSessionDTO;
import com.my.movie.demo.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession session;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate=new DefaultOAuth2UserService();
        OAuth2User oAuth2User=delegate.loadUser(userRequest);

        // OAuth2 서비스 id 구분 코드( 구글, 네이버, 카카오 ...)
        String registrationId=userRequest.getClientRegistration().getRegistrationId();

        // OAuth2 로그인 진행 시 키가 되는 필드 값(PK) (구글 기본 코드는 "sub")
        String userNameAttributeName=userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes=OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user= saveorUpdate(attributes);

        UserSessionDTO userSessionDTO=new UserSessionDTO(user.getEmail(), user.getUsername(),
                user.getNickname(), user.getPassword(), user.getRole(), user.getIndate(), user.getDeldate());

        session.setAttribute("user",userSessionDTO);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleValue())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }
    //소셜 로그인 시 기존 회원이 존재하면 수정 날짜 정보만 업데이트해서 기존 데이터는 그대로 보존한다.
    private User saveorUpdate(OAuthAttributes attributes){
        User user=userRepository.findByEmail(attributes.getEmail())
                .map(User::updateModifiedDate)
                .orElse(attributes.toEntity());
        return userRepository.save(user);
    }
}
