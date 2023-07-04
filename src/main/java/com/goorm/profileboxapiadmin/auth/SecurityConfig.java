package com.goorm.profileboxapiadmin.auth;


import com.goorm.profileboxcomm.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private CorsFilter corsFilter;
    private AuthenticationConfiguration authenticationConfiguration;
    private MemberRepository memberRepository;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    public AuthenticationManager authenticationManager() throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable(); // csrf
        // 면접에서 물어볼만한 질문: 인증방식 중에 하나를 선택햇 ㅓ jwt를 선택한건데 왜 세션을 사용하냐?. 왜 jwt를 사용했는지?
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(corsFilter)
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), memberRepository))
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeHttpRequests()
                // hasRole이나 hasAnyRole은 "ROLE_" prefix를 붙여버림.
                .requestMatchers("v1/notice/admin/**").hasAnyAuthority("ADMIN", "PRODUCER")
                //.requestMatchers("v1/notice/admin/**").hasAuthority("ADMIN")
                .anyRequest().permitAll();
        return http.build();
    }

}
