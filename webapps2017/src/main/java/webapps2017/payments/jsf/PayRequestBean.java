/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import webapps2017.payments.ejb.PaymentRequestService;
import webapps2017.restful.CurrencyJson;
import webapps2017.security.ejb.UserService;

@Named(value = "payRequest")
@RequestScoped
//@ManagedBean
public class PayRequestBean {

    String username;
    String userCurrency;
    String otheruser;
    String otherCurrency;
    double payment;
    double otherPayment;
    char   status;
    String results;
    String errorMessage;
    String fullusername;
    Long   id;
    
    @EJB
    TransactionStorageService store;
    
    @EJB
    UserService user;
    
    @EJB
    PaymentRequestService payreq;
    
    public PayRequestBean() {
    }
    
    @PostConstruct
    public void setupUserName(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        Principal pu = request.getUserPrincipal();
 
        username     = pu.getName();
        userCurrency = user.getCurrency(username);
        fullusername = user.getFullUserName(username);
        System.out.println("UserTranBean username = " + username);
    }
    
    public Long getId(){
        return id;
    }
    
    public void setId(Long id){
        this.id = id;
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
    
    public double getOtherPayment() {
        return otherPayment;
    }
    
    public void setOtherPayment(double otherPayment) {
        this.otherPayment = otherPayment;
    }
    public char getStatus(){
        return status;
    }
    
    public void setStatus(char status){
        this.status = status;
    }
    
    public void setFullusername(String fullusername){
        this.fullusername = fullusername;
    }
    
    public String getFullusername(){
        return fullusername;
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
    
    public String requestPayment() {
        if (!user.userExists(otheruser)){
            errorMessage = "User Not Found: Request For Payment Has Failed. User: " + otheruser + " Does Not Exist!";
            return "userError";
        } 
        
        otherCurrency = user.getCurrency(otheruser);
        otherPayment  = convertCurrency(userCurrency, otherCurrency, payment);
        int a = payreq.insertRequestForPayment(username, otheruser, payment, otherPayment, 'C');
           
        results = "Payment Request Created";
        return "payRequest";
    }
    
    public List getPaymentList(){
        System.out.println("getPaymentList");
        return payreq.getRequestsForPayment(username);
    }
    
    public List getRequestList(){
        System.out.println("requestList");
        return payreq.getPaymentRequests(username);
    }
    
    public String acceptRequest(Long id){
        System.out.println("acceptRequest id = " + id);
        String aaa = payreq.acceptRequestForPayment(id);
        results = "Payment Request Accepted";
        return "acceptRequest";
    }
    
    public String rejectRequest(Long id){
        String aaa = payreq.rejectRequestForPayment(id);
        results = "Payment Request Rejected";
        return "acceptRequest";
    }
    
    private double convertCurrency(String fromCurrency, String toCurrency, double fromValue){
        double currVal = 0;
        String url = "http://localhost:8080/webapps2017/rest/conversion";
        System.out.println("b");
            
        Client client = ClientBuilder.newClient();
        System.out.println("c");
        // WebTarget target = client.target(url);
        CurrencyJson conversion = client.target(url).path("/{currency1}/{currency2}/{amount_of_currency1}").resolveTemplate("currency1", fromCurrency).resolveTemplate("currency2", toCurrency).resolveTemplate("amount_of_currency1", fromValue).request(MediaType.APPLICATION_JSON).get(CurrencyJson.class); 
//        CurrencyJson conversion = client.target(url).path("/{currency1}/{currency2}/{amount_of_currency1}").resolveTemplate("currency1", user.getCurrency(username)).resolveTemplate("currency2", user.getCurrency(otheruser)).resolveTemplate("amount_of_currency1", payment).request(MediaType.APPLICATION_JSON).get(CurrencyJson.class); 
        System.out.println("d");
        if(conversion != null){
           currVal = conversion.getAmountCurrency2(); 
        }
        return currVal;
    }
}
   

