package com.goorm.profileboxapiadmin.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.goorm.profileboxapiadmin.service.MemberService;
import com.goorm.profileboxcomm.dto.member.MemberDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class bk_JwtFilter extends OncePerRequestFilter {
    private final String secretKey;
    private final String tokenPrefix;
    private final MemberService memberService;

    public bk_JwtFilter(MemberService memberService, String secretKey, String tokenPrefix) {
        this.memberService = memberService;
        this.secretKey = secretKey;
        this.tokenPrefix = tokenPrefix;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authentication = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authentication == null || !authentication.startsWith("Bearer")){
            filterChain.doFilter(request, response);
            return; // status 코드 확인 필요.
        }

        String clientJwtToken = authentication.replace(tokenPrefix,"");
        String email = JWT.require(Algorithm.HMAC512(secretKey)).build().verify(clientJwtToken).getClaim("email").asString();
        MemberDTO member = memberService.findLoginMemberByEmail(email);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, null, List.of(new SimpleGrantedAuthority(member.getMemberType().toString())));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
