package pl.altkom.lab19_pes_lock;

import static org.hibernate.criterion.Restrictions.eq;

import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import pl.altkom.model.Student;
import pl.altkom.util.BaseTest;

public class PesimisticLockTest extends BaseTest {
	private Long STUDENT_ID;

	@Override
	protected void beforeTest(Session session) {
		Student student = (Student) session.createCriteria(Student.class)
				.add(eq("index", "Index 00")).uniqueResult();
		this.STUDENT_ID = student.getId();
	}

	@Test
	public void should_read_record_for_edit() {
		// given
		session.load(Student.class, STUDENT_ID, LockOptions.UPGRADE);

	}

	@Test
	public void should_read_records_for_edit() {
		// given
		Query query = session
				.createQuery("FROM Student s ORDER BY s.firstName ASC");
		query.setMaxResults(10);
		query.setLockMode("s", LockMode.PESSIMISTIC_WRITE);

		query.list();

	}
}
