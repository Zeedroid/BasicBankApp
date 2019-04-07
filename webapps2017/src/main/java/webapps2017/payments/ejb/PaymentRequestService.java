package webapps2017.payments.ejb;

import webapps2017.payments.entity.PaymentRequest;
import java.util.List;

public interface PaymentRequestService {
    
    public int insertRequestForPayment(String username, String otheruser, double payment, double currency, char status);
    
    public String acceptRequestForPayment(Long id);
    
    public String rejectRequestForPayment(Long id);
    
    public List<PaymentRequest> getRequestsForPayment(String otheruser);
    
    public List<PaymentRequest> getPaymentRequests(String username);
        
}