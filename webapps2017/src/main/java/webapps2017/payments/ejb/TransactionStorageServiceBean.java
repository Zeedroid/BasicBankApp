package webapps2017.payments.ejb;

import webapps2017.payments.entity.PaymentTransaction;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import webapps2017.payments.entity.PaymentRequest;
import webapps2017.security.ejb.UserService;
import webapps2017.security.entity.SystemUser;


@Local(TransactionStorageService.class)
@Stateless
@EJB(name = "user", beanInterface = UserService.class)
public class TransactionStorageServiceBean implements TransactionStorageService{
    
@Resource SessionContext ctx;
@PersistenceContext 
EntityManager em;
//@PersistenceUnit
//EntityManagerFactory emf;

    public TransactionStorageServiceBean() {
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("TransactionStore: PostConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("TransactionStore: PreDestroy");
    }

    @Override
    public int insertTransaction(String username, String otheruser, double payment, String trantype) {
        PaymentTransaction pt = new PaymentTransaction(username, otheruser, payment, trantype);
        em.persist(pt);
        return 1;
        
    }

    @Override
    public List<PaymentTransaction> getAllTransactionList() {
        return em.createNamedQuery("findAllTransactions").getResultList();
    }
    
    @Override
    public List<PaymentTransaction> getUserTransactionList(String username) {
       return em.createNamedQuery("findUserTransactions")
                        .setParameter("username", username)
                        .getResultList();
    }
    
    @Override
    public int payUser(String username, double payment, String otheruser, double currval){
    
        int i = insertTransaction(username, otheruser, payment * -1, "OUT");
        int j = insertTransaction(otheruser, username, currval, "IN");
        
        SystemUser sr = (SystemUser) em.createNamedQuery("findUser")
                        .setParameter("username", username)
                        .getSingleResult();
        
        sr.setBalance(sr.getBalance() - payment);
        em.merge(sr);
        
        sr = (SystemUser) em.createNamedQuery("findUser")
                        .setParameter("username", otheruser)
                        .getSingleResult();
        
        sr.setBalance(sr.getBalance() + currval);
        em.merge(sr);

        return 1;
    }
}   

