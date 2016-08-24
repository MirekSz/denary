package pl.altkom.lab17_finders;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.hibernate.SQLQuery;
import org.junit.Test;

import pl.altkom.model.Student;
import pl.altkom.model.Teacher;
import pl.altkom.util.BaseTest;

public class SQLTest extends BaseTest {

	@Test
	public void should_find_students_5_from_selected_subject() {
		// given
		Student s = (Student) session.createQuery("FROM Student").list().get(0);
		Teacher t = (Teacher) session.createQuery("FROM Teacher").list().get(0);

		s.setLastName("Nowak");
		t.setLastName("Nowak");

		// when
		SQLQuery query = session
				.createSQLQuery("SELECT s.id as sid,t.id as tid FROM STUDENT s, TEACHER t WHERE s.lastName = t.lastName");
		query.addSynchronizedEntityClass(Student.class)
				.addSynchronizedEntityClass(Teacher.class);
		List list = query.list();

		// then
		assertThat(list).isNotEmpty();
	}
}
