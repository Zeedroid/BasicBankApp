/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapps2017.payments.jsf;

import java.security.Principal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import webapps2017.security.ejb.UserService;

@Named(value = "userList")
@RequestScoped
public class UserListBean {

    Long id;
    String username;
    String name;
    String surname;
    String currency;
    double balance;
    String results;
    String errorMessage;
    String fullusername;

    @EJB
    UserService user;
    
    public UserListBean() {
    }
    
    @PostConstruct
    public void setupUserName(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        Principal pu = request.getUserPrincipal();
 
        username = pu.getName();
        System.out.println("UserTranBean username = " + username);
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getResults(){
        return results;
    }
    
    public void setResults(String results){
        this.results = results;
    }
    
    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }
    
    public String getErrorMessage(){
        return errorMessage;
    }
    
    public List getUserList(){
        System.out.println("getUserList");
        return user.getUserList();
    }
    
    public String getFullusername(){
        System.out.println("getFullusername");
        return user.getFullUserName(username);
    }
    
    public void setFullusername(String fullusername){
        this.fullusername = fullusername;
    }
}