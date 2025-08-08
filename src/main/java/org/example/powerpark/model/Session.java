package org.example.powerpark.model;
public class Session {
    private static int userId;
    private static String username;
    private static String role;

    public static void setSession(int id, String name, String userRole) {
        userId = id;
        username = name;
        role = userRole;
    }

    public static int getUserId() {
        return userId;
    }

    public static String getUsername() {
        return username;
    }

    public static String getRole() {
        return role;
    }

    public static void setUserId(int userId) {
        Session.userId = userId;
    }

    public static void setUsername(String username) {
        Session.username = username;
    }

    public static void setRole(String role) {
        Session.role = role;
    }

    public static void clear() {
        userId = 0;
        username = null;
        role = null;
    }
}
