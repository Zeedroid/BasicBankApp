package webapps2017.payments.jsf;

import java.security.Principal;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import webapps2017.payments.ejb.TransactionStorageService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import webapps2017.restful.CurrencyJson;
import webapps2017.security.ejb.UserService;

@Named(value = "directPay")
@RequestScoped
//@ManagedBean
public class DirectPayBean {

    
    String username;
    String otheruser;
    double payment;
    String results;
    String errorMessage;
    
    @EJB
    TransactionStorageService store;
    
    @EJB
    UserService user;
    
    public DirectPayBean() {
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
    
    public String getOtheruser() {
        return otheruser;
    }
    
    public void setOtheruser(String otheruser) {
        this.otheruser = otheruser;
    }
    
    public double getPayment() {
        return payment;
    }
    
    public void setPayment(double payment) {
        this.payment = payment;
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
    
    public String payUser() {
        if (!user.userExists(otheruser)){
            errorMessage = "User Not Found: Payment of " + payment + " " + user.getCurrency(username) + " Has Failed. User: " + otheruser + " Does Not Exist!";
            return "userError";
        } 
        
        double userBalance = user.getBalance(username);
//        double otherBalance = user.getBalance(otheruser);
        double currVal = getCurrencyValue();
        
        if ( userBalance >= payment ){
            
            int a = store.payUser(username,payment,otheruser,currVal);

/*            int a = store.insertTransaction(username, otheruser, payment * -1, "OUT");
            user.updateBalance(username, userBalance - payment);

            int b = store.insertTransaction(otheruser, username, currVal, "IN");
            user.updateBalance(otheruser, otherBalance + currVal);*/
            
            results = "Payment Completed";
            return "payDirect";
        }else{
            errorMessage = "Insuficient Funds: Payment of " + payment + " " + user.getCurrency(username) + " Has Failed. Your Current Balance Is: " + userBalance;
            return "balanceError";
        }
    }
    
    private double getCurrencyValue(){
            double currVal = 0;
            System.out.println("a");
            
            String url = "http://localhost:8080/webapps2017/rest/conversion";
                        System.out.println("b");
            
            Client client = ClientBuilder.newClient();
                        System.out.println("c");
         // WebTarget target = client.target(url);
            CurrencyJson conversion = client.target(url).path("/{currency1}/{currency2}/{amount_of_currency1}").resolveTemplate("currency1", user.getCurrency(username)).resolveTemplate("currency2", user.getCurrency(otheruser)).resolveTemplate("amount_of_currency1", payment).request(MediaType.APPLICATION_JSON).get(CurrencyJson.class); 
             System.out.println("d");
            if(conversion != null){
                currVal = conversion.getAmountCurrency2(); 
            } 
            return currVal;
    }
}
   

