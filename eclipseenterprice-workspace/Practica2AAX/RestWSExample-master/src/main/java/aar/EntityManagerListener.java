package aar;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.logging.Level;
import java.util.logging.Logger;

@WebListener
public class EntityManagerListener implements ServletContextListener {

    private static EntityManagerFactory entityManager;
    
    Logger log = Logger.getLogger(EntityManagerListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        entityManager = Persistence.createEntityManagerFactory("InMemH2DB");

        DatabaseService d = new DatabaseService();
       
        Employee e1 = new Employee("Adrian Zapater", "abcd");
        Employee e2 = new Employee("Edu Escola", "1234");
        Employee e3 = new Employee("Christian Zanger", "admin");
        Employee e4 = new Employee("Alexandre Viejo", "user");
        Employee e5 = new Employee("Xavier Mallafre", "0000");
        Employee e6 = new Employee("Lorem Ipsum", "loremipsum");
        
        d.insertEmployee(e1);
        d.insertEmployee(e2);
        d.insertEmployee(e3);
        d.insertEmployee(e4);
        d.insertEmployee(e5);
        d.insertEmployee(e6);
        
        Chat c1 = new Chat("Instagram", e1.getId(), e4.getId());
        Chat c2 = new Chat("Facebook", e2.getId(), e3.getId());
        Chat c3 = new Chat("Twitter", e3.getId(), e5.getId());
        Chat c4 = new Chat("Reddit", e4.getId(), e1.getId());
        Chat c5 = new Chat("Chat URV (FAILURE!)", e5.getId(), e6.getId());
        Chat c6 = new Chat("Terra", e6.getId(), e2.getId());
        
        d.insertChat(c1);
        d.insertChat(c2);
        d.insertChat(c3);
        d.insertChat(c4);
        d.insertChat(c5);
        d.insertChat(c6);
        
        log.log(Level.INFO, "Initialized database correctly!");
    }
 
    @Override
    public void contextDestroyed(ServletContextEvent e) {

        entityManager.close();
        log.log(Level.INFO, "Destroying context.... tomcat stopping!");
    }

    public static EntityManager createEntityManager() {
        if (entityManager == null) {
            throw new IllegalStateException("Context is not initialized yet.");
        }

        return entityManager.createEntityManager();
    }
}
    
