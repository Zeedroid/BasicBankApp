package webapps2017.payments.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries({
@NamedQuery(name="findUserTransactions", query="SELECT c FROM PaymentTransaction c WHERE c.username = :username"),
@NamedQuery(name="findAllTransactions", query="SELECT c FROM PaymentTransaction c"),
})
public class PaymentTransaction implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String username;
    @NotNull
    private String otheruser;
    @NotNull
    private double payment;
//    @Temporal(javax.persistence.TemporalType.DATE)
    @NotNull
    private String trantype;
    
    

    public PaymentTransaction(){
        
    }
    
    public PaymentTransaction(String username, String otheruser, double payment, String trantype) {
        this.username  = username;
        this.otheruser = otheruser;
        this.payment   = payment;
        this.trantype  = trantype;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.username);
        hash = 59 * hash + Objects.hashCode(this.otheruser);
        hash = 59 * hash + Objects.hashCode(this.payment);
        hash = 59 * hash + Objects.hashCode(this.trantype);
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
        final PaymentTransaction other = (PaymentTransaction) obj;
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
        
        if (!Objects.equals(this.trantype, other.trantype)) {
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

    public void setUserame(String username) {
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

    public String getTrantype() {
        return trantype;
    }

    public void setTrantype(String trantype) {
        this.trantype = trantype;
    }
}