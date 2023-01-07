package com.mycompany.messageboxsystem;

public class User {

    // Variables
    private String username;
    private String password;
    
    // Constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    // Getter and Setter Methods
    public String getUserName(){
        return username;
    }
    
    public void setUserName(String newUserName){
        this.username = newUserName;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String newPassword){
        this.password = SecurityFunctions.getMD5Hash(newPassword);
    }
}
