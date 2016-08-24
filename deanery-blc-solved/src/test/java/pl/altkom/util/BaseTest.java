package pl.altkom.util;

import java.util.Calendar;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;

import pl.altkom.model.TeacheTitle;
import pl.altkom.model.Teacher;

public class BaseTest {

	protected Session session;
	private Transaction tx;

	@Before
	public void beforeTestMethod() {
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();
		beforeTest(session);

	}

	protected void beforeTest(Session session) {
	}

	@After
	public void afterTest() {
		session.flush();
		if (rollback()) {
			tx.rollback();
		} else {
			tx.commit();
		}
		session.close();
	}

	protected boolean rollback() {
		return true;
	}

	protected long countRowsInTable(Class<?> entityClass) {
		return (Long) session.createCriteria(entityClass)
				.setProjection(Projections.rowCount()).uniqueResult();
	}

	public void cleanData(Class<?> entityClass) {
		session.createQuery("DELETE FROM " + entityClass.getSimpleName())
				.executeUpdate();
	}

	@AfterClass
	public static void afterTests() {
		HibernateUtil.close();
	}

	protected void flush() {
		session.flush();
	}

	protected void flushClear() {
		session.flush();
		session.clear();
	}

	protected Teacher preapareTeacher() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(1954, 11, 22);

		Teacher teacher = new Teacher();
		teacher.setEmail("jan.kowalski@gmail.com");
		teacher.setFirstName("Jan");
		teacher.setLastName("Kowalski");
		teacher.setBirthDate(calendar.getTime());
		teacher.setTitle(TeacheTitle.Mgr);

		return teacher;
	}
}
