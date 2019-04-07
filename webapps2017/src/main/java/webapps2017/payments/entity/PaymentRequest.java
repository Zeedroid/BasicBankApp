/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapps2017.payments.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries({
@NamedQuery(name="findPaymentRequests", query="SELECT c FROM PaymentRequest c WHERE c.username = :username"),
@NamedQuery(name="findRequestsForPayment", query="SELECT c FROM PaymentRequest c WHERE c.otheruser = :otheruser"),
@NamedQuery(name="findRequestToPay", query="SELECT c FROM PaymentRequest c WHERE c.id = :id"),
})
public class PaymentRequest implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long   id;
    @NotNull
    private String username;
    @NotNull
    private String otheruser;
    @NotNull
    private double payment;
    @NotNull
    private double otherPayment;
    @NotNull
    private char   status;

    public PaymentRequest(){
        
    }
    
    public PaymentRequest(String username, String otheruser, double payment, double otherPayment, char status) {
        this.username       = username;
        this.otheruser      = otheruser;
        this.payment        = payment;
        this.otherPayment   = otherPayment;
        this.status         = status;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.username);
        hash = 59 * hash + Objects.hashCode(this.otheruser);
        hash = 59 * hash + Objects.hashCode(this.payment);
        hash = 59 * hash + Objects.hashCode(this.otherPayment);
        hash = 59 * hash + Objects.hashCode(this.status);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PaymentRequest other = (PaymentRequest) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.otheruser, other.otheruser)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.payment, other.payment)) {
            return false;
        }
        if (!Objects.equals(this.otherPayment, other.otherPayment)) {
            return false;
        }        
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }    

        return true;
    }

    public Long getId() {
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
    
    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }    
}