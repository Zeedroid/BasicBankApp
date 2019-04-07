/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapps2017.security.ejb;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import webapps2017.security.entity.SystemUser;
import webapps2017.security.entity.SystemUserGroup;

/*This EJB will be instantiated only once when the applciation is deployed - @Startup and @Singleton respectively*/
@Startup
@Singleton
public class AdminSetup {

    @PersistenceContext      //(unitName = "TransactionExercisePU")
    EntityManager em;

    @PostConstruct
    public void createAdmin() {
        System.out.println("At startup: Initialising Datbase with rows (x,y) with initial values 0");
        try {
            SystemUser sys_user;
            SystemUserGroup sys_user_group;
            
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwd = "admin";
            md.update(passwd.getBytes("UTF-8"));
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String paswdToStoreInDB = bigInt.toString(16);
           
            // apart from the default constructor which is required by JPA
            // you need to also implement a constructor that will make the following code succeed
            sys_user = new SystemUser("admin", paswdToStoreInDB, "admin", "admin", "GBP", 0);
            sys_user_group = new SystemUserGroup("admin", "admin");
            
            em.persist(sys_user);
            em.persist(sys_user_group);
        } catch (PersistenceException ex){
            System.out.println("Ignore Error: Admin Account Already Exists. " + ex.getMessage());
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(UserServiceBean.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
}    



  