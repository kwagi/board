package com.example.board.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.board.member.entity.Member;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class JwtUtils {
    private final static String    key           = "kwagi";
    private final static String    claimMemberId = "member_id";
    private final static Algorithm algorithm     = Algorithm.HMAC512(key.getBytes(StandardCharsets.UTF_8));

    public static String createToken(Member member) {
        if (member == null) {
            return null;
        }
        Timestamp expiredDate = Timestamp.valueOf(LocalDateTime.now().plusHours(1));
        return JWT.create()
                .withIssuer(member.getEmail())
                .withSubject(member.getName())
                .withClaim(claimMemberId, member.getMemberId())
                .withExpiresAt(expiredDate)
                .sign(algorithm);
    }

    public static String getIssuer(String token) {
        return JWT.require(algorithm)
                .build().
                verify(token)
                .getIssuer();
    }

    public static void verifyToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(getIssuer(token))
                .build();
        verifier.verify(token);
    }
}