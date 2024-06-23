/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package notiz.app.test;

/**
 *
 * @author Moritz
 */
public class user {
    
    private String name;
    private String username;
    private String password;
    
    public user(String user, String n, String p){
        this.name = n;
        this.username = user;
        this.password = p;
    }
    
    public String getName(){
        return this.name;
    }
    
    
    public String getUsername(){
        return this.username;
    }  
    
    public String getPassword(){
        return this.password;
    }
}
