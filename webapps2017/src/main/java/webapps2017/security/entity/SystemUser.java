package webapps2017.security.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import webapps2017.payments.entity.PaymentTransaction;


@Entity
@NamedQueries({
@NamedQuery(name="findUser", query="SELECT c FROM SystemUser c WHERE c.username = :username"),
@NamedQuery(name="findAllUsers", query="SELECT c FROM SystemUser c"),
})
public class SystemUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull
    @Column(unique=true)
    private String username;

    @NotNull
    private String userpassword;

    @NotNull
    private String name;

    @NotNull
    private String surname;
    
    @NotNull
    private String currency;
    
    @NotNull
    private double balance;
    
//    @ElementCollection(fetch=LAZY)
 //   @OneToOne(mappedBy="username")
//    private SystemUserGroup groupname;

    public SystemUser() {
    }

    public SystemUser(String username, String userpassword, String name, String surname, String currency, double balance) {
        this.username       = username;
        this.userpassword   = userpassword;
        this.name           = name;
        this.surname        = surname;
        this.currency       = currency;
        this.balance        = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    
//    public SystemUserGroup getGroupName(){
//        return groupname;
//    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.username);
        hash = 97 * hash + Objects.hashCode(this.userpassword);
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.surname);
        hash = 97 * hash + Objects.hashCode(this.currency);
        hash = 97 * hash + Objects.hashCode(this.balance);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SystemUser other = (SystemUser) obj;

        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
               
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.userpassword, other.userpassword)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        
        if (!Objects.equals(this.currency, other.currency)) {
            return false;
        }
        
        if (!Objects.equals(this.balance, other.balance)) {
            return false;
        }    
        return Objects.equals(this.surname, other.surname);
    }

}
