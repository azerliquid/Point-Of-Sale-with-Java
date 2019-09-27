package config;

/**
 *
 * @author Reza Azmi
 */
public class UserID {
    private static  String username;
    
    public static void setUserLogin(String username) {
        UserID.username = username;
    }
    
    public static String getUserLogin(){
        return username;
    }
}
