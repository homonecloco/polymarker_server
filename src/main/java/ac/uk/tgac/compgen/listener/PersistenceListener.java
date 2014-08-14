package ac.uk.tgac.compgen.listener;

/**
 * Created by ramirezr on 05/03/2014.
 */
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class PersistenceListener implements ServletContextListener {


    private EntityManagerFactory entityManagerFactory;

    public void contextInitialized(ServletContextEvent sce){
        ServletContext context = sce.getServletContext();
        entityManagerFactory = Persistence.createEntityManagerFactory("com.playground.myapp.jpa");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        entityManagerFactory.close();
    }
}
