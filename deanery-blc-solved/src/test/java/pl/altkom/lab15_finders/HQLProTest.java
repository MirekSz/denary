package pl.altkom.lab15_finders;

import static org.fest.assertions.Assertions.assertThat;
import static org.hibernate.criterion.Restrictions.eq;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import pl.altkom.model.Student;
import pl.altkom.util.BaseTest;

public class HQLProTest extends BaseTest {
	private Long STUDENT_ID;

	@Override
	protected void beforeTest(Session session) {
		Student student = (Student) session.createCriteria(Student.class)
				.add(eq("index", "Index 00")).uniqueResult();
		this.STUDENT_ID = student.getId();
	}

	@Test
	public void should_find_students_with_at_least_five_marks() {
		// given
		Query query = session.getNamedQuery("Student.findWithAtLeastFiveMarks");

		// when
		query.setMaxResults(5);
		List list = query.list();

		// then
		assertThat(list).isNotEmpty();
	}

	@Test
	public void should_coutn_avg_for_student() {
		// given
		Query query = session.getNamedQuery("Student.countAvgForStudent");
		query.setLong("id", STUDENT_ID);

		// when
		List<Object[]> list = query.list();

		// then
		assertThat(list).isNotEmpty();
		assertThat(list.get(0)[0]).isInstanceOf(String.class);
		assertThat(list.get(0)[1]).isInstanceOf(Double.class);
	}

	@Test
	public void promotions() {
		// given
		session.createQuery(
				"Insert Into Mark(value, student) select 5,s from Student s")
				.executeUpdate();
	}
}
