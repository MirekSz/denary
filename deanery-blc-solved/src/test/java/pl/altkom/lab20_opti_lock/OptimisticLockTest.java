package pl.altkom.lab20_opti_lock;

import static org.hibernate.criterion.Restrictions.eq;

import org.hibernate.Session;
import org.junit.Test;

import pl.altkom.model.Student;
import pl.altkom.util.BaseTest;

public class OptimisticLockTest extends BaseTest {
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
		Student student = (Student) session.load(Student.class, STUDENT_ID);

		// when
		student.setFirstName(student.getFirstName()
				+ System.currentTimeMillis());

	}

}
