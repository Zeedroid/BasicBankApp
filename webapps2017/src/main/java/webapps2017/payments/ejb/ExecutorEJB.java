/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapps2017.payments.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
//import webapps2017.payments.operations.Operation;

@Stateless
@TransactionAttribute(MANDATORY)
public class ExecutorEJB {

    @PersistenceContext   //(unitName = "PaymentsPU")
    EntityManager em;
   // @EJB
    //PaymentsEJB pb;

    public String makePayment(String fromuser, String touser, String currency, double payment) {
        try{
            String result = "Payment Completed";
            changeBalance(fromuser, currency, payment);
            transactionOut(fromuser, touser, currency, payment);
            transactionIn(fromuser, touser, currency, payment);
            changeBalance(touser, currency, payment);
            return result;
        } catch (EJBTransactionRolledbackException pab) {
            return "EJBTransactionRolledbackException Was Caught and Transaction was Rolled Back..In Real life I should have done something here for the user (e.g. cancel a payment or reset and tell user to try again).!!!!";
        }
    }
    
    public String requestPayment(String username, double payment){
        String result = "Request Completed";
        
        return result;
    }
    
    public ArrayList getTransactions(String username){
        ArrayList aaa = new ArrayList();
        return aaa;
    }
    
    public ArrayList getAllTransactions(){
        ArrayList aaa = new ArrayList();
        return aaa;
    }
    
    public ArrayList getAllUsers(){
        ArrayList aaa = new ArrayList();
        return aaa;
    }
    
    private void changeBalance(String username, String currency, double payment){
        
    }
    
    private void transactionOut(String fromUser, String toUser, String currency, double payment){
        
    }
    
    private void transactionIn(String fromUser, String toUser, String currency, double payment){
        
    }
}