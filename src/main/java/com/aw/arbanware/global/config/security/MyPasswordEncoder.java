package com.aw.arbanware.global.config.security;

import org.springframework.security.crypto.password.PasswordEncoder;

public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public boolean upgradeEncoding(final String encodedPassword) {
        return false;
    }

    @Override
    public String encode(final CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
        return encodedPassword.equals(encode(rawPassword));
    }
}
