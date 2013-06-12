package service;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Account;
import model.Customer;
import model.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.BeforeClass;

public class TransactionManagerTest {

    private static EntityManager em;
    private static TransactionManager transactionManager;
    private static AccountManager accountManager;

    @BeforeClass
    public static void beforeClass() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bank-test");
        em = emf.createEntityManager();

        transactionManager = new TransactionManager();
        transactionManager.em = em;

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
    public void testWithDraw() throws Exception {
        Customer customer = accountManager.createCustomer("Petra Müller", "Bahnhofstrasse 1, 3000 Bern", "5678");
        Assert.assertNotNull(customer.getId());

        Account account = accountManager.createAccount(customer, "Privatkonto");
        Assert.assertNotNull(account.getId());

        Transaction transaction = transactionManager.withDraw(account, BigDecimal.TEN);
        Assert.assertNotNull(transaction.getId());
    }
}