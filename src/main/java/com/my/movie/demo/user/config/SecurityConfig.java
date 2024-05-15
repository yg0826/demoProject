package com.my.movie.demo.user.config;

import com.my.movie.demo.user.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public BCryptPasswordEncoder Encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf((csrf)-> csrf.disable());
        //경로별 인가 작업
        http
                .authorizeHttpRequests((auth)->auth
                        // login, 루트, signup경로에 대해서는 모든 경로 허용
                        .requestMatchers("/login","/","/logout","/login/**","/signup/**","/signup","/user/**","/swagger-ui/*","/v3/api-docs/**").permitAll()
                        // ADMIN권한을 가진 사용자만 접근 가능
                        .requestMatchers("/admin").hasRole("ADMIN")
                        // 그외 요청은 로그인 사용자만 접근 가능
                        .anyRequest().authenticated());
        http
                .logout((logout)->logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/login")); // 로그아웃 성공 시 이동할 페이지
        http
                .oauth2Login((oauth2)->oauth2
                        .loginPage("/oauth2/authorization/naver") //  권한 접근 실패 시 로그인 페이지로 이동
                        .defaultSuccessUrl("http://localhost:8089/mypage")// 로그인 성공 시 이동할 페이지
                        .failureUrl("/login") // 로그인 실패 시 이동할 페이지
                        .userInfoEndpoint(userinfoEndpoint -> userinfoEndpoint
                        .userService(customOAuth2UserService)));
        return http.build();
    }
}
