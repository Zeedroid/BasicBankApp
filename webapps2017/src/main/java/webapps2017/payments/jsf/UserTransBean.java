
package webapps2017.payments.jsf;

import java.security.Principal;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import webapps2017.payments.ejb.TransactionStorageService;
import javax.servlet.http.HttpServletRequest;
import webapps2017.security.ejb.UserService;

@Named(value = "userTrans")
@RequestScoped
//@ManagedBean
public class UserTransBean {

    String username;
    String otheruser;
    String fullusername;
    double payment;
    String results;
    String errorMessage;
    
    @EJB
    TransactionStorageService store;
    
    @EJB
    UserService user;
    
    public UserTransBean() {
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
    
    public List getTransactionList(){
        System.out.println("getTransactionList");
        return store.getUserTransactionList(username);
    }
    
    public String getFullusername(){
        System.out.println("getFullusername");
        return user.getFullUserName(username);
    }
    
    public void setFullusername(String fullusername){
        this.fullusername = fullusername;
    }
    
    public List getAllTransactions(){
        System.out.println("getTransactionList");
        return store.getAllTransactionList();
    }
}
   

