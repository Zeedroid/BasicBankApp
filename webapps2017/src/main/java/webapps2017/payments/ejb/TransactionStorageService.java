
package webapps2017.payments.ejb;

import webapps2017.payments.entity.PaymentTransaction;
import java.util.List;

public interface TransactionStorageService {
    
    public int insertTransaction(String username, String otheruser, double payment, String trantype);
    
    public List<PaymentTransaction> getAllTransactionList();
    
    public List<PaymentTransaction> getUserTransactionList(String username);
    
    public int payUser(String username, double payment, String otheruser, double currval);
        
}