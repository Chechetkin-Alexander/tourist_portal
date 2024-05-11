package org.portal;

import org.portal.models.Role;
import org.portal.models.Card;
import org.portal.models.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class HibernateSessionFactoryUtil {
    @Bean(name="entityManagerFactory")
    public static SessionFactory getSessionFactory() {
        try {
            Configuration configuration = new Configuration().configure();

            configuration.addAnnotatedClass(Role.class);
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Card.class);

            return configuration.buildSessionFactory(
                    new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build()
            );

        } catch (Exception exception) {
            throw new RuntimeException("cannot create db session");
        }
    }
}