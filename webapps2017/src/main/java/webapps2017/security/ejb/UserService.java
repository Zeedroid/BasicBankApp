/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapps2017.security.ejb;

import java.util.List;
import webapps2017.security.ejb.UserServiceBean;
import webapps2017.security.entity.SystemUser;

public interface UserService {

    public String registerUser(String username, String userpassword, String name, String surname, String currency);
    
    public double getBalance(String username);
    
    public void updateBalance(String username, double balance);
    
    public String getCurrency(String username);
    
    public boolean userExists(String username);
    
    public String getFullUserName(String username);
    
    public List<SystemUser> getUserList();
        
}