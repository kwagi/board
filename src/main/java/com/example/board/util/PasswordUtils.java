package com.example.board.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtils {
    public static String doEncryption(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean isNotEqual(String rawPassword, String hashedPassword) {
        return !BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
