package business;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Account;
import model.Customer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.BeforeClass;

public class AccountManagerTest {

    private static EntityManager em;
    private static AccountManager accountManager;

    @BeforeClass
    public static void beforeClass() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bank-test");
        em = emf.createEntityManager();

        accountManager = new AccountManager();
        accountManager.em = em;
    }

    @Before
    public void beginTransaction() {
        em.getTransaction().begin();
    }

    @After
    public void endTransaction() {
        em.getTransaction().commit();
    }

    @Test
    public void testCreateCustomer() throws Exception {
        Customer customer = accountManager.createCustomer("Peter Muster", "Bahnhofstrasse 1, 3000 Bern", "1234");
        Assert.assertNotNull(customer.getId());
    }

    @Test
    public void testCreateAccount() throws Exception {
        Customer customer = accountManager.createCustomer("Petra Müller", "Bahnhofstrasse 1, 3000 Bern", "5678");
        Assert.assertNotNull(customer.getId());
        
        Account account = accountManager.createAccount(customer, "Privatkonto");
        Assert.assertNotNull(account.getId());
    }
    
    @Test
    public void testGetAccounts() throws Exception {
        Customer customer = accountManager.createCustomer("Petra Müller", "Bahnhofstrasse 1, 3000 Bern", "5678");
        Assert.assertNotNull(customer.getId());
        
        Account account = accountManager.createAccount(customer, "Privatkonto");
        Assert.assertNotNull(account.getId());
        
        List<Account> accounts = accountManager.getAccounts(customer.getId());
        Assert.assertTrue(accounts.size() > 0);
    }
}