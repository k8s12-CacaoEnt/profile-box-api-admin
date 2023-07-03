//package com.goorm.profileboxapiadmin.auth;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.goorm.profileboxcomm.dto.member.MemberDTO;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//@Component
//public class bk_JwtProvider {
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
//    public String generateToken(MemberDTO memberDTO){
//        return JWT.create()
//                .withSubject(memberDTO.getMemberName())
//                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
//                .withClaim("email", memberDTO.getMemberEmail())
//                .withClaim("username", memberDTO.getMemberName())
//                .sign(Algorithm.HMAC512(secretKey));
//    }
//}
