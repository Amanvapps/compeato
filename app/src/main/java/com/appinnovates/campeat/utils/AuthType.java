package com.appinnovates.campeat.utils;

import com.parse.AuthenticationCallback;

import java.util.Map;

public class AuthType implements AuthenticationCallback {
    @Override
    public boolean onRestore(Map<String, String> authData) {
        return true;
    }
}

