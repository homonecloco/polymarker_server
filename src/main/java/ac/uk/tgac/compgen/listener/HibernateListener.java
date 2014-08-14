package ac.uk.tgac.compgen.listener;

/**
 * Created by ramirezr on 05/03/2014.
 */
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class HibernateListener  implements ServletContextListener {

    private SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;

    public void contextInitialized(ServletContextEvent sce){
        ServletContext context = sce.getServletContext();

        try{
            Configuration configuration = new Configuration();
            configuration.configure();
            serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        }catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        context.setAttribute("sessionFactory", sessionFactory);

    }

    public void contextDestroyed(ServletContextEvent sce) {
        sessionFactory.close();
     }
}