package org.example;

import java.util.prefs.Preferences;

public class JwtHandler {
    private static final Preferences prefs = Preferences.userNodeForPackage(JwtHandler.class);

    public static void storeToken(String token, boolean admin) {
        prefs.put(admin ? "ADMIN" : "USER", token);
    }

    public static String getToken(boolean admin) {
        return prefs.get(admin ? "ADMIN" : "USER" , null);
    }
}