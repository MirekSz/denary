package pl.altkom.lab21_cache;

import static org.fest.assertions.Assertions.assertThat;
import static org.hibernate.criterion.Restrictions.eq;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import pl.altkom.model.Student;
import pl.altkom.util.BaseTest;

public class CacheTest extends BaseTest {
	private Long STUDENT_ID;

	@Override
	protected void beforeTest(Session session) {
		Student student = (Student) session.createCriteria(Student.class)
				.add(eq("index", "Index 00")).uniqueResult();
		this.STUDENT_ID = student.getId();
		session.clear();
	}

	@Test
	public void should_read_record_from_cache() {
		// given
		Student student = (Student) session.load(Student.class, STUDENT_ID);

		// when
		assertThat(student).isNotNull();
	}

	@Test
	public void should_read_query_from_cache() {
		Query query = session.createQuery("SELECT s.index FROM Student s");
		query.setCacheable(true);
		query.setMaxResults(10);
		query.list();

	}

}
