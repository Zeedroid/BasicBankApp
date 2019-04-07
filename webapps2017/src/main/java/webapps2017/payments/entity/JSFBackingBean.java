/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapps2017.payments.entity;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import webapps2017.payments.ejb.ExecutorEJB;
////import webapps2017.payments.operations.Operation;
//import webapps2017.payments.ParseException;
import webapps2017.payments.ejb.TransactionStorageService;

@Named(value = "paymentsBean")
@ConversationScoped
public class JSFBackingBean implements Serializable {

    @EJB
    ExecutorEJB patmentsEJB;
 
    private String username;
    private double payment;
    private String results;
    
    TransactionStorageService store;

    public JSFBackingBean() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public String getResults() {
        return username;
    }

    public void setResults(String results) {
        this.results = results;
    }
    
    public String payuser() {
 //       results = ExecutorEJB.execute(ops);
 //       setParseOutput(ops.toString());
        return "index";
    }
    
 //   public List<PaymentTransaction> getTransactionList() {
//        return store.getTransactionList();
//    }
}
