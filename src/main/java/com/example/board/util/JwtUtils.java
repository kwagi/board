package com.example.board.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.board.member.entity.Member;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class JwtUtils {
    private final static String key           = "kwagi";
    private final static String claimMemberId = "member_id";

    public static String createToken(Member member) {
        if (member == null) {
            return null;
        }

        Timestamp expiredDate = Timestamp.valueOf(LocalDateTime.now().plusHours(1));

        return JWT.create()
                .withIssuer(member.getEmail())
                .withSubject("verifying" + member.getName())
                .withClaim(claimMemberId, member.getId())
                .withExpiresAt(expiredDate)
                .sign(Algorithm.HMAC512(key.getBytes(StandardCharsets.UTF_8)));
    }

    public static String getIssuer(String token) {
        return JWT.require(Algorithm.HMAC512(key.getBytes(StandardCharsets.UTF_8)))
                .build().
                verify(token)
                .getIssuer();
    }
}