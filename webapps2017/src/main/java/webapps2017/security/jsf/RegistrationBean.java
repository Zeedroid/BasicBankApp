package webapps2017.security.jsf;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import webapps2017.security.ejb.UserService;

@Named
@RequestScoped
public class RegistrationBean {

    @EJB
    UserService usrSrv;
    
    String username;
    String userpassword;
    String name;
    String surname;
    String currency;
    String results;

    public RegistrationBean() {

    }

    //call the injected EJB
    public String register() {
        System.out.println("AAAAA");
        String aaa = usrSrv.registerUser(username, userpassword, name, surname, currency);
        if (aaa == "DUPLICATE"){
            results = "Error: USER ALREADY EXISTS";
            return "registration";
        }
        System.out.println("BBBBB");
        return "index";
    }
    
    public UserService getUsrSrv() {
        System.out.println("CCCCC");
        return usrSrv;
    }

    public void setUsrSrv(UserService usrSrv) {
        this.usrSrv = usrSrv;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    public String getResults(){
        return results;
    }
}
