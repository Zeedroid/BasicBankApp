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


@Local(PaymentRequestService.class)
@Stateless
@EJB(name = "trans", beanInterface = TransactionStorageService.class)

public class PaymentRequestServiceBean implements PaymentRequestService{


    @Resource SessionContext ctx;
    @PersistenceContext 
    EntityManager em;
    
    @PostConstruct
    public void postConstruct() {
        System.out.println("TransactionStore: PostConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("TransactionStore: PreDestroy");
    }

    @Override
    public int insertRequestForPayment(String username, String otheruser, double payment, double currency, char status){
        PaymentRequest pr = new PaymentRequest(username, otheruser, payment, currency, status);
        em.persist(pr);
        return 1;
    };
    
    @Override
    public String acceptRequestForPayment(Long id){
        String username     = "";
        String otheruser    = "";
        double payment      = 0;
        double otherPayment = 0;
        System.out.println("acceptRequestForPayment id = " + id);
        PaymentRequest pr = (PaymentRequest) em.createNamedQuery("findRequestToPay")
                        .setParameter("id", id)
                        .getSingleResult();
        
        username  = pr.getUsername();
        otheruser = pr.getOtheruser();
        payment   = pr.getPayment();
        otherPayment = pr.getOtherPayment();
        System.out.println("acceptRequestForPayment 1");    
        try {
            Context ctx = new InitialContext();
                    System.out.println("acceptRequestForPayment 2");
            TransactionStorageService tss = (TransactionStorageService) ctx.lookup("java:comp/env/trans");
//            tss.insertTransaction(otheruser, username, otherPayment * -1, "OUT");
//            tss.insertTransaction(username, otheruser, payment, "IN"); 
System.out.println("acceptRequestForPayment 3");
            tss.payUser(username, payment, otheruser, otherPayment);
            System.out.println("acceptRequestForPayment 4");
        } catch (NamingException e) {
            System.out.println("acceptRequestForPayment 5");
            throw new EJBException(e);
        }
        em.remove(pr);
        return "OK";
    }
    
    @Override
    public String rejectRequestForPayment(Long id){
            PaymentRequest pr = (PaymentRequest) em.createNamedQuery("findRequestToPay")
                        .setParameter("id", id)
                        .getSingleResult();
            pr.setStatus('R');
            em.merge(pr);
        return "OK";
    }
    
    @Override
    public List<PaymentRequest> getRequestsForPayment(String otheruser){
        return em.createNamedQuery("findRequestsForPayment")
                     .setParameter("otheruser", otheruser)
                     .getResultList();
    }
    
    @Override
    public List<PaymentRequest> getPaymentRequests(String username){
        return em.createNamedQuery("findPaymentRequests")
                     .setParameter("username", username)
                     .getResultList();
     }
}   

