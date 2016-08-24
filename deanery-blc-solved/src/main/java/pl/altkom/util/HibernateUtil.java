package pl.altkom.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory sessionFactory;

	public static Session getSession() {
		if (sessionFactory == null) {
			Configuration configuration = new Configuration();
			// uzupelnic nazwe pliku
			configuration.configure("hibernate.cfg-postgres.xml");
			StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties());
			sessionFactory = configuration.buildSessionFactory(ssrb.build());
		}
		return sessionFactory.openSession();
	}

	public static void close() {
		sessionFactory.close();
		sessionFactory = null;

	}

}
