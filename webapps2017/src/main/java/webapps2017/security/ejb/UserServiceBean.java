package webapps2017.security.ejb;

import webapps2017.security.entity.SystemUser;
import webapps2017.security.entity.SystemUserGroup;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import webapps2017.restful.CurrencyJson;

/**
 *
 * @author 
 */
@Stateless
public class UserServiceBean implements UserService{

    @PersistenceContext
    EntityManager em;

    public UserServiceBean() {
    }

    @Override
    public String registerUser(String username, String userpassword, String name, String surname, String currency) {
        try {
            SystemUser sys_user;
            SystemUserGroup sys_user_group;
            double balance = 0;
            System.out.println("a");
            
            String url = "http://localhost:8080/webapps2017/rest/conversion";
                        System.out.println("b");
            
            Client client = ClientBuilder.newClient();
                        System.out.println("c");
         // WebTarget target = client.target(url);
            CurrencyJson conversion = client.target(url).path("/{currency1}/{currency2}/{amount_of_currency1}").resolveTemplate("currency1", "GBP").resolveTemplate("currency2", currency).resolveTemplate("amount_of_currency1", 100).request(MediaType.APPLICATION_JSON).get(CurrencyJson.class); 
             System.out.println("d");
            if(conversion != null){
                balance = conversion.getAmountCurrency2(); 
            } 
                        System.out.println("e");
            System.out.println("New currency value is : " + balance );
            
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwd = userpassword;
            md.update(passwd.getBytes("UTF-8"));
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String paswdToStoreInDB = bigInt.toString(16);
            
                        System.out.println("f");
            // apart from the default constructor which is required by JPA
            // you need to also implement a constructor that will make the following code succeed
            sys_user = new SystemUser(username, paswdToStoreInDB, name, surname, currency, balance);
                        System.out.println("g");
            sys_user_group = new SystemUserGroup(username, "users");
            
                        System.out.println("h");
            em.persist(sys_user);
            em.flush();
                        System.out.println("i");
            em.persist(sys_user_group);
            em.flush();
            
        } catch (PersistenceException ex){
                       System.out.println("AAAAAAAAAAAAAAAAAAError: " + ex.getMessage());
            return "DUPLICATE";
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
                       System.out.println("BBBBBBBBBBBBBBError: " + ex.getMessage());
            Logger.getLogger(UserServiceBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "INSERTED";
    }
    
    @Override
    public double getBalance(String username){
        double balance = ((SystemUser) em.createNamedQuery("findUser")
                        .setParameter("username", username)
                        .getSingleResult()).getBalance();
    //    double balance = em.find(SystemUser.class, username).getBalance();
        return balance;
    }
    
    @Override
    public void updateBalance(String username, double balance){
        SystemUser user =(SystemUser) em.createNamedQuery("findUser")
                        .setParameter("username", username)
                        .getSingleResult();
        //    SystemUser user = em.find(SystemUser.class, username);
        user.setBalance(balance);
    }
    
    @Override
    public String getCurrency(String username){
        String currency = ((SystemUser) em.createNamedQuery("findUser")
                        .setParameter("username", username)
                        .getSingleResult()).getCurrency();
        return currency;
    }
    
    @Override
    public boolean userExists(String username){
        return (em.createNamedQuery("findUser")
                        .setParameter("username", username)
                        .getResultList()).size() == 1;
    }
    
    @Override
    public String getFullUserName(String username){
        SystemUser aUser = (SystemUser) em.createNamedQuery("findUser")
                        .setParameter("username", username)
                        .getSingleResult();
        System.out.println("Name =" + aUser.getName() + "-END");
        return aUser.getName() + " " + aUser.getSurname();
    }
    
    @Override
    public List<SystemUser> getUserList(){
        return em.createNamedQuery("findAllUsers")
                        .getResultList();
    }
}
            


 


