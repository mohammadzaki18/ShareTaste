/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FrontEnd;

/**
 *
 * @author Zah
 */

public class Session {
    private static int userId;
    private static String username;
    private static String fullName;

    public static void setUserId(int id) {
        userId = id;
    }

    public static void setUsername(String user) {
        username = user;
    }

    public static void setFullName(String name) {
        fullName = name;
    }

    public static int getUserId() {
        return userId;
    }

    public static String getUsername() {
        return username;
    }

    public static String getFullName() {
        return fullName;
    }

    public static void clear() {
        userId = 0;
        username = null;
        fullName = null;
    }

}
