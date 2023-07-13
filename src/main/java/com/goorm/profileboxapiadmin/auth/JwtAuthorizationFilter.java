package com.goorm.profileboxapiadmin.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.goorm.profileboxapiadmin.service.MemberService;
import com.goorm.profileboxcomm.entity.Member;
import com.goorm.profileboxcomm.service.MemberRedisService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private MemberService memberService;
    private MemberRedisService memberRedisService;
    private JwtProvider jwtProvider;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberService memberService, JwtProvider jwtProvider, MemberRedisService memberRedisService) {

        super(authenticationManager);
        this.memberService = memberService;
        this.memberRedisService = memberRedisService;
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        String jwtToken = jwtProvider.getJwtAccessTokenFromCookie(request);
        String jwtToken = jwtProvider.getJwtAccessTokenFromHeader(request);

        if(jwtToken.equals("")){
            chain.doFilter(request, response);
            return;
        }

        String email = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtToken).getClaim("email").asString();
        if(email != null){
            Member member = memberService.findLoginMemberByEmail(email);
//            Member member = memberRedisService.getMemberFromRedis(jwtToken);
//            if(member == null){
//                member = memberService.findLoginMemberByEmail(email);
//            }
            PrincipalDetails princialDetails = new PrincipalDetails(member);
            Authentication authentication = new UsernamePasswordAuthenticationToken(princialDetails, null, princialDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("context holder에 authenticaton 저장");
            chain.doFilter(request, response);
        }

    }
}
