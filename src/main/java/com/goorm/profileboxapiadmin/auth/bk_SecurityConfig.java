//package com.goorm.profileboxapiadmin.auth;
//
//
//import com.goorm.profileboxapiadmin.service.MemberService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.filter.CorsFilter;
//
//@EnableWebSecurity
//@Configuration
//@RequiredArgsConstructor
//public class bk_SecurityConfig {
//
//    private final CorsFilter corsFilter;
////    private final AuthenticationConfiguration authenticationConfiguration;
//    private final MemberService memberService;
//
//    @Value("${jwt.secret}")
//    private String secretKey;
//
//    @Value("${jwt.expirationTime}")
//    private int expirationTime;
//
//    @Value("${jwt.tokenPrefix}")
//    private String tokenPrefix;
//
//    @Value("${jwt.headerString}")
//    private String headerString;
//
//
//    private final String[] allowedUrls = {"/", "/swagger-ui/**", "/v3/**", "/v1/auth/member/login", "/v1/auth/member/register"};
//
////    @Bean
////    public AuthenticationManager authenticationManager() throws Exception{
////        return authenticationConfiguration.getAuthenticationManager();
////    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        // 면접에서 물어볼만한 질문: 인증방식 중에 하나를 선택햇 ㅓ jwt를 선택한건데 왜 세션을 사용하냐?. 왜 jwt를 사용했는지?
//        return http.httpBasic().disable()
//                .csrf().disable()
//                .cors()
//                .and()
//                .addFilter(corsFilter)
//                .addFilterBefore(new bk_JwtFilter(memberService, secretKey, tokenPrefix), UsernamePasswordAuthenticationFilter.class)
//                .authorizeHttpRequests()
//                .requestMatchers("/v1/auth/member/login", "/v1/auth/member/register").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // csrf
//                .and()
//                .build();
//    }
//}
